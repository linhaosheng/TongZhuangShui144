package pro.haichuang.tzs144.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.util.Utils;

public class DeliverOrderDetailAdapter extends BaseQuickAdapter<OrderDetailModel.DataBean.GoodsListBean, BaseViewHolder> {


    public DeliverOrderDetailAdapter() {
        super(R.layout.item_deliver_order_detail);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderDetailModel.DataBean.GoodsListBean item) {
        String priceNum = "¥"+item.getGoodsPrice()+"x"+item.getGoodsNum();
        String totalPrice = "¥"+item.getGoodsPrice()*item.getGoodsNum();

        helper.setText(R.id.name,item.getGoodsName())
                .setText(R.id.total_price_num,priceNum)
                .setText(R.id.total_price,totalPrice);

        List<OrderDetailModel.DataBean.BindMaterList> bindMaterList = item.getBindMaterList();
        try {
            helper.setText(R.id.recycle_type,bindMaterList.get(0).getName())
            .setText(R.id.shop_num_tong,item.getRecycleNum()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
