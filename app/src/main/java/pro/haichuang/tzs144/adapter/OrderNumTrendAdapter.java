package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientListModel;

/**
 * 客户应收款适配器
 */
public class OrderNumTrendAdapter extends BaseQuickAdapter<ClientListModel.DataBean, BaseViewHolder> implements LoadMoreModule {

    private Context context;

    public OrderNumTrendAdapter(Context context) {
        super(R.layout.item_order_num_trend);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClientListModel.DataBean dataBean) {

        String orderNum = "订单量："+dataBean.getOrderCount();
        String saleCount = "销量："+dataBean.getSaleCount()+"件";

        TextView last_month_num = baseViewHolder.getView(R.id.last_month_num);
        TextView last_year_num = baseViewHolder.getView(R.id.last_year_num);
        TextView sale_last_month_num = baseViewHolder.getView(R.id.sale_last_month_num);
        TextView sale_last_year_num = baseViewHolder.getView(R.id.sale_last_year_num);

        if (dataBean.getIsMaintain()){
            baseViewHolder.getView(R.id.order_state).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.order_state).setVisibility(View.GONE);
        }
        Drawable upDrawable = ContextCompat.getDrawable(context, R.mipmap.trend_up);
        upDrawable.setBounds(0,0,upDrawable.getMinimumWidth(),upDrawable.getMinimumHeight());

        Drawable dowmDrawable = ContextCompat.getDrawable(context, R.mipmap.trend_down);
        dowmDrawable.setBounds(0,0,upDrawable.getMinimumWidth(),upDrawable.getMinimumHeight());

        if (dataBean.getMonthRatio().contains("+")){
            last_month_num.setTextColor(Color.parseColor("#3C9C25"));
            last_month_num.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            last_month_num.setTextColor(Color.parseColor("#E02020"));
            last_month_num.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        if (dataBean.getYearRatio().contains("+")){
            last_year_num.setTextColor(Color.parseColor("#3C9C25"));
            last_year_num.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            last_year_num.setTextColor(Color.parseColor("#E02020"));
            last_year_num.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        if (dataBean.getMonthSaleRatio().contains("+")){
            sale_last_month_num.setTextColor(Color.parseColor("#3C9C25"));
            sale_last_month_num.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            sale_last_month_num.setTextColor(Color.parseColor("#E02020"));
            sale_last_month_num.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        if (dataBean.getSaleYearRatio().contains("+")){
            sale_last_year_num.setTextColor(Color.parseColor("#3C9C25"));
            sale_last_year_num.setCompoundDrawables(upDrawable,null,null,null);
        }else {
            sale_last_year_num.setTextColor(Color.parseColor("#E02020"));
            sale_last_year_num.setCompoundDrawables(dowmDrawable,null,null,null);
        }

        String monthRatio = dataBean.getMonthRatio().replace("+","").replace("-","");
        String yearRatio = dataBean.getYearRatio().replace("+","").replace("-","");
        String monthSaleRatio = dataBean.getMonthSaleRatio().replace("+","").replace("-","");

        String saleYearRatio = dataBean.getSaleYearRatio().replace("+","").replace("-","");

        baseViewHolder.setText(R.id.client_info,dataBean.getCustomerName()+"  "+dataBean.getPhone())
                .setText(R.id.dealer,dataBean.getTypeName())
                .setText(R.id.order_num,orderNum)
                .setText(R.id.sale_num,saleCount)
                .setText(R.id.last_month_num,monthRatio+"%")
                .setText(R.id.last_year_num,yearRatio+"%")
                .setText(R.id.sale_last_month_num,monthSaleRatio+"%")
                .setText(R.id.sale_last_year_num,saleYearRatio+"%");

    }
}
