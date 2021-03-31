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
import pro.haichuang.tzs144.model.WithDrawalOrderModel;
import rxhttp.wrapper.param.IFile;

public class StartDepositSearchAdapter extends BaseQuickAdapter<WithDrawalOrderModel.DataBean, BaseViewHolder> {


    private Context context;

    public StartDepositSearchAdapter(Context context) {
        super(R.layout.item_start_deposit_search);
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
        String deposit_type = "";
        if (dataBean.getType()==0){
            deposit_type = "开押类型 ：押金";
        }else if (dataBean.getType()==1){
            deposit_type = "开押类型 ：借条";
        }else {
            deposit_type = "开押类型 ：暂欠";
        }
        String start_drawal_time = "开押时间 ："+dataBean.getTime();
        String start_drawal_name = "开押人 ："+dataBean.getCreateName();

        String with_drawal_time = "退押时间 ："+dataBean.getReturnTime();
        String with_drawal_name = "退押人 ："+dataBean.getReturnName();

        if (dataBean.getReturnTime()==null || dataBean.getReturnTime().equals("")){
            baseViewHolder.getView(R.id.with_drawal_time).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.with_drawal_time).setVisibility(View.VISIBLE);
        }


        if (dataBean.getReturnName()==null || dataBean.getReturnName().equals("")){
            baseViewHolder.getView(R.id.with_drawal_name).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.with_drawal_name).setVisibility(View.VISIBLE);
        }


        TextView voil_txt = baseViewHolder.getView(R.id.voil_txt);

        ImageView orderStateImg = baseViewHolder.getView(R.id.order_state);
        if (dataBean.getStatus()==0){
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.no_back));
            voil_txt.setVisibility(View.VISIBLE);
        }else {
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.send_back));
            voil_txt.setVisibility(View.GONE);
        }

        baseViewHolder.setText(R.id.deposit_number,deposit_number)
                .setText(R.id.start_code,start_code)
                .setText(R.id.end_code,end_code)
                .setText(R.id.code_time,code_time)
                .setText(R.id.with_drawal_price,with_drawal_price)
                .setText(R.id.with_drawal_num,with_drawal_num)
                .setText(R.id.with_drawal_money,with_drawal_money)
                .setText(R.id.deposit_type,deposit_type)
                .setText(R.id.start_drawal_time,start_drawal_time)
                .setText(R.id.start_drawal_name,start_drawal_name)
        .setText(R.id.with_drawal_time,with_drawal_time)
        .setText(R.id.with_drawal_name,with_drawal_name);
    }
}
