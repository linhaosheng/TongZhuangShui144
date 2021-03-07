package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;

public class VoidWithDrawalOrderAdapter extends BaseQuickAdapter<WithDrawalOrderModel.DataBean, BaseViewHolder> implements LoadMoreModule {

    private Context context;

    public VoidWithDrawalOrderAdapter(Context context) {
        super(R.layout.item_void_with_drawal_order);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WithDrawalOrderModel.DataBean dataBean) {

        String deposit_number = "押金编号："+dataBean.getYjNo();
        String start_code = "押金本编号："+dataBean.getBookNo();
        String end_code = "客户名称："+dataBean.getKhName();
        String code_time = "开押物品："+dataBean.getGoodsName();
        String with_drawal_price = "开押单价："+dataBean.getPrice();
        String with_drawal_num = "数量 ："+dataBean.getNum();
        String with_drawal_money = "金额 ："+dataBean.getTotalPrice();

        String recode_time = "记录时间 ："+dataBean.getTime();
        String with_drawal_name = "退押人 ："+dataBean.getReturnName();
        String void_time = "作废时间 ："+dataBean.getCancelTime();
        String void_name = "作废人 ："+dataBean.getCreateName();
        String status = "";
        TextView statusTv =  baseViewHolder.getView(R.id.status);
        if (dataBean.isAdd()){
            status = "新增";
            statusTv.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn31));
        }else {
            status = "历史";
            statusTv.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn19));
        }


        baseViewHolder.setText(R.id.deposit_number,deposit_number)
                .setText(R.id.start_code,start_code)
                .setText(R.id.end_code,end_code)
                .setText(R.id.code_time,code_time)
                .setText(R.id.with_drawal_price,with_drawal_price)
                .setText(R.id.with_drawal_num,with_drawal_num)
                .setText(R.id.with_drawal_money,with_drawal_money)
                .setText(R.id.recode_time,recode_time)
                .setText(R.id.with_drawal_name,with_drawal_name)
                .setText(R.id.void_name,void_name)
                .setText(R.id.void_time,void_time)
        .setText(R.id.status,status);
    }
}
