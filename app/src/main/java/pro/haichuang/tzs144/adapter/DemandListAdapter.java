package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.GoodBeanModel;

/**
 * 需求列表适配器
 */
public class DemandListAdapter extends BaseQuickAdapter<GoodBeanModel, BaseViewHolder> {


    public DemandListAdapter() {
        super(R.layout.item_demand_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GoodBeanModel baseMedia) {

    }
}
