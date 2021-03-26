package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountOrderModel;

/**
 * 客户应收款适配器
 */
public class OrderPaymentAdapter extends BaseQuickAdapter<AccountOrderModel.DataBean, BaseViewHolder> implements LoadMoreModule {


    public OrderPaymentAdapter() {
        super(R.layout.item_order_payment);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AccountOrderModel.DataBean dataBean) {
        String order_type  = "";
        if (dataBean.getType().equals("0")){
            order_type = "直接销售";
        }else if (dataBean.getType().equals("1")) {
            order_type = "补录订";
        }else if (dataBean.getType().equals("2")) {
            order_type = "商城订单";
        }else if (dataBean.getType().equals("3")) {
            order_type = "电话订单";
        }else if (dataBean.getType().equals("4")) {
            order_type = "外卖订单";
        }
        String info = dataBean.getCustomerName()+"  "+dataBean.getPhone();
        String order_inconme = "";
        if (dataBean.getTotalPrice()>0){
            order_inconme = "商品总额:   "+dataBean.getTotalPrice()+"应收收入:   "+dataBean.getReceivablePrice()+"元   实际收入:    "+dataBean.getRealPrice()+"元";
        }else {
            order_inconme = "应收收入:   "+dataBean.getReceivablePrice()+"元   实际收入:    "+dataBean.getRealPrice()+"元";
        }

        String cash = "现金 (元) :  "+dataBean.getXjPrice();
        String water_tickets = "水票 (张) :  "+dataBean.getWaterNum();
        String reward_tickets = "奖券 (元) :  "+dataBean.getCouponNum();
        String monthly = "月结 (元) :  "+dataBean.getMonthNum();

        String  orderNo = "订单编号 : "+dataBean.getNo();
           baseViewHolder.setText(R.id.order_num,orderNo)
                   .setText(R.id.order_type,order_type)
                   .setText(R.id.order_contact,info)
                   .setText(R.id.client_type,dataBean.getCustomerType())
                   .setText(R.id.order_inconme,order_inconme)
                   .setText(R.id.cash,cash)
           .setText(R.id.water_tickets,water_tickets)
           .setText(R.id.reward_tickets,reward_tickets)
           .setText(R.id.monthly,monthly);

           if (dataBean.getSettleStatus()==0){
               baseViewHolder.getView(R.id.void_order).setVisibility(View.VISIBLE);
           }else {
               baseViewHolder.getView(R.id.void_order).setVisibility(View.GONE);
           }

           if (dataBean.getDeliveryTime()!=null && dataBean.getDeliveryTime().length()>0){
               String deleve_date = "配送日期："+dataBean.getDeliveryTime();
               baseViewHolder.setText(R.id.deleve_date,deleve_date);
               baseViewHolder.getView(R.id.deleve_date).setVisibility(View.VISIBLE);
           }else {
               baseViewHolder.getView(R.id.deleve_date).setVisibility(View.GONE);
           }

        if (dataBean.getSettleTime()!=null && dataBean.getSettleTime().length()>0){
            String settle_date = "结账日期："+dataBean.getDeliveryTime();
            baseViewHolder.setText(R.id.settle_date,settle_date);
            baseViewHolder.getView(R.id.settle_date).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.settle_date).setVisibility(View.GONE);
        }

    }
}
