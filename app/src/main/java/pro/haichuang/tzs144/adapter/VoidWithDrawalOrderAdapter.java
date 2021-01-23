package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

public class VoidWithDrawalOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public VoidWithDrawalOrderAdapter() {
        super(R.layout.item_void_with_drawal_order);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
