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
import pro.haichuang.tzs144.adapter.OrderRecordAdapter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 订单记录
 */
public class OrderRecordActivity extends BaseActivity {


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
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.last_date)
    TextView lastDate;
    @BindView(R.id.last_week)
    TextView lastWeek;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.shop_num)
    TextView shopNum;
    @BindView(R.id._shop_last_date)
    TextView ShopLastDate;
    @BindView(R.id.shop_last_week)
    TextView shopLastWeek;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;

    private OrderRecordAdapter orderRecordAdapter;
    private List<String> datas;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_record;
    }

    @Override
    protected void setUpView() {
        title.setText("订单记录");
        orderRecordAdapter = new OrderRecordAdapter(this);
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(orderRecordAdapter);

    }

    @Override
    protected void setUpData() {
        datas = new ArrayList<>();
        datas.add("" + 0);
        datas.add("" + 1);
        datas.add("" + 2);
        datas.add("" + 0);
        datas.add("" + 1);
        datas.add("" + 2);
        orderRecordAdapter.setList(datas);
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.back, R.id.start_time, R.id.end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.start_time:
                selectTime(SELECT_START_TIME);
                break;
            case R.id.end_time:
                selectTime(SELECT_END_TIME);
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
