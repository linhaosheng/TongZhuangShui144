package pro.haichuang.tzs144.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.SaleListModel;

public class SaleListItemAdapter extends BaseQuickAdapter<SaleListModel.DataBean, BaseViewHolder> implements LoadMoreModule {


    public SaleListItemAdapter() {
        super(R.layout.item_sale_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SaleListModel.DataBean dataBean) {

        String order_num = "订单编号："+dataBean.getNo();
        String order_type = "";
        if (dataBean.getOrderType().equals("0")){
            order_type = "直接销售";
        }else if (dataBean.getOrderType().equals("1")){
            order_type = "补录订单";
        }else if (dataBean.getOrderType().equals("2")){
            order_type = "商城订单";
        }else if (dataBean.getOrderType().equals("3")){
            order_type = "电话订单";
        }else if (dataBean.getOrderType().equals("4")){
            order_type = "外卖订单";
        }

        String order_state = "";
        if (dataBean.getOrderStatus()==0){
            order_state = "作废";
            baseViewHolder.getView(R.id.order_state).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.order_state).setVisibility(View.GONE);
        }


        String clinet_name = "客户姓名："+dataBean.getCustomerName();
        String phone = "联系电话："+dataBean.getCustomerPhone();
        String address = "地      址："+dataBean.getAddressName();
        String total_price = "商品总额："+dataBean.getTotalPrice();
        String need_price = "应收金额："+dataBean.getReceivablePrice();
        String actual_price = "实收金额："+dataBean.getRealPrice();
        String time = "录入时间："+dataBean.getCreateTime();
        String sale_distance = "销售距离："+dataBean.getSalesDistance();

        if (dataBean.getCreateTime()==null || dataBean.getCreateTime().equals("")){
            baseViewHolder.getView(R.id.time).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.time).setVisibility(View.VISIBLE);
        }

        baseViewHolder.setText(R.id.order_num,order_num)
                .setText(R.id.order_state,order_state)
                .setText(R.id.order_type,order_type)
                .setText(R.id.clinet_name,clinet_name)
                .setText(R.id.client_type,dataBean.getCustomerType())
                .setText(R.id.phone,phone)
                .setText(R.id.address,address)
                .setText(R.id.address_detail,dataBean.getAddress())
                .setText(R.id.total_price,total_price)
                .setText(R.id.need_price,need_price)
        .setText(R.id.actual_price,actual_price)
        .setText(R.id.time,time)
        .setText(R.id.sale_distance,sale_distance);


        TextView order_num_txt = baseViewHolder.getView(R.id.order_num);
        TextView clinet_name_txt = baseViewHolder.getView(R.id.clinet_name);
        TextView phone_txt = baseViewHolder.getView(R.id.phone);
        TextView address_txt = baseViewHolder.getView(R.id.address);
        TextView address_detail_txt = baseViewHolder.getView(R.id.address_detail);
        TextView pay_way_txt = baseViewHolder.getView(R.id.pay_way);
        TextView total_price_txt = baseViewHolder.getView(R.id.total_price);
        TextView need_price_txt = baseViewHolder.getView(R.id.need_price);
        TextView actual_price_txt = baseViewHolder.getView(R.id.actual_price);
        TextView sale_distance_txt = baseViewHolder.getView(R.id.sale_distance);
        TextView time_txt = baseViewHolder.getView(R.id.time);

        if ("true".equals(dataBean.getIsWaterRead())){
            order_num_txt.setTextColor(Color.RED);
            clinet_name_txt.setTextColor(Color.RED);
            phone_txt.setTextColor(Color.RED);
            address_txt.setTextColor(Color.RED);
            address_detail_txt.setTextColor(Color.RED);
            pay_way_txt.setTextColor(Color.RED);
            total_price_txt.setTextColor(Color.RED);
            need_price_txt.setTextColor(Color.RED);

            actual_price_txt.setTextColor(Color.RED);
            sale_distance_txt.setTextColor(Color.RED);
            time_txt.setTextColor(Color.RED);
        }else {
            order_num_txt.setTextColor(Color.parseColor("#000000"));
            clinet_name_txt.setTextColor(Color.parseColor("#333333"));
            phone_txt.setTextColor(Color.parseColor("#333333"));
            address_txt.setTextColor(Color.parseColor("#333333"));
            address_detail_txt.setTextColor(Color.parseColor("#333333"));
            pay_way_txt.setTextColor(Color.parseColor("#333333"));
            total_price_txt.setTextColor(Color.parseColor("#333333"));
            need_price_txt.setTextColor(Color.parseColor("#333333"));

            actual_price_txt.setTextColor(Color.parseColor("#333333"));
            sale_distance_txt.setTextColor(Color.parseColor("#333333"));
            time_txt.setTextColor(Color.parseColor("#333333"));
        }
    }
}
