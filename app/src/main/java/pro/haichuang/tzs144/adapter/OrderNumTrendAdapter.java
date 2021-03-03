package pro.haichuang.tzs144.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientListModel;

/**
 * 客户应收款适配器
 */
public class OrderNumTrendAdapter extends BaseQuickAdapter<ClientListModel.DataBean, BaseViewHolder> {


    public OrderNumTrendAdapter() {
        super(R.layout.item_order_num_trend);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClientListModel.DataBean dataBean) {

        String orderNum = "订单量："+dataBean.getOrderCount();
        String saleCount = "销量："+dataBean.getSaleCount()+"件";

        baseViewHolder.setText(R.id.client_info,dataBean.getCustomerName()+"  "+dataBean.getPhone())
                .setText(R.id.dealer,dataBean.getTypeName())
                .setText(R.id.order_num,orderNum)
                .setText(R.id.sale_num,saleCount)
                .setText(R.id.last_month_num,dataBean.getMonthRatio()+"%")
                .setText(R.id.last_year_num,dataBean.getYearRatio()+"%")
        .setText(R.id.sale_last_month_num,dataBean.getMonthSaleRatio()+"%")
        .setText(R.id.sale_last_year_num,dataBean.getSaleYearRatio()+"%");


        TextView last_month_num = baseViewHolder.getView(R.id.last_month_num);
        TextView last_year_num = baseViewHolder.getView(R.id.last_year_num);
        TextView sale_last_month_num = baseViewHolder.getView(R.id.sale_last_month_num);
        TextView sale_last_year_num = baseViewHolder.getView(R.id.sale_last_year_num);

        if (dataBean.getIsMaintain()){
            baseViewHolder.getView(R.id.order_state).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.order_state).setVisibility(View.GONE);
        }

        if (dataBean.getMonthRatio().contains("+")){
            last_month_num.setTextColor(Color.parseColor("#E02020"));
        }else {
            last_month_num.setTextColor(Color.parseColor("#3C9C25"));
        }

        if (dataBean.getYearRatio().contains("+")){
            last_year_num.setTextColor(Color.parseColor("#E02020"));
        }else {
            last_year_num.setTextColor(Color.parseColor("#3C9C25"));
        }

        if (dataBean.getMonthSaleRatio().contains("+")){
            sale_last_month_num.setTextColor(Color.parseColor("#E02020"));
        }else {
            sale_last_month_num.setTextColor(Color.parseColor("#3C9C25"));
        }

        if (dataBean.getSaleYearRatio().contains("+")){
            sale_last_year_num.setTextColor(Color.parseColor("#E02020"));
        }else {
            sale_last_year_num.setTextColor(Color.parseColor("#3C9C25"));
        }

    }
}
