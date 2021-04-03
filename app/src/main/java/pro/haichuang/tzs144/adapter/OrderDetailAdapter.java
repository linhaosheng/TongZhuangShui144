package pro.haichuang.tzs144.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.util.Utils;
import rxhttp.wrapper.param.IFile;

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

        if (item.getMaterialList()!=null && item.getMaterialList().size()>0){
            StringBuilder materailBuild = new StringBuilder();
            for (String data : item.getMaterialList()){
                if (!data.contains("null")){
                    materailBuild.append(data).append("   ");
                }
            }
            if (materailBuild.length()>0){
                helper.getView(R.id.recycle).setVisibility(View.VISIBLE);
                helper.getView(R.id.recycle_num).setVisibility(View.VISIBLE);
                helper.setText(R.id.recycle_num,materailBuild.toString());
            }else {
                helper.getView(R.id.recycle).setVisibility(View.GONE);
                helper.getView(R.id.recycle_num).setVisibility(View.GONE);
            }

        }else {
            helper.getView(R.id.recycle).setVisibility(View.GONE);
            helper.getView(R.id.recycle_num).setVisibility(View.GONE);
        }

        ImageView rewardImg = helper.getView(R.id.reward_img);
        if (item.getCouponDeductNum()>0){
            Utils.showImage(rewardImg,item.getCouponImg());
            helper.getView(R.id.reward_view).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.reward_view).setVisibility(View.GONE);
        }


        if (item.getMonthDeductNum()>0){
            ImageView monthImg = helper.getView(R.id.month_img);
            Utils.showImage(monthImg,item.getMonthImg());
            helper.getView(R.id.month_view).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.month_view).setVisibility(View.GONE);
        }

    }
}
