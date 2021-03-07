package pro.haichuang.tzs144.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.OrderRecordAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderRecordModel;
import pro.haichuang.tzs144.presenter.OrderRecordActivityPresenter;
import pro.haichuang.tzs144.util.Utils;

/**
 * 订单记录
 */
public class OrderRecordActivity extends BaseActivity implements ILoadDataView<OrderRecordModel.DataBean> {


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
    @BindView(R.id.shop_last_date)
    TextView ShopLastDate;
    @BindView(R.id.shop_last_week)
    TextView shopLastWeek;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private String customerId;

    private OrderRecordAdapter orderRecordAdapter;
    private final static  int SELECT_START_TIME = 0x110;
    private final static  int SELECT_END_TIME = 0x111;
    private OrderRecordActivityPresenter orderRecordActivityPresenter;
    private int currentPage = 1;


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
        orderRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String orderNumId = orderRecordAdapter.getData().get(position).getId();
                Intent intent = new Intent(OrderRecordActivity.this, SaleOrderDetailActivity.class);
                intent.putExtra("id",orderNumId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpData() {
        customerId = getIntent().getStringExtra("id");
        if (customerId==null){
            return;
        }
        orderRecordActivityPresenter = new OrderRecordActivityPresenter(this);
        endTime.setText(Utils.formatSelectTime(new Date()));
        orderRecordActivityPresenter.findCustomerOrders(customerId,startTime.getText().toString(),endTime.getText().toString(),currentPage);
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
                    orderRecordActivityPresenter.findCustomerOrders(customerId,startTime.getText().toString(),endTime.getText().toString(),currentPage);
                }
            }
        })
                .build();
        pvTime.show();
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this,"加载中...");
    }

    @Override
    public void successLoad(OrderRecordModel.DataBean data) {
        WaitDialog.dismiss();

        if (data.getOrderList()==null || data.getOrderList().size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
        }

        Drawable upDrawable = ContextCompat.getDrawable(this, R.mipmap.trend_up);
        upDrawable.setBounds(0,0,upDrawable.getMinimumWidth(),upDrawable.getMinimumHeight());

        Drawable dowmDrawable = ContextCompat.getDrawable(this, R.mipmap.trend_down);
        dowmDrawable.setBounds(0,0,upDrawable.getMinimumWidth(),upDrawable.getMinimumHeight());

        if (data.getDayOrderRatio().contains("+")){
            lastDate.setTextColor(Color.parseColor("#E02020"));
            lastDate.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            lastDate.setTextColor(Color.parseColor("#3C9C25"));
            lastDate.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        if (data.getWeekOrderRatio().contains("+")){
            lastWeek.setTextColor(Color.parseColor("#E02020"));
            lastWeek.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            lastWeek.setTextColor(Color.parseColor("#3C9C25"));
            lastWeek.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        if (data.getDaySaleRatio().contains("+")){
            ShopLastDate.setTextColor(Color.parseColor("#E02020"));
            ShopLastDate.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            ShopLastDate.setTextColor(Color.parseColor("#3C9C25"));
            ShopLastDate.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        if (data.getWeekSaleRatio().contains("+")){
            shopLastWeek.setTextColor(Color.parseColor("#E02020"));
            shopLastWeek.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            shopLastWeek.setTextColor(Color.parseColor("#3C9C25"));
            shopLastWeek.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        orderNum.setText(data.getOrderCount());

        lastDate.setText(data.getDayOrderRatio().replace("+","").replace("-","")+"%");
        lastWeek.setText(data.getWeekOrderRatio().replace("+","").replace("-","")+"%");

        shopNum.setText(data.getSaleCount());

        ShopLastDate.setText(data.getDaySaleRatio().replace("+","").replace("-","")+"%");
        shopLastWeek.setText(data.getWeekSaleRatio().replace("+","").replace("-","")+"%");

        orderRecordAdapter.setList(data.getOrderList());

    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
        Utils.showCenterTomast(error);
    }
}
