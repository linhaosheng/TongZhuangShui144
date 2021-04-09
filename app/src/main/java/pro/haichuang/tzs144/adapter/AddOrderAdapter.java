package pro.haichuang.tzs144.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AddOrderModel;
import pro.haichuang.tzs144.model.MaterialModel;
import pro.haichuang.tzs144.util.Utils;

public class AddOrderAdapter extends BaseQuickAdapter<AddOrderModel.GoodsListBean, BaseViewHolder> {


    public AddOrderAdapter() {
        super(R.layout.item_add_order);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddOrderModel.GoodsListBean item) {

        try {
            if (item.getDeductWater() != null) {
                helper.getView(R.id.water_view).setVisibility(View.VISIBLE);
                if ("-1".equals(item.getDeductWater().getWaterGoodsId())) {
                    helper.getView(R.id.water_view).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.water_view).setVisibility(View.VISIBLE);
                }
            } else {
                helper.getView(R.id.water_view).setVisibility(View.GONE);
            }


            String priceNum = "¥" + item.getGoodsPrice() + "x" + item.getNum();
            String totalPrice = "¥" + Float.parseFloat(item.getGoodsPrice()) * Float.parseFloat(item.getNum());

            String waterNum = "";
            String discountNum = "";
            if (item.getDeductWater() != null) {
                waterNum = "水票x" + item.getDeductWater().getNum();
                discountNum = "抵扣 -" + item.getDeductWater().getDeductNum();
            }

//        String recycleNum = item.getMaterials().get(0).getMaterialName() +"x"+item.getMaterials().get(0).getNum() ;
            helper.setText(R.id.name, item.getGoodName())
                    .setText(R.id.shop_num, priceNum)
                    .setText(R.id.total_price, totalPrice)
                    //          .setText(R.id.recycle_num,recycleNum)
                    .setText(R.id.water_num, waterNum)
                    .setText(R.id.discount_num, discountNum);

            List<MaterialModel.DataBean> materials = item.getMaterials();
            if (materials != null && materials.size() > 0) {
                StringBuilder materialBuilder = new StringBuilder();
                for (MaterialModel.DataBean dataBean : materials) {
                    materialBuilder.append(dataBean.getName()).append("x").append(dataBean.getNum()).append("   ");
                }
                helper.getView(R.id.recycle_num).setVisibility(View.VISIBLE);
                helper.setText(R.id.recycle_num, materialBuilder.toString());
                helper.getView(R.id.recycle).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.recycle_num).setVisibility(View.GONE);
                helper.getView(R.id.recycle).setVisibility(View.GONE);
            }

            if (item.getDeductCoupon() == null) {
                helper.getView(R.id.reward_view).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.reward_view).setVisibility(View.VISIBLE);
                ImageView rewardImg = helper.getView(R.id.reward_img);
                Utils.showImage(rewardImg, item.getDeductCoupon().getCouponImg());
                String reward_discount = "抵扣 -" + item.getDeductCoupon().getDeductNum();
                helper.setText(R.id.reward_discount, reward_discount);
            }

            if (item.getDeductMonth() == null) {
                helper.getView(R.id.month_view).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.month_view).setVisibility(View.VISIBLE);
                ImageView monthImg = helper.getView(R.id.month_img);
                Utils.showImage(monthImg, item.getDeductMonth().getMonthImg());
                String month_discount = "抵扣 -" + item.getDeductMonth().getDeductNum();
                helper.setText(R.id.month_discount, month_discount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
