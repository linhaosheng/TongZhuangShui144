package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.AddressBean;

public class AddressSearchAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {


    private Context context;

    public AddressSearchAdapter(Context mContext) {
        super(R.layout.item_address);
        this.context = mContext;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,AddressBean dataBean) {
        ImageView view = baseViewHolder.getView(R.id.check);
      if (dataBean.isSelect()){
          view.setVisibility(View.VISIBLE);
          view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check_box));
      }else {
          view.setVisibility(View.GONE);
      }

      baseViewHolder.setText(R.id.address,dataBean.getAddress())
              .setText(R.id.address_detail,dataBean.getAddressInfo());
    }
}
