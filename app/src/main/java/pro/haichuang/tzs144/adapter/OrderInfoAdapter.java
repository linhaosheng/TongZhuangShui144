package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.haichuang.tzs144.R;

public class OrderInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public OrderInfoAdapter() {
        super(R.layout.item_order_info);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
