package pro.haichuang.tzs144.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.next.easynavigation.utils.NavigationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.InventoryNumAdapter;
import pro.haichuang.tzs144.adapter.TypeListAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.InventoryNumModel;
import pro.haichuang.tzs144.model.TextEvent;
import pro.haichuang.tzs144.model.UpdateTitleEvent;
import pro.haichuang.tzs144.presenter.InventoryNumFragmentPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 库存数量
 */
public class InventoryNumFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener , ILoadDataView<InventoryNumModel> {



    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.list_recycle)
    RecyclerView listRecycle;

    private TypeListAdapter typeListAdapter;
    private InventoryNumAdapter inventoryNumAdapter;
    private int currentIndex = 0;
    private String id;
    private InventoryNumFragmentPresenter inventoryNumFragmentPresenter;
    private List<InventoryNumModel.DataBean.ListBean>allDataList;

    public InventoryNumFragment(String mId) {
       this.id = mId;
    }

    public InventoryNumFragment() {
        super();
    }

    @Override
    public boolean lazyLoader() {
//        if (id.equals("0")){
//            return false;
//        }
//        return true;
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragmnet_inventor_num;
    }

    @Override
    protected void setUpView() {
        inventoryNumFragmentPresenter = new InventoryNumFragmentPresenter(this);
        refresh.setOnRefreshListener(this);
        typeListAdapter = new TypeListAdapter();
        inventoryNumAdapter = new InventoryNumAdapter();

        recycleData.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleData.setAdapter(inventoryNumAdapter);

        inventoryNumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        listRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        listRecycle.setAdapter(typeListAdapter);
        typeListAdapter.setList(InventoryFragment.dataBeanList);

        typeListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                typeListAdapter.setSelectIndex(position);
                typeListAdapter.notifyDataSetChanged();
                if (position!=0){
                    String categoryId = String.valueOf(typeListAdapter.getData().get(position).getId());
                    inventoryNumFragmentPresenter.findGoodsWithType("",categoryId,id);
                }
            }
        });
    }

    @Override
    protected void setUpData() {
       if (inventoryNumAdapter.getData()==null || inventoryNumAdapter.getData().size()==0){
           inventoryNumFragmentPresenter.findGoodsWithType("","",id);
       }
    }

    @Override
    public void onRefresh() {
        inventoryNumFragmentPresenter.findGoodsWithType("","",id);
    }

    @Override
    public void startLoad() {
        refresh.setRefreshing(true);
    }

    @Override
    public void successLoad(InventoryNumModel data) {
        refresh.setRefreshing(false);
       if (data!=null){
           if (id.equals("0") && allDataList== null){
               EventBus.getDefault().post(new UpdateTitleEvent(data));
           }
           allDataList = data.getData().getList();
           inventoryNumAdapter.setList(data.getData().getList());
       }
    }

    @Override
    public void errorLoad(String error) {
        refresh.setRefreshing(false);
        Utils.showCenterTomast(error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TextEvent event) {
        if (event!=null){
            TextEvent.CurrentText currentText = event.currentText;
            String text = currentText.text;
            if (id.equals(String.valueOf(currentText.currentIndex))){
                inventoryNumFragmentPresenter.findGoodsWithType(text,"",id);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
