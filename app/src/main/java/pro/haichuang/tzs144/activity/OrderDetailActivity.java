package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialog.v2.WaitDialog;

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
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.product_name_detail)
    TextView productNameDetail;
    @BindView(R.id.price_num)
    TextView priceNum;
    @BindView(R.id.line)
    View line;

    @BindView(R.id.product_info_view)
    RelativeLayout productInfoView;
    @BindView(R.id.product_name_confirm)
    TextView productNameConfirm;
    @BindView(R.id.product_price_confirm)
    TextView productPriceConfirm;
    @BindView(R.id.product_name_detail_confirm)
    TextView productNameDetailConfirm;
    @BindView(R.id.price_num_confirm)
    TextView priceNumConfirm;
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
    @BindView(R.id.record_persion)
    TextView recordPersion;
    @BindView(R.id.finish_distance)
    TextView finishDistance;
    @BindView(R.id.order_view)
    LinearLayout orderView;
    @BindView(R.id.recycle_data)
    RecyclerView recycleData;
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
        tips.setVisibility(View.VISIBLE);
        tips.setTextSize(12);

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


    @OnClick({R.id.back, R.id.tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tips:
                WaitDialog.show(this,"提交中...");
                orderDetailPresenter.directSelling(id);
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

        tatalPrice.setText("¥" + data.getTotalPrice());
        needPrice.setText("¥" + data.getReceivablePrice());
        orderNumData.setText("订单编号：" + data.getOrderNo());
        recordTime.setText("录入时间：" + data.getTime());
        recordPersion.setText("录入人：" + data.getCreateName());
        finishDistance.setText("录入时与客户距离：" + data.getSalesDistance() + "M");
        orderDetailAdapter.setList(data.getGoodsList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("定单作废成功");
            } else {
                Utils.showCenterTomast("定单作废失败");
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
