package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.haichuang.tzs144.R;

public class OrderInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int type;

    public OrderInfoAdapter(int mType) {
        super(R.layout.item_order_info);
        this.type = mType;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

        if (type==0){
            baseViewHolder.setText(R.id.take_orders,"抢单/接单");
        }else if (type==1){
            baseViewHolder.setText(R.id.take_orders,"配送");
        }else if (type==2 || type==3){
            baseViewHolder.setText(R.id.take_orders,"知道了");
        }
    }
}
