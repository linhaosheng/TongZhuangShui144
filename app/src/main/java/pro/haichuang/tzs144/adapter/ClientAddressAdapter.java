package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

/**
 * 客户应收款适配器
 */
public class ClientAddressAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ClientAddressAdapter() {
        super(R.layout.item_client_address);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
