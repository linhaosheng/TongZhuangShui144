package pro.haichuang.tzs144.fragment;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AllocationActivity;
import pro.haichuang.tzs144.activity.DemandListActivity;
import pro.haichuang.tzs144.activity.ReturnDetailActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;

/**
 * 库存
 */
public class InventoryFragment extends BaseFragment {


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


    @Override
    public boolean lazyLoader() {
        return false;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_inventory;
    }

    @Override
    protected void setUpView() {

        orderTitleList = new ArrayList<>();
        orderTitleList.add("全部 15");
        orderTitleList.add("可售 10");
        orderTitleList.add("已售罄 5");

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
}
