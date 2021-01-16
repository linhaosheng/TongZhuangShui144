package pro.haichuang.tzs144.fragment;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;

/**
 * 库存
 */
public class InventoryFragment extends BaseFragment{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vpView;

    private MyPagerAdapter myPagerAdapter;
    private List<Fragment> orderList;
    private List<String>orderTitleList;


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

        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),orderList,orderTitleList);
        vpView.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vpView);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vpView.setCurrentItem(0);

    }

    @Override
    protected void setUpData() {

    }
}
