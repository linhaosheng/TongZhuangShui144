package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.activity.AddClientActivity;
import pro.haichuang.tzs144.activity.AddressSearchActivity;
import pro.haichuang.tzs144.activity.ClientDetailActivity;
import pro.haichuang.tzs144.model.ClientDetailModel;
import pro.haichuang.tzs144.util.Config;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

public class AddressListAdapter extends BaseQuickAdapter<ClientDetailModel.DataBean.AddressListBean, BaseViewHolder> {


    private ClientDetailActivity context;
    private AddressNameListener addressNameListener;
    public AddressListAdapter(ClientDetailActivity context,AddressNameListener addressNameListener) {
        super(R.layout.item_address_list);
        this.context = context;
        this.addressNameListener = addressNameListener;
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

        if (data.isAddAddress()){
            baseViewHolder.getView(R.id.address).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.address_detail).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.address).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.address_detail).setVisibility(View.VISIBLE);
        }

        LSettingItem detail_address = baseViewHolder.getView(R.id.detail_address);

        if (data.getNewAddress()!=null && !"".equals(data.getNewAddress())){
            detail_address.setEditInfo(data.getNewAddress());
        }

        detail_address.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                Intent intent = new Intent(context, AddressSearchActivity.class);
                if (data.getLatitude()==null || "".equals(data.getLatitude()) || data.getLongitude()==null || "".equals(data.getLongitude()) ){
                    context.startActivityForResult(intent, position);
                }else {
                    String addressJson = Utils.gsonInstane().toJson(data);
                    intent.putExtra("addressJson",addressJson);
                    context.startActivityForResult(intent, position);
                }
            }
        });

        detail_address.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                if (addressNameListener!=null){
                    addressNameListener.addressDetail(baseViewHolder.getPosition(),text);
                }
            }
        });

        LSettingItem address_name = baseViewHolder.getView(R.id.address_name);
        if (data.getNewAddressName()!=null && !"".equals(data.getNewAddressName())){
            address_name.setEditinput(data.getNewAddressName());
        }
        address_name.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                if (addressNameListener!=null){
                    addressNameListener.addressName(baseViewHolder.getPosition(),text);
                }
            }
        });
    }

    public static interface AddressNameListener{
        void addressName(int position,String name);
        void addressDetail(int position,String detail);
    }
}
