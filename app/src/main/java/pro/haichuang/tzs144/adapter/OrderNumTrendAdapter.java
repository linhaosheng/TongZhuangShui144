package pro.haichuang.tzs144.adapter;

import android.view.View;

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
                .setText(R.id.last_month_num,String.valueOf(dataBean.getMonthRatio() ))
                .setText(R.id.last_year_num,String.valueOf(dataBean.getYearRatio()))
        .setText(R.id.sale_last_month_num,String.valueOf(dataBean.getMonthSaleRatio()))
        .setText(R.id.sale_last_year_num,String.valueOf(dataBean.getSaleYearRatio()));

        if (dataBean.getIsMaintain()){
            baseViewHolder.getView(R.id.order_state).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.order_state).setVisibility(View.GONE);
        }

    }
}
