package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientDetailModel;

public class AddressListAdapter extends BaseQuickAdapter<ClientDetailModel.DataBean.AddressListBean, BaseViewHolder> {


    public AddressListAdapter() {
        super(R.layout.item_address_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClientDetailModel.DataBean.AddressListBean data) {
        int position = baseViewHolder.getPosition();
        if (position==0){
            baseViewHolder.getView(R.id.default_address).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.default_address).setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.address,data.getAddressName())
                .setText(R.id.address_detail,data.getAddress());

        if (data.isUpadteAddress()){
            baseViewHolder.getView(R.id.update_address_view).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.default_address).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.update_address_view).setVisibility(View.GONE);
            if (position==0){
                baseViewHolder.getView(R.id.default_address).setVisibility(View.VISIBLE);
            }
        }

        if (data.isEdit()){
            baseViewHolder.getView(R.id.edit_linview).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.edit_linview).setVisibility(View.GONE);
        }

    }
}
