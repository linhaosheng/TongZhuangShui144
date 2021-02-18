package pro.haichuang.tzs144.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.util.Utils;

public class AddOrderAdapter extends BaseQuickAdapter<AddOrderModel.GoodsListBean, BaseViewHolder> {


    public AddOrderAdapter() {
        super(R.layout.item_add_order);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddOrderModel.GoodsListBean item) {

        if (item.getDeductWater().getWaterGoodsId().equals("-1")){
            helper.getView(R.id.water_view).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.water_view).setVisibility(View.VISIBLE);
        }


        String priceNum = "¥"+item.getGoodsPrice()+"x"+item.getNum();
        String totalPrice = "¥"+Float.parseFloat(item.getGoodsPrice())*Integer.parseInt(item.getNum());
        String waterNum = "水票x"+item.getDeductWater().getNum();
        String discountNum = "抵扣 -"+item.getDeductWater().getDeductNum();

//        String recycleNum = item.getMaterials().get(0).getMaterialName() +"x"+item.getMaterials().get(0).getNum() ;
        helper.setText(R.id.name,item.getGoodName())
                .setText(R.id.shop_num,priceNum)
                .setText(R.id.total_price,totalPrice)
      //          .setText(R.id.recycle_num,recycleNum)
                .setText(R.id.water_num,waterNum)
                .setText(R.id.discount_num,discountNum);


        if (item.getDeductCoupon()==null){
            helper.getView(R.id.reward_view).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.reward_view).setVisibility(View.VISIBLE);
            ImageView rewardImg = helper.getView(R.id.reward_img);
            Utils.showImage(rewardImg,item.getDeductCoupon().getCouponImg());
        }

        if (item.getDeductMonth()==null){
            helper.getView(R.id.month_view).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.month_view).setVisibility(View.VISIBLE);
            ImageView monthImg = helper.getView(R.id.month_img);
            Utils.showImage(monthImg,item.getDeductMonth().getMonthImg());


        }


    }
}
