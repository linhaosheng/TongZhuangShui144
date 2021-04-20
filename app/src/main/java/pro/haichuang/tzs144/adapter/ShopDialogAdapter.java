package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ShopListModel;
import pro.haichuang.tzs144.model.ShopModel;

public class ShopDialogAdapter extends BaseQuickAdapter<ShopListModel.DataBean.DataListBean, BaseViewHolder> {


    private Context context;

    public ShopDialogAdapter(Context context) {
        super(R.layout.item_shop);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShopListModel.DataBean.DataListBean dataBean) {
        ImageView view = baseViewHolder.getView(R.id.check);

        if (dataBean.isCheck()){
            view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check_box));
       }else {
            view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check));
        }
        String shopNum = "库存 ： "+dataBean.getStockNum() +dataBean.getTypeName();
        baseViewHolder.setText(R.id.name,dataBean.getGoodsName())
        .setText(R.id.shop_num,shopNum);
        baseViewHolder.getView(R.id.capacity).setVisibility(View.GONE);
    }
}
