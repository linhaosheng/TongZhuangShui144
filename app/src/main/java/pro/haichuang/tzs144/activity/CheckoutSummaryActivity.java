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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.MyPagerAdapter;
import pro.haichuang.tzs144.fragment.SalesFragment;
import pro.haichuang.tzs144.model.StockMainModel;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

public class CheckoutSummaryActivity extends BaseActivity{


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

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.vp_view)
    ViewPager vp_view;

    private List<Fragment> fragmentList;
    private List<String>titleList;
    private MyPagerAdapter myPagerAdapter;

    private List<CharSequence> financialList;
    private List<CharSequence> subjectList;
    private List<CharSequence> shopList;
    private StockMainModel stockMainModel;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_check_summary;
    }

    @Override
    protected void setUpView() {
        title.setText("结账汇总");
        /**
         * 库存主体
         */
        inventory_subject.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(CheckoutSummaryActivity.this, subjectList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        inventory_subject.setRightText(text);
                    }
                });

            }
        });

        /**
         * 财务状态
         */
        financial_status.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                BottomMenu.show(CheckoutSummaryActivity.this, financialList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        financial_status.setRightText(text);
                    }
                });
            }
        });

        /**
         * 时间段
         */
        time_rand.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {

                TimePickerView pvTime = new TimePickerBuilder(CheckoutSummaryActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        time_rand.setRightText(Utils.formatSelectTime(date));
                    }
                })
                        .build();
                pvTime.show();

            }
        });

        /**
         * 商品类型
         */
        shop_type.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                BottomMenu.show(CheckoutSummaryActivity.this, shopList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        shop_type.setRightText(text);
                    }
                });
            }
        });

        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        fragmentList.add(new SalesFragment());
        fragmentList.add(new SalesFragment());
        fragmentList.add(new SalesFragment());

        titleList.add("销售情况");
        titleList.add("收入情况");
        titleList.add("开押情况");
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        vp_view.setAdapter(myPagerAdapter);
        tabs.setupWithViewPager(vp_view);
        tabs.setTabsFromPagerAdapter(myPagerAdapter);
        vp_view.setCurrentItem(0);

    }

    @Override
    protected void setUpData() {

        shopList = new ArrayList<>();
        shopList.add("商品品类1");
        shopList.add("商品品类2");
        shopList.add("商品品类3");

        financialList = new ArrayList<>();
        financialList.add("实时数据");
        financialList.add("历史数据");

        String subjectListJson = SPUtils.getString(Config.STOCK_MAIN_LIST, "");
        if (!subjectListJson.equals("")) {
            subjectList = new ArrayList<>();
            stockMainModel = Utils.gsonInstane().fromJson(subjectListJson, StockMainModel.class);
            for (StockMainModel.DataBean dataBean : stockMainModel.getData()) {
                subjectList.add(dataBean.getStockName());
            }
        }

    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
