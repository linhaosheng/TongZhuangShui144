package pro.haichuang.tzs144.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.util.Utils;

public class OrderShopDetailAdapter extends BaseQuickAdapter<OrderDetailModel.DataBean.GoodsListBean, BaseViewHolder> {


    public OrderShopDetailAdapter() {
        super(R.layout.item_order_shop_detail);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OrderDetailModel.DataBean.GoodsListBean item) {

        String priceNum = "¥"+item.getGoodsPrice()+"x"+item.getGoodsNum();
        String totalPrice = "¥"+item.getGoodsPrice()*item.getGoodsNum();
        String waterNum = "水票x"+item.getWaterNum();
        String discountNum = "抵扣 -"+item.getWaterDeductNum();
        String monthDiscount =  "抵扣 -"+item.getMonthDeductNum();;
        String reward_discount = "抵扣 -"+item.getCouponDeductNum();

        if (item.getWaterNum()>0){
            helper.getView(R.id.water_num).setVisibility(View.VISIBLE);
            helper.getView(R.id.discount_num).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.water_num).setVisibility(View.GONE);
            helper.getView(R.id.discount_num).setVisibility(View.GONE);
        }
        helper.setText(R.id.name,item.getGoodsName())
                .setText(R.id.shop_num,priceNum)
                .setText(R.id.total_price,totalPrice)
                .setText(R.id.water_num,waterNum)
                .setText(R.id.discount_num,discountNum)
        .setText(R.id.month_discount,monthDiscount)
        .setText(R.id.reward_discount,reward_discount);

        ImageView rewardImg = helper.getView(R.id.reward_img);
        if (item.getCouponImg()!=null){
            Utils.showImage(rewardImg,item.getCouponImg());
        }else {
            helper.getView(R.id.reward_view).setVisibility(View.GONE);
        }

        ImageView monthImg = helper.getView(R.id.month_img);
        if (item.getMonthImg()!=null){
            Utils.showImage(monthImg,item.getMonthImg());
        }else {
            helper.getView(R.id.month_view).setVisibility(View.GONE);
        }

        String name_spec = item.getGoodsName()+"  "+item.getGoodsSpecsName();
        helper.setText(R.id.name_spec,name_spec);
    }
}
