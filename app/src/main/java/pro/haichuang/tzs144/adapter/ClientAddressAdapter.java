package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.SaleDataModel;

/**
 * 客户应收款适配器
 */
public class ClientAddressAdapter extends BaseQuickAdapter<SaleDataModel.DataBean, BaseViewHolder> {

    private Context context;


    public ClientAddressAdapter(Context context) {
        super(R.layout.item_client_address);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SaleDataModel.DataBean dataModel) {

        baseViewHolder.setText(R.id.address,dataModel.getAddressName())
                .setText(R.id.address_detail,dataModel.getAddress());

        ImageView selectState = baseViewHolder.getView(R.id.select_state);

        if (baseViewHolder.getPosition()==0){
            baseViewHolder.getView(R.id.default_txt).setVisibility(View.VISIBLE);
            selectState.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check_box));
        }else {
            baseViewHolder.getView(R.id.default_txt).setVisibility(View.GONE);
            selectState.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check));
        }
    }
}
