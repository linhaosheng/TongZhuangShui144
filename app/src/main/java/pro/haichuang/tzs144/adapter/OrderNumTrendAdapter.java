package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 客户应收款适配器
 */
public class OrderNumTrendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OrderNumTrendAdapter() {
        super(R.layout.item_order_num_trend);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
