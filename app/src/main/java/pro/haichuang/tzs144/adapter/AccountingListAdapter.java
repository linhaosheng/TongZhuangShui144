package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;

public class AccountingListAdapter  extends BaseQuickAdapter<AccountListModel.DataBean, BaseViewHolder> {


    public AccountingListAdapter() {
        super(R.layout.item_account_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AccountListModel.DataBean dataBean) {

        String order_time = "订单时间段："+dataBean.getOrderTime();
        String time_send = "订单总金额："+dataBean.getPrice();
        String name_info = "结账人 : "+dataBean.getRealName();
        String end_time = "结账时间："+dataBean.getTime();
        baseViewHolder.setText(R.id.order_time,order_time)
                .setText(R.id.time_send,time_send)
                .setText(R.id.name_info,name_info)
                .setText(R.id.end_time,end_time);

        /**
         *  1 表示已结账，2 已收账，3 表示已压帐
         */
        if (dataBean.getSettleStatus()==1){
            baseViewHolder.getView(R.id.write_off).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.write_off).setVisibility(View.GONE);
        }
    }
}
