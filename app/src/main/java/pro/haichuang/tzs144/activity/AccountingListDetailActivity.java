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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AccountingListDetailAdapter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 账目详情
 */
public class AccountingListDetailActivity extends BaseActivity {

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

    private AccountingListDetailAdapter accountingListDetailAdapter;
    private List<String> dataList;
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

        accountingListDetailAdapter = new AccountingListDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycleData.setAdapter(accountingListDetailAdapter);
    }

    @Override
    protected void setUpData() {
        dataList = new ArrayList<>();
        for (int i = 0; i <4;i++){
            dataList.add("");
        }
        accountingListDetailAdapter.setList(dataList);
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
}
