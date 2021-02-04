package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientTypeModel;
import pro.haichuang.tzs144.model.ShopModel;

public class AddShopDialogAdapter extends BaseQuickAdapter<ShopModel.DataBean, BaseViewHolder> {


    private Context context;

    public AddShopDialogAdapter(Context context) {
        super(R.layout.item_add_shop);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShopModel.DataBean dataBean) {
        ImageView view = baseViewHolder.getView(R.id.check);

        if (dataBean.isCheck()){
            view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check_box));
       }else {
            view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check));
        }
        baseViewHolder.setText(R.id.name,dataBean.getName())
        .setText(R.id.capacity,dataBean.getSpecs());
    }
}
