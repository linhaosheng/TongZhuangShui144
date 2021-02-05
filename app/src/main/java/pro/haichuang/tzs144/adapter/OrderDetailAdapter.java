package pro.haichuang.tzs144.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.util.Utils;

public class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailModel.DataBean.GoodsListBean, BaseViewHolder> {


    public OrderDetailAdapter() {
        super(R.layout.item_order_detail);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderDetailModel.DataBean.GoodsListBean item) {

        String priceNum = "¥"+item.getGoodsPrice()+"x"+item.getGoodsNum();
        String totalPrice = "¥"+item.getGoodsPrice()*item.getGoodsNum();
        String waterNum = "水票x"+item.getWaterNum();
        String discountNum = "抵扣 -"+item.getWaterDeductNum();
        String monthDiscount =  "抵扣 -"+item.getMonthDeductNum();;
        String reward_discount = "抵扣 -"+item.getCouponDeductNum();

        String recycleNum = "直饮桶x5   直饮桶x5";      //item.getMaterials().get(0).getMaterialName() +"x"+item.getMaterials().get(0).getNum() ;
        helper.setText(R.id.name,item.getGoodsName())
                .setText(R.id.shop_num,priceNum)
                .setText(R.id.total_price,totalPrice)
                .setText(R.id.recycle_num,recycleNum)
                .setText(R.id.water_num,waterNum)
                .setText(R.id.discount_num,discountNum)
        .setText(R.id.month_discount,monthDiscount)
        .setText(R.id.reward_discount,reward_discount);

        ImageView rewardImg = helper.getView(R.id.reward_img);
        Utils.showImage(rewardImg,item.getCouponImg());
//        String rewardDiscount =
//        helper.setText(R.id.reward_discount,)


        ImageView monthImg = helper.getView(R.id.month_img);
        Utils.showImage(monthImg,item.getMonthImg());


    }
}
