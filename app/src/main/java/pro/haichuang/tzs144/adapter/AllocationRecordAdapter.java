package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 调拨记录适配器
 */
public class AllocationRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public AllocationRecordAdapter() {
        super(R.layout.item_allocation_record);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
