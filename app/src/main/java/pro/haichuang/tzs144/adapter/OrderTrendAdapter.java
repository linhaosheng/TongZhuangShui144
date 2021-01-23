package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 客户实时数据中的走势
 */
public class OrderTrendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OrderTrendAdapter() {
        super(R.layout.item_order_trend);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
