package pro.haichuang.tzs144.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.InventoryNumModel;
import pro.haichuang.tzs144.util.Utils;

public class InventoryNumAdapter extends BaseQuickAdapter<InventoryNumModel.DataBean.ListBean, BaseViewHolder> {


    public InventoryNumAdapter() {
        super(R.layout.item_order_inventory_num);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InventoryNumModel.DataBean.ListBean dataBean) {

        ImageView view = baseViewHolder.getView(R.id.bottled_water);
        Utils.showImage(view,dataBean.getImg());

        String num = "库存:"+dataBean.getStockNum()+"        月售:"+dataBean.getSalesNum();
        baseViewHolder.setText(R.id.name,dataBean.getGoodsName() +" "+dataBean.getSpecsName())
                .setText(R.id.bottled_water_num,num);

    }
}
