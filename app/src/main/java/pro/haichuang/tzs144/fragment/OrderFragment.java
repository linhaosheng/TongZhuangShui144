package pro.haichuang.tzs144.fragment;

import android.content.Intent;
import android.util.Log;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AllocationActivity;
import pro.haichuang.tzs144.activity.CheckoutSummaryActivity;
import pro.haichuang.tzs144.activity.DemandListActivity;
import pro.haichuang.tzs144.activity.LoginActivity;
import pro.haichuang.tzs144.activity.ReturnDetailActivity;
import pro.haichuang.tzs144.activity.SalesListActivity;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.model.PageEvent;
import pro.haichuang.tzs144.model.StatusUpdateEvent;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.CustomViewPager;

/**
 * 订单
 */
public class OrderFragment extends BaseFragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    @BindView(R.id.tip_img)
    ImageView tip_img;
    private MyPagerAdapter myPagerAdapter;
    private List<Fragment> orderList;
    private List<String>orderTitleList;

    @Override
    public boolean lazyLoader() {
        return true;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_order;
    }

    @Override
    protected void setUpView() {
        back.setVisibility(View.GONE);
        title.setText("订单");
        tips.setText("直接销售列表");
        tips.setTextSize(12);
        tips.setVisibility(View.GONE);
        tip_img.setVisibility(View.VISIBLE);
        tip_img.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.mipmap.more));

        orderTitleList = new ArrayList<>();
        orderTitleList.add("新订单");
        orderTitleList.add("待配送");
        orderTitleList.add("取消单");
        orderTitleList.add("订单查询");

        orderList = new ArrayList<>();
        orderList.add(new OrderInfoFragment(0));
        orderList.add(new OrderInfoFragment(1));
        orderList.add(new OrderInfoFragment(3));
        orderList.add(new OrderInfoFragment(4));

        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),orderList,orderTitleList);

        //vpView.setSlidingEnable(true);
        vpView.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vpView);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vpView.setCurrentItem(0);

    }


    private final void showMoreDialog() {
        new XPopup.Builder(getActivity())
                .atView(tip_img)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                .asAttachList(new String[]{"直接销售列表", "退出登录"},
                        new int[]{},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Intent intent = new Intent();
                                if (position == 0) {
                                    intent.setClass(getActivity(), SalesListActivity.class);
                                } else if (position == 1) {
                                    intent.setClass(getActivity(), LoginActivity.class);
                                    getActivity().finish();
                                }
                                startActivity(intent);
                            }
                        })
                .show();
    }

    @Override
    protected void setUpData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PageEvent event) {
        if (event != null) {
            try {
                int type = event.type;
                if (vpView!=null){
                    vpView.setCurrentItem(3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.tip_img)
    public void onViewClicked() {
        showMoreDialog();
    }
}
