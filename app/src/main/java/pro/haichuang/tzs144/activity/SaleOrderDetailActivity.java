package pro.haichuang.tzs144.activity;


import android.graphics.Color;
import android.util.Log;
import android.view.View;
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
import butterknife.OnClick;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.adapter.OrderDetailAdapter;
import pro.haichuang.tzs144.iview.ILoadDataView;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.model.RefreshEvent;
import pro.haichuang.tzs144.model.StatusEvent;
import pro.haichuang.tzs144.presenter.OrderDetailPresenter;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;

/**
 * 直接销售订单详情
 */
public class SaleOrderDetailActivity extends BaseActivity implements ILoadDataView<OrderDetailModel.DataBean> {


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
    @BindView(R.id.name_view)
    RelativeLayout nameView;

    @BindView(R.id.tatal_price)
    TextView tatalPrice;
    @BindView(R.id.need_price)
    TextView needPrice;
    @BindView(R.id.actual_price)
    TextView actualPrice;
    @BindView(R.id.price_view)
    LinearLayout priceView;
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

    @BindView(R.id.deposit_info_view)
    LinearLayout deposit_info_view;
    @BindView(R.id.deposit_num)
    TextView deposit_num;
    @BindView(R.id.deposit_money_num)
    TextView deposit_money_num;
    @BindView(R.id.deposit_name)
    TextView deposit_name;
    @BindView(R.id.deposit_total)
    TextView deposit_total;
    @BindView(R.id.deposit_person)
    TextView deposit_person;
    @BindView(R.id.deposit_time)
    TextView deposit_time;
    @BindView(R.id.remark_info)
    TextView remark_info;

    private OrderDetailAdapter orderDetailAdapter;



    private OrderDetailPresenter orderDetailPresenter;
    private  String id;
    private int settleStatus;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_sale_order_detail;
    }

    @Override
    protected void setUpView() {
        title.setText("订单详情");
        tips.setText("作废");
        tips.setTextSize(14);
        tips.setTextColor(Color.parseColor("#3F3F3F"));

        orderDetailAdapter = new OrderDetailAdapter();
        recycleData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycleData.setAdapter(orderDetailAdapter);
    }

    @Override
    protected void setUpData() {
        id = getIntent().getStringExtra("id");
        settleStatus = getIntent().getIntExtra("settleStatus",0);
        if (settleStatus!=0){
            tips.setVisibility(View.GONE);
        }else {
            tips.setVisibility(View.VISIBLE);
        }
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
                if (settleStatus!=0){
                    Utils.showCenterTomast("只能作废未结账的订单");
                    return;
                }
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
        if (data.getCustomerTypeName()==null || data.getCustomerTypeName().equals("")){
            type.setVisibility(View.GONE);
        }
        type.setText(data.getCustomerTypeName());
        address.setText(data.getAddressName());
        addressDetail.setText(data.getAddress());

        tatalPrice.setText("商品金额: ¥ " + data.getTotalPrice());
        needPrice.setText("应收金额: ¥ " + data.getReceivablePrice());
        actualPrice.setText("实收金额: ¥ "+data.getRealPrice());
        orderNumData.setText("订单编号：" + data.getOrderNo());
        recordTime.setText("录入时间：" + data.getTime());
        recordPersion.setText("录入人：" + data.getCreateName());
        finishDistance.setText("录入时与客户距离：" + data.getSalesDistance() + "M");
        remark_info.setText("备注信息: "+data.getOrderDesc());
        orderDetailAdapter.setList(data.getGoodsList());

        if (data.getOrderStatus()==1){
            tips.setVisibility(View.GONE);
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.void_state));
        }else {
            tips.setVisibility(View.VISIBLE);
        }

        if (data.getKyInfo()!=null){
            OrderDetailModel.DataBean.KyInfo kyInfo = data.getKyInfo();
            deposit_info_view.setVisibility(View.VISIBLE);
            deposit_num.setText("押金本编号 : "+kyInfo.getBookNo());
            deposit_money_num.setText(("押金编号 : "+kyInfo.getYjNo()));
            deposit_name.setText("物品名称 : "+kyInfo.getGoodsName());
            deposit_total.setText("总价 : "+kyInfo.getYjTotalPrice());
            deposit_person.setText("开押人 : "+kyInfo.getKyName());
            deposit_time.setText("退押时间 : "+kyInfo.getTyTime());
        }else {
            deposit_info_view.setVisibility(View.GONE);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StatusEvent event) {
        WaitDialog.dismiss();
        if (event != null) {
            if (event.status == Config.LOAD_SUCCESS) {
                Utils.showCenterTomast("订单作废成功");
                tips.setVisibility(View.GONE);
                orderStateImg.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.void_state));
                EventBus.getDefault().post(new RefreshEvent("refresh",0));
            } else {
                Utils.showCenterTomast("订单作废失败 :" +event.result);
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
        Utils.showCenterTomast(error);
    }
}
