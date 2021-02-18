package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.SaleListModel;

public class SaleListItemAdapter extends BaseQuickAdapter<SaleListModel.DataBean, BaseViewHolder> {


    public SaleListItemAdapter() {
        super(R.layout.item_sale_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SaleListModel.DataBean dataBean) {

        String order_num = "订单编号："+dataBean.getId();
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
        String clinet_name = "客户姓名："+dataBean.getCustomerName();
        String phone = "联系电话："+dataBean.getCustomerPhone();
        String address = "地      址："+dataBean.getAddressName();
        String total_price = "商品总额："+dataBean.getTotalPrice();
        String need_price = "应收金额："+dataBean.getReceivablePrice();
        String actual_price = "实收金额："+dataBean.getRealPrice();
        String time = "录入时间："+dataBean.getCreateTime();
        String sale_distance = "销售距离: "+dataBean.getSalesDistance();

        baseViewHolder.setText(R.id.order_num,order_num)
                .setText(R.id.order_state,order_state)
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




    }
}
