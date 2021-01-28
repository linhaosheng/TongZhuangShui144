package pro.haichuang.tzs144.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.InventoryNumAdapter;

/**
 * 库存数量
 */
public class InventoryNumFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.all_commodity)
    TextView allCommodity;
    @BindView(R.id.bottled_water)
    TextView bottledWater;
    @BindView(R.id.water_tickets)
    TextView waterTickets;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private InventoryNumAdapter inventoryNumAdapter;
    private List<String> listData;
    private int currentIndex = 0;


    public InventoryNumFragment(String id) {

    }

    public InventoryNumFragment() {
        super();
    }

    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragmnet_inventor_num;
    }

    @Override
    protected void setUpView() {
        refresh.setOnRefreshListener(this);
        inventoryNumAdapter = new InventoryNumAdapter();

        recycleData.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recycleData.setAdapter(inventoryNumAdapter);

        inventoryNumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }

    @Override
    protected void setUpData() {
        listData = new ArrayList<>();
        for (int i = 0;i<6;i++){
            listData.add("");
        }
        inventoryNumAdapter.setList(listData);
    }

    @OnClick({R.id.all_commodity, R.id.bottled_water, R.id.water_tickets})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_commodity:
                 if (currentIndex!=0){

                 }
                 currentIndex = 0;

                break;
            case R.id.bottled_water:
                currentIndex = 1;
                break;
            case R.id.water_tickets:
                currentIndex = 2;
                break;
        }
        setBg();
    }

    private void setBg(){
        allCommodity.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.grep10));
        bottledWater.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.grep10));
        waterTickets.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.grep10));
        if (currentIndex==0){
            allCommodity.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.set_bg_color));
        }else if (currentIndex==1){
            bottledWater.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.set_bg_color));
        }else if (currentIndex==2){
            waterTickets.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.set_bg_color));
        }
    }

    @Override
    public void onRefresh() {

    }
}
