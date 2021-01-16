package pro.haichuang.tzs144.fragment;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;

/**
 * 订单
 */
public class OrderFragment extends BaseFragment {


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
        return R.layout.fragment_order;
    }

    @Override
    protected void setUpView() {

        orderTitleList = new ArrayList<>();
        orderTitleList.add("新订单");
        orderTitleList.add("待配送");
        orderTitleList.add("取消单");
        orderTitleList.add("订单查询");

        orderList = new ArrayList<>();
        orderList.add(new OrderInfoFragment("0"));
        orderList.add(new OrderInfoFragment("1"));
        orderList.add(new OrderInfoFragment("2"));
        orderList.add(new OrderInfoFragment("3"));

        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),orderList,orderTitleList);
        vpView.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vpView);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vpView.setCurrentItem(0);
    }

    @Override
    protected void setUpData() {

    }

    @OnClick(R.id.tips)
    public void onViewClicked() {
    }
}
