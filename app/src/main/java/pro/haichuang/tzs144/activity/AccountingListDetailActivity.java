package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.AccountingListDetailPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 账目详情
 */
public class AccountingListDetailActivity extends BaseActivity implements ILoadDataView<AccountListDetailModel.DataBean> {

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
    private String id;


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
        accountingListDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String orderNumId = accountingListDetailAdapter.getData().get(position).getId() +"";
                Intent intent = new Intent(AccountingListDetailActivity.this, SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpData() {
        id = getIntent().getStringExtra("id");
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
                WaitDialog.show(this,"加载中...");
                accountingListDetailPresenter.cancelAccount(id);
                break;
            case R.id.start_time:
                endTime.setTextColor(Color.parseColor("#6D7278"));
                startTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn36));
                endTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn37));
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
                startTime.setTextColor(Color.parseColor("#6D7278"));
                endTime.setTextColor(Color.parseColor("#32C5FF"));
                startTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn37));
                endTime.setBackground(ContextCompat.getDrawable(this,R.drawable.set_bg_btn36));
                selectTime(SELECT_END_TIME);
                break;
            case R.id.bill:
                WaitDialog.show(this,"加载中...");
                accountingListDetailPresenter.settle();
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
                     accountingListDetailPresenter.getAccountInfo(id);
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
    public void successLoad(AccountListDetailModel.DataBean data) {

        WaitDialog.dismiss();
        cash.setText(data.getXjPrice()+"元");
        //coupon.setText(dataBean.);
        wechatPay.setText(data.getWxPrice()+"元");
        wateTicket.setText(data.getWaterNum()+"元");
        meituanPay.setText(data.getMtPrice()+"元");
        rewardNum.setText(data.getCouponNum()+"元");
        elmePay.setText(data.getElPrice()+"元");
        monthPay.setText(data.getMonthNum()+"元");

        if (data.getList()==null || data.getList().size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
            accountingListDetailAdapter.setList(data.getList());
        }
        if (data.getTime()==null){
            orderTime.setText("结账日期："+Utils.transformTime(new Date()));
        }else {
            orderTime.setText("结账日期："+data.getTime());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null && event.type==7) {
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("销账成功");
                accountingListDetailPresenter.getAccountInfo(id);
            } else {
                Utils.showCenterTomast("销账失败: "+event.result);
            }
        }else if (event != null && event.type==8){
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("结账成功");
                accountingListDetailPresenter.getAccountInfo(id);
            } else {
                Utils.showCenterTomast("结账失败 : "+event.result);
            }
        }
        Log.i(TAG, "onMessageEvent===");
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

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }
}
