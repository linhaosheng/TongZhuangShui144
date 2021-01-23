package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 客户销售搜索适配器
 */
public class SaleSearcAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SaleSearcAdapter() {
        super(R.layout.item_sale_search);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }

}
