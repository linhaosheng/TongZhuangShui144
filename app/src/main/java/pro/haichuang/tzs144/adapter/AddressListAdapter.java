package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

public class AddressListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public AddressListAdapter() {
        super(R.layout.item_address_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {
        int position = baseViewHolder.getPosition();
        if (position==0){
            baseViewHolder.getView(R.id.default_address).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.default_address).setVisibility(View.GONE);
        }

    }
}
