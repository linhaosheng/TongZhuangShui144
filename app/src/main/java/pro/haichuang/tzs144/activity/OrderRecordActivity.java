package pro.haichuang.tzs144.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.OrderInfoAdapter;
import pro.haichuang.tzs144.adapter.OrderRecordAdapter;

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
}
