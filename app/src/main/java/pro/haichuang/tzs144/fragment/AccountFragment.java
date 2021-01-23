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
 * 账务
 */
public class AccountFragment extends BaseFragment{

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
        return R.layout.fragment_account;
    }

    @Override
    protected void setUpView() {
        title.setText("财务管理");

        orderTitleList = new ArrayList<>();
        orderTitleList.add("实时数据");
        orderTitleList.add("历史数据");


        orderList = new ArrayList<>();
        orderList.add(new ClientRealTimeDataFragment());
        orderList.add(new ClientHistoryTimeDataFragment());

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
