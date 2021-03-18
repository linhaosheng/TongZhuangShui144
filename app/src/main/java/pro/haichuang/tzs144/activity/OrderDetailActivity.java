package pro.haichuang.tzs144.activity;


import android.content.Intent;
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

import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.AddOrderAdapter;
import pro.haichuang.tzs144.adapter.OrderDetailAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.AreaEvent;
import pro.haichuang.tzs144.model.AreaModel;
import pro.haichuang.tzs144.model.MessageEvent;
import pro.haichuang.tzs144.model.OrderDetailDataModel;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.ShopDeleveModel;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.model.StockMainModel;
import pro.haichuang.tzs144.model.SubjectModel;
import pro.haichuang.tzs144.model.UpdateOrderEvent;
import pro.haichuang.tzs144.presenter.OrderDetailActivityPresenter;
import pro.haichuang.tzs144.presenter.OrderDetailPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.SPUtils;
import pro.haichuang.tzs144.util.Utils;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements ILoadDataView<OrderDetailDataModel.DataBean> {


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
    @BindView(R.id.void_sale_btn)
    Button voidSaleBtn;

    private OrderDetailAdapter orderDetailAdapter;

    private OrderDetailActivityPresenter orderDetailPresenter;
    private String id;
    private int typeId;
    private int orderStatus;
    private String orderNo;

    private List<CharSequence> subjectList;
    private StockMainModel stockMainModel;


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
        orderStatus = getIntent().getIntExtra("orderStatus", 0);
        id = getIntent().getStringExtra("id");
        typeId = getIntent().getIntExtra("typeId", 0);
        orderDetailPresenter = new OrderDetailActivityPresenter(this);
        orderDetailPresenter.getHomeOrderInfo(id);
        if (typeId == 0 || typeId==1) {
            recordTime.setVisibility(View.GONE);
        }
        /**
         * 所在片区
         */
        String subjectListJson = SPUtils.getString(Config.STOCK_MAIN_LIST, "");
        if (!subjectListJson.equals("")) {
            subjectList = new ArrayList<>();
            stockMainModel = Utils.gsonInstane().fromJson(subjectListJson, StockMainModel.class);
            for (StockMainModel.DataBean dataBean : stockMainModel.getData()) {
                subjectList.add(dataBean.getStockName());
            }
        }
    }


    @OnClick({R.id.back, R.id.tips, R.id.switch_order, R.id.take_order, R.id.delivery_btn, R.id.void_sale_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.switch_order:
                BottomMenu.show(OrderDetailActivity.this, subjectList, new OnMenuItemClickListener() {
                    @Override
                    public void onClick(String text, int index) {
                        orderDetailPresenter.turnOrder(id,stockMainModel.getData().get(index).getId());
                    }
                });

                break;
            case R.id.take_order:
                orderDetailPresenter.takeOrder(id);
                break;
            case R.id.delivery_btn:

                Intent intent = new Intent(this, DeliveryOrderActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("typeId", typeId);
                intent.putExtra("orderStatus", orderStatus);
                startActivity(intent);
                finish();

                break;
            case R.id.void_sale_btn:
                WaitDialog.show(this, "提交中...");
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
    public void successLoad(OrderDetailDataModel.DataBean data) {
        WaitDialog.dismiss();

        name.setText(data.getCustomerName());
        orderNum.setText(data.getCustomerPhone());
        if (data.getCustomerTypeName()==null || data.getCustomerTypeName().equals("")){
            type.setVisibility(View.GONE);
        }
        type.setText(data.getCustomerTypeName());
        address.setText(data.getAddressName());
        addressDetail.setText(data.getAddress());
        timeSend.setText(data.getTimeRange());
        timeOut.setText(data.getTimeStatus());

        tatalPrice.setText("¥" + data.getTotalPrice());
        needPrice.setText("¥" + data.getReceivablePrice());
        orderNumData.setText("订单编号：" + data.getOrderNo());
        recordTime.setText("完成时间：" + data.getCompleteTime());
        if (data.getCompleteTime()==null || data.getCompleteTime().equals("")){
            recordTime.setVisibility(View.GONE);
        }
        orderNo = data.getOrderNo();


        if ("微商城".equals(data.getOrderType())) {
            voidSaleBtn.setVisibility(View.GONE);
        } else if ("电话订单".equals(data.getOrderType())) {
            voidSaleBtn.setVisibility(View.VISIBLE);
        } else {
            voidSaleBtn.setVisibility(View.GONE);
        }

        orderSource.setText("订单来源: " + data.getOrderType());
        payWay.setText("支付方式：" + data.getPayMode());
        orderDetailAdapter.setList(data.getGoodsList());
        actualPrice.setText("¥" + data.getRealPrice());

        timeOut.setVisibility(View.VISIBLE);
        if (data.getDeliveryStatus() == 0) {
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.pend_order));
        } else if (data.getDeliveryStatus() == 1) {
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.nedd_delivery));
        } else if (data.getDeliveryStatus() == 2) {
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.have_finish));
        } else if (data.getDeliveryStatus() == 3) {
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.have_cancel));
        }

        if (id.equals("0")) {
            takeOrder.setText("接单");
        } else if (id.equals("1")) {
            takeOrder.setText("配送");
        }

        //已经接单，需要配送
        if (data.getDeliveryStatus() == 1) {
            voidDeliveryView.setVisibility(View.VISIBLE);
            takeOrder.setVisibility(View.GONE);
        } else {
            voidDeliveryView.setVisibility(View.GONE);
        }
        if (data.getTimeStatus() != null && data.getTimeStatus().contains("已超时") && data.getDeliveryStatus() == 1) {
            voidDeliveryView.setVisibility(View.VISIBLE);
            takeOrder.setVisibility(View.GONE);
        }

        if (data.getDeliveryStatus() == 2) {
            voidDeliveryView.setVisibility(View.GONE);
            takeOrder.setVisibility(View.GONE);
            switchOrder.setVisibility(View.GONE);
        }

        /**
         * [0-待接单 1-已接单 2-已完成 3-已取消]
         */
        if (orderStatus == 2 || orderStatus==3) {
            voidDeliveryView.setVisibility(View.GONE);
            takeOrder.setVisibility(View.GONE);
            switchOrder.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AreaEvent event) {
        if (event != null) {
//            List<AreaModel.DataBean> data = event.dataBean.getData();
//            BottomMenu.show(OrderDetailActivity.this, subAreaList, new OnMenuItemClickListener() {
//                @Override
//                public void onClick(String text, int index) {
//                    int honeycombGridId = data.get(index).getId();
//                    orderDetailPresenter.turnOrder(id,honeycombGridId);
//                }
//            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.type == 2) {
                if (event.status == Config.LOAD_SUCCESS) {
                    Utils.showCenterTomast("定单作废成功");
                    finish();
                } else {
                    Utils.showCenterTomast("定单作废失败");
                }
            } else if (event.type == 1) {
                if (event.status == Config.LOAD_SUCCESS) {
                    Utils.showCenterTomast("接单成功");
                    finish();
                } else {
                    Utils.showCenterTomast("定单作废失败");
                }
            } else if (event.type == 4) {
                if (event.status == Config.LOAD_SUCCESS) {
                    Utils.showCenterTomast("转成功");
                    finish();
                } else {
                    Utils.showCenterTomast("转单失败");
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
        Log.i(TAG, "UpdateOrderEvent====" + typeId);
        EventBus.getDefault().post(new UpdateOrderEvent(typeId + ""));
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void errorLoad(String error) {
        WaitDialog.dismiss();
    }
}
