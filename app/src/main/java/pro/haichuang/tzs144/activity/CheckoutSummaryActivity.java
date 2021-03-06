package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.fragment.CustodySituationFragment;
import pro.haichuang.tzs144.fragment.IncomeCountFragment;
import pro.haichuang.tzs144.fragment.RecycleCountFragment;
import pro.haichuang.tzs144.fragment.SalesFragment;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.RefreshCountEvent;
import pro.haichuang.tzs144.model.ShopListModel;
import pro.haichuang.tzs144.model.StockMainModel;
import pro.haichuang.tzs144.model.SummaryModel;
import pro.haichuang.tzs144.model.TypeListModel;
import pro.haichuang.tzs144.presenter.CheckoutSummaryPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;
import pro.haichuang.tzs144.view.TimeDialog;

public class CheckoutSummaryActivity extends BaseActivity implements ILoadDataView<List<SummaryModel.DataBean>> {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.inventory_subject)
    LSettingItem inventory_subject;
    @BindView(R.id.financial_status)
    LSettingItem financial_status;
    @BindView(R.id.time_rand)
    LSettingItem time_rand;
    @BindView(R.id.shop_type)
    LSettingItem shop_type;
    @BindView(R.id.cash_num)
    TextView cash_num;
    @BindView(R.id.platform_num)
    TextView platform_num;
    @BindView(R.id.coupon_num)
    TextView coupon_num;
    @BindView(R.id.takeaway_num)
    TextView takeawayNum;
    @BindView(R.id.give_away_num)
    TextView giveAwayNum;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_view)
    ViewPager vp_view;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private MyPagerAdapter myPagerAdapter;

    private List<CharSequence> financialList;
    private List<CharSequence> subjectList;
    private List<CharSequence> shopList;
    private StockMainModel stockMainModel;
    private TypeListModel typeListModel;
    private CheckoutSummaryPresenter checkoutSummaryPresenter;
    public static String startTime;
    public static String endTime;
    public static String scMainId;
    public static String categoryId;
    public static String type;
    private float takeNum;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_check_summary;
    }

    @Override
    protected void setUpView() {
        title.setText("????????????");
        /**
         * ????????????
         */
        inventory_subject.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                if (Config.AUTHORITY.contains("8")){
                    BottomMenu.show(CheckoutSummaryActivity.this, subjectList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        inventory_subject.setRightText(text);
                        scMainId = String.valueOf(stockMainModel.getData().get(index).getId());
                        EventBus.getDefault().post(new RefreshCountEvent(0));
                        checkoutSummaryPresenter.findSummaryHj(scMainId, type, startTime, endTime, categoryId);
                    }
                });
                }
            }
        });

        /**
         * ????????????
         */
        financial_status.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                BottomMenu.show(CheckoutSummaryActivity.this, financialList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        financial_status.setRightText(text);
                        type = String.valueOf(index);
                        EventBus.getDefault().post(new RefreshCountEvent(0));
                        checkoutSummaryPresenter.findSummaryHj(scMainId, type, startTime, endTime, categoryId);
                    }
                });
            }
        });

        /**
         * ?????????
         */
        time_rand.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                TimeDialog timeDialog = new TimeDialog(CheckoutSummaryActivity.this, new TimeDialog.SelectTimeListener() {
                    @Override
                    public void selectTime(String mStartTime, String mEndTime) {
                        startTime = mStartTime;
                        endTime = mEndTime;
                        time_rand.setRightText(startTime + "  -  " + endTime);
                        EventBus.getDefault().post(new RefreshCountEvent(0));
                        checkoutSummaryPresenter.findSummaryHj(scMainId, type, startTime, endTime, categoryId);
                    }
                });
                timeDialog.show(getSupportFragmentManager(), "");
            }
        });

        /**
         * ????????????
         */
        shop_type.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(CheckoutSummaryActivity.this, shopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        shop_type.setRightText(text);
//                        if (index==0){
//                            categoryId = "";
//                        }else {
//                            categoryId = String.valueOf(typeListModel.getData().get(index-1).getId());
//                        }
                        categoryId = String.valueOf(typeListModel.getData().get(index).getId());
                        checkoutSummaryPresenter.findSummaryHj(scMainId, type, startTime, endTime, categoryId);
                        EventBus.getDefault().post(new RefreshCountEvent(0));
                    }
                });
            }
        });
    }

    @Override
    protected void setUpData() {

        shopList = new ArrayList<>();

        financialList = new ArrayList<>();
        financialList.add("????????????");
        financialList.add("????????????");

        String subjectListJson = SPUtils.getString(Config.STOCK_MAIN_LIST, "");
        if (!subjectListJson.equals("")) {
            subjectList = new ArrayList<>();
            stockMainModel = Utils.gsonInstane().fromJson(subjectListJson, StockMainModel.class);
            for (StockMainModel.DataBean dataBean : stockMainModel.getData()) {
                subjectList.add(dataBean.getStockName());
            }
        }

        /**
         * ????????????
         */
        String categoryListJson = SPUtils.getString(Config.GOODS_CATEGORY_LIST, "");
        if (!categoryListJson.equals("")) {
            shopList = new ArrayList<>();
         //   shopList.add("??????");
            typeListModel = Utils.gsonInstane().fromJson(categoryListJson, TypeListModel.class);
            for (TypeListModel.DataBean dataBean : typeListModel.getData()) {
                shopList.add(dataBean.getName());
            }
        }

        try {

            startTime = Utils.formatSelectTime(new Date());
            endTime = Utils.formatSelectTime(new Date());
            time_rand.setRightText(startTime + "  -  " + endTime);
            inventory_subject.setRightText(subjectList.get(0).toString());
            financial_status.setRightText(financialList.get(0).toString());
            shop_type.setRightText(shopList.get(0).toString());

            checkoutSummaryPresenter = new CheckoutSummaryPresenter(this);
            inventory_subject.setRightText(Config.CURRENT_MAIN_NAME);
            scMainId = Config.CURRENT_MAIN_ID;
            categoryId = String.valueOf(typeListModel.getData().get(0).getId());;
            type = "0";

            initFragmentData();

            checkoutSummaryPresenter.findSummaryHj(scMainId, type, startTime, endTime, categoryId);

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showCenterTomast("??????????????????");
        }
    }

    /**
     * ?????????fragment??????
     */
    private void initFragmentData() {
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        fragmentList.add(new SalesFragment());
        fragmentList.add(new IncomeCountFragment());
        fragmentList.add(new RecycleCountFragment());
        fragmentList.add(new CustodySituationFragment(0));

        titleList.add("????????????");
        titleList.add("????????????");
        titleList.add("????????????");
        titleList.add("???????????????");
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        vp_view.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vp_view);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vp_view.setCurrentItem(0);
    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"?????????...");
    }

    @Override
    public void successLoad(List<SummaryModel.DataBean> datas) {
        WaitDialog.dismiss();
        cash_num.setText("0???");
        platform_num.setText("0???");
        takeawayNum.setText("0???");
        giveAwayNum.setText("0???");
        coupon_num.setText("0???");
        takeNum = 0.0f;
        if (datas != null && datas.size() >= 0) {
            for (SummaryModel.DataBean dataBean : datas) {
                if (dataBean.getPrice()!=null){
                    if ("0".equals(dataBean.getType())) {
                        cash_num.setText(dataBean.getPrice() + "???");
                    } else if ("1".equals(dataBean.getType())) {
                        platform_num.setText(dataBean.getPrice() + "???");
                    }else if ("5".equals(dataBean.getType())){
                        giveAwayNum.setText(dataBean.getPrice() + "???");
                    } else if ("2".equals(dataBean.getType()) || "3".equals(dataBean.getType())){
                        try {
                            takeNum = takeNum + Float.parseFloat(dataBean.getPrice());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else if ("4".equals(dataBean.getType())){
                        coupon_num.setText(dataBean.getPrice() + "???");
                    }
                }
            }
        }
        takeawayNum.setText(takeNum+"???");
    }

    @Override
    public void errorLoad(String error) {
        Utils.showCenterTomast(error);
        WaitDialog.dismiss();
    }
}
