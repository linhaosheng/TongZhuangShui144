package pro.haichuang.tzs144.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.TrendModel;

/**
 * 客户实时数据中的走势
 */
public class OrderTrendAdapter extends BaseQuickAdapter<TrendModel, BaseViewHolder> {


    public OrderTrendAdapter() {
        super(R.layout.item_order_trend);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TrendModel trendModel) {
       baseViewHolder.setText(R.id.text_name,trendModel.name)
               .setText(R.id.text_num,trendModel.num)
       .setText(R.id.last_tend,trendModel.getLastOne())
       .setText(R.id.tend_week,trendModel.getLastTwo());

        TextView lastTrend = baseViewHolder.getView(R.id.last_tend);
        TextView tendWeek = baseViewHolder.getView(R.id.tend_week);

       if (trendModel.getLastOne().contains("+")){
            lastTrend.setTextColor(Color.parseColor("#E02020"));
        }else {
            lastTrend.setTextColor(Color.parseColor("#3C9C25"));
        }

        if (trendModel.getLastTwo().contains("+")){
            tendWeek.setTextColor(Color.parseColor("#E02020"));
        }else {
            tendWeek.setTextColor(Color.parseColor("#3C9C25"));
        }
    }
}
