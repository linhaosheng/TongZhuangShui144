package pro.haichuang.tzs144.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.OrderInfoModel;
import pro.haichuang.tzs144.model.ShopDetailModel;
import pro.haichuang.tzs144.util.Utils;

public class ShopDetailAdapter extends BaseQuickAdapter<ShopDetailModel, BaseViewHolder> {


    public ShopDetailAdapter() {
        super(R.layout.item_shop_detail);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ShopDetailModel dataBean) {

        if (baseViewHolder.getPosition()==0){
            baseViewHolder.getView(R.id.shop_type).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.shop_type).setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.shop_type,dataBean.getTitle())
        .setText(R.id.shop_name,dataBean.getName())
        .setText(R.id.shop_unit,dataBean.getUnit())
        .setText(R.id.shop_price,dataBean.getPrice());

        ImageView shopImg = baseViewHolder.getView(R.id.shop_img);

        Utils.showRectangleImage(shopImg,dataBean.getImage_url());

    }
}
