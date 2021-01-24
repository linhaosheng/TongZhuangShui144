package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 调拨记录适配器
 */
public class DemandRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public DemandRecordAdapter() {
        super(R.layout.item_demand_record);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
