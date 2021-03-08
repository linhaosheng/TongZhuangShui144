package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;

public class AddWithDrawalOrderAdapter extends BaseQuickAdapter<WithDrawalOrderModel.DataBean, BaseViewHolder> {


    private Context context;

    public AddWithDrawalOrderAdapter(Context context) {
        super(R.layout.item_add_with_drawal_order);
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
        String with_drawal_time = "开押时间 ："+dataBean.getTime();
        String with_drawal_persion = "开押人 ："+dataBean.getCreateName();
        String with_drawal_name = "退押人 ："+dataBean.getReturnName();
        String with_drawal_end_time = "退押时间 ："+dataBean.getReturnTime();

        baseViewHolder.setText(R.id.deposit_number,deposit_number)
                .setText(R.id.start_code,start_code)
                .setText(R.id.end_code,end_code)
                .setText(R.id.code_time,code_time)
                .setText(R.id.with_drawal_price,with_drawal_price)
                .setText(R.id.with_drawal_num,with_drawal_num)
                .setText(R.id.with_drawal_money,with_drawal_money)
                .setText(R.id.deposit_type,deposit_type)
                .setText(R.id.with_drawal_time,with_drawal_time)

        .setText(R.id.with_drawal_persion,with_drawal_persion)
        .setText(R.id.with_drawal_end_time,with_drawal_end_time);

        ImageView checkStateImh = baseViewHolder.getView(R.id.check_state);
        if (dataBean.isChecked()){
            checkStateImh.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check_box));
        }else {
            checkStateImh.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check));
        }
    }
}
