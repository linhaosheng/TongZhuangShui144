package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListDetailModel;

public class AccountingListDetailAdapter extends BaseQuickAdapter<AccountListDetailModel.DataBean.ListBean, BaseViewHolder> {


    public AccountingListDetailAdapter() {
        super(R.layout.item_account_list_detail);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AccountListDetailModel.DataBean.ListBean dataBean) {

        String order_type  = "";
        if (dataBean.getType()==0){
            order_type = "直接销售";
        }else if (dataBean.getType()==1) {
            order_type = "补录订单";
        }else if (dataBean.getType()==2) {
            order_type = "商城订单";
        }else if (dataBean.getType()==3) {
            order_type = "电话订单";
        }else if (dataBean.getType()==4) {
            order_type = "外卖订单";
        }
        String info = dataBean.getCouponNum()+"  "+dataBean.getPhone();
        String order_inconme  = "商品总额:   "+dataBean.getTotalPrice()+"元   应收收入:   "+dataBean.getReceivablePrice()+"元   实际收入:    "+dataBean.getRealPrice()+"元";
        String cash = "现金 (元) : "+dataBean.getXjPrice();
        String water_tickets = "水票 (张) : "+dataBean.getWaterNum();
        String reward_tickets = "奖券 (元) : "+dataBean.getCouponNum();
        String monthly = "月结 (元) : "+dataBean.getMonthNum();
        String name_info = dataBean.getCustomerName()+"  "+dataBean.getPhone();

        baseViewHolder.setText(R.id.order_num,dataBean.getNo())
                .setText(R.id.order_type,order_type)
                .setText(R.id.order_contact,info)
                .setText(R.id.client_type,dataBean.getCustomerType())
                .setText(R.id.order_inconme,order_inconme)
                .setText(R.id.cash,cash)
                .setText(R.id.order_contact,name_info)
                .setText(R.id.water_tickets,water_tickets)
                .setText(R.id.reward_tickets,reward_tickets)
                .setText(R.id.monthly,monthly);

    }
}
