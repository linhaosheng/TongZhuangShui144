package pro.haichuang.tzs144.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.haichuang.tzs144.R;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity {


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
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.time_send)
    TextView timeSend;
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
    @BindView(R.id.ticket_num)
    TextView ticketNum;
    @BindView(R.id.mortgage)
    TextView mortgage;
    @BindView(R.id.reward_ticket)
    TextView rewardTicket;
    @BindView(R.id.ticket_img)
    ImageView ticketImg;
    @BindView(R.id.mortgage2)
    TextView mortgage2;
    @BindView(R.id.month_ticket)
    TextView monthTicket;
    @BindView(R.id.month_img)
    ImageView monthImg;
    @BindView(R.id.mortgage3)
    TextView mortgage3;
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
    @BindView(R.id.order_source)
    TextView orderSource;
    @BindView(R.id.pay_way)
    TextView payWay;
    @BindView(R.id.finish_time)
    TextView finishTime;
    @BindView(R.id.order_view)
    LinearLayout orderView;
    @BindView(R.id.take_orders)
    Button takeOrders;
    @BindView(R.id.transfer_order)
    Button transferOrder;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {

    }


    @OnClick({R.id.back, R.id.take_orders, R.id.transfer_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.take_orders:
                break;
            case R.id.transfer_order:
                break;
        }
    }
}
