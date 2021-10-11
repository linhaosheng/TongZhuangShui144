package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;

public class WithDrawalOrderAdapter extends BaseQuickAdapter<WithDrawalOrderModel.DataBean, BaseViewHolder> implements LoadMoreModule {


    private Context context;

    public WithDrawalOrderAdapter(Context context) {
        super(R.layout.item_with_drawal_order);
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
        String with_drawal_time = "记录时间 ："+dataBean.getReturnTime();
        String with_drawal_name = "退押人 ："+dataBean.getReturnName();

        String with_drawal_money_data = "退押金额 : "+dataBean.getReturnPrice();
        String with_drawal_num_data = "退押数量 : "+dataBean.getReturnCount();
        String with_buy_num_data = "买断数量 : "+dataBean.getMdCount();
        String loss_num_data = "损耗费 : "+dataBean.getHsPrice();

        ImageView orderStateImg = baseViewHolder.getView(R.id.order_state);
        TextView voilTxt = baseViewHolder.getView(R.id.voil_txt);

        if (dataBean.getStatus()==0){
            voilTxt.setVisibility(View.VISIBLE);
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.no_back));
        }else {
            voilTxt.setVisibility(View.VISIBLE);
            orderStateImg.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.send_back));
        }

        TextView stateTxt = baseViewHolder.getView(R.id.state);
        String state;
        if (dataBean.isAdd()){
            state = "新增";
            stateTxt.setTextColor(Color.parseColor("#FFFFFF"));
            stateTxt.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn53));
        }else {
            state = "历史";
            stateTxt.setTextColor(Color.parseColor("#999999"));
            stateTxt.setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn52));
        }
        stateTxt.setText(state);

        baseViewHolder.setText(R.id.deposit_number,deposit_number)
                .setText(R.id.start_code,start_code)
                .setText(R.id.end_code,end_code)
                .setText(R.id.code_time,code_time)
                .setText(R.id.with_drawal_price,with_drawal_price)
                .setText(R.id.with_drawal_num,with_drawal_num)
                .setText(R.id.with_drawal_money,with_drawal_money)
                .setText(R.id.deposit_type,deposit_type)
                .setText(R.id.with_drawal_time,with_drawal_time)
                .setText(R.id.with_drawal_name,with_drawal_name)
        .setText(R.id.with_drawal_money_data,with_drawal_money_data)
        .setText(R.id.with_drawal_num_data,with_drawal_num_data)
        .setText(R.id.with_buy_num_data,with_buy_num_data)
        .setText(R.id.loss_num_data,loss_num_data);

        if (dataBean.getReturnName()==null){
          baseViewHolder.getView(R.id.with_drawal_name).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.with_drawal_name).setVisibility(View.VISIBLE);
        }
    }
}
