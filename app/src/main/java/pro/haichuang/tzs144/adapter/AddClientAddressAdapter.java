package pro.haichuang.tzs144.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AddressBean;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

public class AddClientAddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {


    private LSettingItemClickListner lSettingItemClickListner;

    public AddClientAddressAdapter(LSettingItemClickListner mLSettingItemClickListner) {
        super(R.layout.item_add_client_address);
        this.lSettingItemClickListner = mLSettingItemClickListner;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddressBean item) {
        LSettingItem address = helper.getView(R.id.address_name);
        if (item.getAddress()!=null){
            address.setEditinput(item.getAddressName());
        }else {
            address.setEditinput("");
        }
        LSettingItem detailAddress = helper.getView(R.id.detail_address);

        detailAddress.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                if (lSettingItemClickListner!=null){
                    lSettingItemClickListner.itemClick(helper.getPosition());
                }
            }
        });
        if (item.getAddressInfo()!=null){
            detailAddress.setRightText(item.getAddressInfo() +" "+item.getAddress());
        }else {
            detailAddress.setRightText("请选择");
        }

        LSettingItem address_detail = helper.getView(R.id.address_detail);
        address_detail.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                if (lSettingItemClickListner!=null){
                    int position = helper.getPosition();
                    lSettingItemClickListner.editText(position,text);
                }
            }
        });
        if (item.getAddressDetail()!=null){
            address_detail.setEditinput(item.getAddressDetail());
        }else {
            address_detail.setEditinput("");
        }
    }

    public static interface LSettingItemClickListner{
        void itemClick(int position);
        void editText(int position,String text);
    }
}
