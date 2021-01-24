package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 需求列表适配器
 */
public class ReturnDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ReturnDetailAdapter() {
        super(R.layout.item_return_detail);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
