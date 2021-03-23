package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AllocationActivity;
import pro.haichuang.tzs144.activity.DemandListActivity;
import pro.haichuang.tzs144.activity.ReturnDetailActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.MessageEvent;
import pro.haichuang.tzs144.model.SubjectModel;
import pro.haichuang.tzs144.model.TextEvent;
import pro.haichuang.tzs144.model.TypeListModel;
import pro.haichuang.tzs144.model.UpdateTitleEvent;
import pro.haichuang.tzs144.presenter.InventoryFragmentPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 库存
 */
public class InventoryFragment extends BaseFragment implements ILoadDataView<List<TypeListModel.DataBean>> {


    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.search_view)
    LinearLayout searchView;
    @BindView(R.id.title)
    RelativeLayout title;

    private MyPagerAdapter myPagerAdapter;
    private List<Fragment> orderList;
    private List<String> orderTitleList;

    private InventoryFragmentPresenter inventoryFragmentPresenter;
    public static List<TypeListModel.DataBean> dataBeanList;

    @Override
    public boolean lazyLoader() {
        return true;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_inventory;
    }

    @Override
    protected void setUpView() {
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  EventBus.getDefault().post(new TextEvent(new TextEvent.CurrentText(searchEdit.getText().toString(),vpView.getCurrentItem())));
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==event.KEYCODE_ENTER){
                    Utils.closeKeybord(getActivity());
                }
                return false;
            }
        });
    }

    private void initData(){
        orderTitleList = new ArrayList<>();
        orderTitleList.add("全部");
        orderTitleList.add("可售");
        orderTitleList.add("已售罄");

        orderList = new ArrayList<>();
        orderList.add(new InventoryNumFragment("0"));
        orderList.add(new InventoryNumFragment("1"));
        orderList.add(new InventoryNumFragment("2"));

        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), orderList, orderTitleList);
        vpView.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vpView);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vpView.setCurrentItem(0);

    }

    @Override
    protected void setUpData() {
        inventoryFragmentPresenter = new InventoryFragmentPresenter(this);
        inventoryFragmentPresenter.findGoodsCategory();

    }

    @OnClick({R.id.search, R.id.more, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                title.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.more:
                showMoreDialog();
                break;
            case R.id.cancel:
                title.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                break;
        }
    }

    private final void showMoreDialog() {
        new XPopup.Builder(getActivity())
                .atView(more)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(new String[]{"填写需求单", "调拨", "取水还桶明细"},
                        new int[]{},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Intent intent = new Intent();
                                if (position == 0) {
                                    intent.setClass(getActivity(), DemandListActivity.class);
                                } else if (position == 1) {
                                    intent.setClass(getActivity(), AllocationActivity.class);
                                } else if (position == 2) {
                                    intent.setClass(getActivity(), ReturnDetailActivity.class);
                                }
                                startActivity(intent);
                            }
                        })
                .show();
    }

    @Override
    public void startLoad() {

    }

    @Override
    public void successLoad(List<TypeListModel.DataBean> data) {
         if (data!=null){
             TypeListModel.DataBean dataBean = new TypeListModel.DataBean();
             dataBean.setName("全部商品");
             dataBean.setId(0);
             dataBeanList = new ArrayList<>();

             dataBeanList.add(dataBean);
             dataBeanList.addAll(data);

             data.clear();
             initData();
         }
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UpdateTitleEvent event) {
        if (event!=null){
            Log.i(TAG,"onMessageEvent===");
            tabs.getTabAt(0).setText("全部 "+event.inventoryNumModel.getData().getAllNum());
            tabs.getTabAt(1).setText("可售 "+event.inventoryNumModel.getData().getSellNum());
            tabs.getTabAt(2).setText("已售罄 "+event.inventoryNumModel.getData().getSellOutNum());
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
