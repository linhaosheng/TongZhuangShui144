package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
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
import pro.haichuang.tzs144.activity.AccountingListActivity;
import pro.haichuang.tzs144.activity.AllocationActivity;
import pro.haichuang.tzs144.activity.CheckoutSummaryActivity;
import pro.haichuang.tzs144.activity.DemandListActivity;
import pro.haichuang.tzs144.activity.DepositManagementSearchActivity;
import pro.haichuang.tzs144.activity.ReturnDetailActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;

/**
 * 账务
 */
public class AccountFragment extends BaseFragment {

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
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.tip_img)
    ImageView tipImg;

    private MyPagerAdapter myPagerAdapter;
    private List<Fragment> orderList;
    private List<String> orderTitleList;


    @Override
    public boolean lazyLoader() {
        return true;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_account;
    }

    @Override
    protected void setUpView() {
        back.setVisibility(View.GONE);
        title.setText("财务管理");
        tipImg.setVisibility(View.VISIBLE);
        tipImg.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.mipmap.more));

        orderTitleList = new ArrayList<>();
        orderTitleList.add("实时数据");
        orderTitleList.add("历史数据");


        orderList = new ArrayList<>();
        orderList.add(new ClientRealTimeDataFragment());
        orderList.add(new ClientHistoryTimeDataFragment());

        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), orderList, orderTitleList);
        vpView.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vpView);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vpView.setCurrentItem(0);
    }

    @Override
    protected void setUpData() {

    }

    @OnClick(R.id.tip_img)
    public void onViewClicked() {
        new XPopup.Builder(getActivity())
                .atView(tipImg)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(new String[]{"结账汇总","账目列表", "押金本管理"},
                        new int[]{},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Intent intent = new Intent();
                                if (position==0){
                                    intent.setClass(getActivity(), CheckoutSummaryActivity.class);
                                }else if (position == 1) {
                                    intent.setClass(getActivity(), AccountingListActivity.class);
                                } else if (position == 2) {
                                    intent.setClass(getActivity(), DepositManagementSearchActivity.class);
                                }
                                startActivity(intent);
                            }
                        })
                .show();
    }
}
