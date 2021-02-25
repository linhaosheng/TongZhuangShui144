package pro.haichuang.tzs144.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddOrderAdapter;
import pro.haichuang.tzs144.adapter.OrderDetailAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.MessageEvent;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.SubjectModel;
import pro.haichuang.tzs144.presenter.OrderDetailPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements ILoadDataView<OrderDetailModel.DataBean> {


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
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.order_state_img)
    ImageView orderStateImg;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.time_out)
    TextView timeOut;
    @BindView(R.id.name_view)
    RelativeLayout nameView;

    @BindView(R.id.time_send)
    TextView timeSend;
    @BindView(R.id.tatal_price)
    TextView tatalPrice;
    @BindView(R.id.need_price)
    TextView needPrice;
    @BindView(R.id.actual_price)
    TextView actualPrice;
    @BindView(R.id.price_view)
    RelativeLayout priceView;
    @BindView(R.id.order_num_data)
    TextView orderNumData;
    @BindView(R.id.record_time)
    TextView recordTime;
    @BindView(R.id.order_source)
    TextView orderSource;
    @BindView(R.id.pay_way)
    TextView payWay;
    @BindView(R.id.order_view)
    LinearLayout orderView;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
    @BindView(R.id.take_order)
    Button takeOrder;
    @BindView(R.id.switch_order)
    Button switchOrder;
    @BindView(R.id.void_delivery_view)
    LinearLayout voidDeliveryView;

    private OrderDetailAdapter orderDetailAdapter;

    private OrderDetailPresenter orderDetailPresenter;
    private  String id;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void setUpView() {
        title.setText("订单详情");
        tips.setText("作废");
        tips.setVisibility(View.GONE);
        tips.setTextSize(14);
        tips.setTextColor(Color.parseColor("#3F3F3F"));

        orderDetailAdapter = new OrderDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(orderDetailAdapter);
    }

    @Override
    protected void setUpData() {
        id = getIntent().getStringExtra("id");
        orderDetailPresenter = new OrderDetailPresenter(this);
        orderDetailPresenter.getOrderInfo(id);
    }


    @OnClick({R.id.back, R.id.tips,R.id.switch_order,R.id.take_order,R.id.delivery_btn,R.id.void_sale_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.switch_order:
                break;
            case R.id.take_order:
                orderDetailPresenter.takeOrder(id);
                break;
            case R.id.delivery_btn:
                break;
            case R.id.void_sale_btn:
                WaitDialog.show(this,"提交中...");
                orderDetailPresenter.directSelling(id);
                break;
            case R.id.tips:

                break;
        }
    }

    @Override
    public void startLoad() {
        WaitDialog.show(this, "加载中...");
    }

    @Override
    public void successLoad(OrderDetailModel.DataBean data) {
        WaitDialog.dismiss();

        name.setText(data.getCustomerName());
        orderNum.setText(data.getCustomerPhone());
        type.setText(data.getCustomerTypeName());
        address.setText(data.getAddressName());
        addressDetail.setText(data.getAddress());
        timeSend.setText(data.getTimeRange());
        timeOut.setText(data.getTimeStatus());

        tatalPrice.setText("¥" + data.getTotalPrice());
        needPrice.setText("¥" + data.getReceivablePrice());
        orderNumData.setText("订单编号：" + data.getOrderNo());
        recordTime.setText("完成时间：" + data.getCompleteTime());
        orderSource.setText("订单来源: "+data.getOrderType());
        payWay.setText("支付方式：" + data.getPayMode());
        orderDetailAdapter.setList(data.getGoodsList());
        actualPrice.setText("¥"+data.getRealPrice());

        timeOut.setVisibility(View.VISIBLE);
        if (data.getDeliveryStatus()==0){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.pend_order));
        }else if (data.getDeliveryStatus()==1){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.nedd_delivery));
        }else if (data.getDeliveryStatus()==2){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.have_finish));
        }else if (data.getDeliveryStatus()==3){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.have_cancel));
        }

        if (id.equals("0")){
            takeOrder.setText("接单");
        }else if (id.equals("1")){
            takeOrder.setText("配送");
        }
        if (data.getTimeStatus().contains("已超时") && data.getDeliveryStatus()==1){
            voidDeliveryView.setVisibility(View.VISIBLE);
            takeOrder.setVisibility(View.GONE);
        }else {
            voidDeliveryView.setVisibility(View.GONE);
        }
        if (data.getDeliveryStatus()==2){
            voidDeliveryView.setVisibility(View.GONE);
            takeOrder.setVisibility(View.GONE);
            switchOrder.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.type==2){
                if (event.status == Config.LOAD_SUCCESS) {
                    Utils.showCenterTomast("定单作废成功");
                } else {
                    Utils.showCenterTomast("定单作废失败");
                }
            }else if (event.type==1){
                if (event.status == Config.LOAD_SUCCESS) {
                    Utils.showCenterTomast("接单成功");
                    finish();
                } else {
                    Utils.showCenterTomast("定单作废失败");
                }
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
