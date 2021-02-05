package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.kongzue.dialog.v3.WaitDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AccountingListDetailAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AccountListDetailModel;
import pro.haichuang.tzs144.presenter.AccountingListDetailPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 账目详情
 */
public class AccountingListDetailActivity extends BaseActivity implements ILoadDataView<AccountListDetailModel> {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.tip_img)
    ImageView tipImg;
    @BindView(R.id.head_view)
    RelativeLayout headView;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.write_off)
    TextView writeOff;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.cash)
    TextView cash;
    @BindView(R.id.coupon)
    TextView coupon;
    @BindView(R.id.wechat_pay)
    TextView wechatPay;
    @BindView(R.id.wate_ticket)
    TextView wateTicket;
    @BindView(R.id.meituan_pay)
    TextView meituanPay;
    @BindView(R.id.reward_num)
    TextView rewardNum;
    @BindView(R.id.elme_pay)
    TextView elmePay;
    @BindView(R.id.month_pay)
    TextView monthPay;
    @BindView(R.id.order_detail)
    TextView orderDetail;
    @BindView(R.id.bill)
    TextView bill;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    private AccountingListDetailPresenter accountingListDetailPresenter;

    private AccountingListDetailAdapter accountingListDetailAdapter;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_account_list_detail;
    }

    @Override
    protected void setUpView() {
        title.setText("账目详情");
        tips.setVisibility(View.VISIBLE);
        tips.setTextSize(12);
        tips.setText("销账");
        endTime.setText(Utils.formatSelectTime(new Date()));

        accountingListDetailAdapter = new AccountingListDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(accountingListDetailAdapter);
    }

    @Override
    protected void setUpData() {
        String id = getIntent().getStringExtra("id");
        accountingListDetailPresenter = new AccountingListDetailPresenter(this);
        accountingListDetailPresenter.getAccountInfo(id);
    }


    @OnClick({R.id.back, R.id.tips, R.id.start_time, R.id.end_time, R.id.write_off, R.id.bill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                break;
            case R.id.start_time:
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
                selectTime(SELECT_END_TIME);
                break;
            case R.id.write_off:
                break;
            case R.id.bill:
                break;
        }
    }

    private void selectTime(int type){

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                 if (type==SELECT_START_TIME){
                   startTime.setText(Utils.formatSelectTime(date));
                 }else {
                     endTime.setText(Utils.formatSelectTime(date));
                 }
            }
        })
                .build();
        pvTime.show();
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"获取中...");
    }

    @Override
    public void successLoad(AccountListDetailModel data) {

        WaitDialog.dismiss();
        AccountListDetailModel.DataBean dataBean = data.getData().get(0);
        cash.setText(dataBean.getXjPrice()+"元");
        //coupon.setText(dataBean.);
        wechatPay.setText(dataBean.getWxPrice()+"元");
        wateTicket.setText(dataBean.getWaterNum()+"元");
        meituanPay.setText(dataBean.getMtPrice()+"元");
        rewardNum.setText(dataBean.getCouponNum()+"元");
        elmePay.setText(dataBean.getElPrice()+"元");
        monthPay.setText(dataBean.getMonthNum()+"元");

        if (dataBean.getList()==null || dataBean.getList().size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
            accountingListDetailAdapter.setList(dataBean.getList());
        }
        if (dataBean.getTime()==null){
            orderTime.setText("结账日期："+Utils.transformTime(new Date()));
        }else {
            orderTime.setText("结账日期："+dataBean.getTime());
        }
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }
}
