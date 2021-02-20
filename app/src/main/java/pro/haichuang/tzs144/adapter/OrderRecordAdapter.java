package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.OrderRecordModel;

public class OrderRecordAdapter extends BaseQuickAdapter<OrderRecordModel.DataBean.OrderListBean, BaseViewHolder> {


    private Context context;

    public OrderRecordAdapter(Context context) {
        super(R.layout.item_order_record);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderRecordModel.DataBean.OrderListBean dataBean) {

        ImageView orderStateImg = baseViewHolder.getView(R.id.order_state_img);
        if (dataBean.getStatus()==0){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.have_finish));
        }else if (dataBean.getStatus()==2){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.have_cancel));
        }

        String order_num = "订单编号："+dataBean.getOrderNo();
        String order_state = "";
        if (dataBean.getOrderType().equals("0")){
            order_state = "直接销售";
        }else if (dataBean.getOrderType().equals("1")){
            order_state = "补录订单";
        }else if (dataBean.getOrderType().equals("2")){
            order_state = "商城订单";
        }else if (dataBean.getOrderType().equals("3")){
            order_state = "电话订单";
        }else if (dataBean.getOrderType().equals("4")){
            order_state = "外卖订单";
        }
        String time_send = dataBean.getTimeTitle()+ " "+dataBean.getTimeRange() +" 送达";
        String name_info = dataBean.getCustoemrName() + "  "+ dataBean.getCustomerPhone();
        String distance = "距离"+dataBean.getDistance()+"km";
        String order_detail_info = "共"+dataBean.getGoodsTypeNum()+"种商品，合计"+dataBean.getGoodsNum()+"件 ｜ "+ dataBean.getGoodsTopInfo()+ " ↓";

        baseViewHolder.setText(R.id.order_num,order_num)
                .setText(R.id.shop_order,order_state)
                .setText(R.id.time_send,time_send)
                .setText(R.id.timeout,dataBean.getTimeStatus())
                .setText(R.id.name_info,name_info)
                .setText(R.id.dealer,dataBean.getCustomerType())
                .setText(R.id.address,dataBean.getAddress())
                .setText(R.id.distance,distance)
                .setText(R.id.order_detail_info,order_detail_info);

    }
}
