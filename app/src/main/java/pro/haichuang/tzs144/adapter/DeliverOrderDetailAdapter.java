package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.OrderDetailModel;
import pro.haichuang.tzs144.util.Utils;
import pro.haichuang.tzs144.view.LSettingItem;

public class DeliverOrderDetailAdapter extends BaseQuickAdapter<OrderDetailModel.DataBean.GoodsListBean, BaseViewHolder> {

    private boolean showTicket_type;
    private Context context;
    private SelectWaterListener selectWaterListener;

    public DeliverOrderDetailAdapter(Context context) {
        super(R.layout.item_deliver_order_detail);
        this.context = context;
    }

    public void setSelectWaterListener(SelectWaterListener selectWaterListener){
        this.selectWaterListener = selectWaterListener;
    }

    public void setShowTicket_type(boolean showTicket_type){
        this.showTicket_type = showTicket_type;
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

        if (showTicket_type){
            helper.getView(R.id.ticket_type).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.ticket_type).setVisibility(View.GONE);
        }
        LSettingItem lSettingItem =  helper.getView(R.id.select_ticket);
        /**
         * 显示水票
         */
        if (item.isShowWater()){
            helper.getView(R.id.select_ticket).setVisibility(View.VISIBLE);
            helper.getView(R.id.select_water_num).setVisibility(View.VISIBLE);
            helper.getView(R.id.select_deduction_nunm).setVisibility(View.VISIBLE);

            if (item.getWaterName()!=null){
                lSettingItem.setLeftText(item.getWaterName());
            }else {
                lSettingItem.setLeftText("水票");
            }
        }else {
            helper.getView(R.id.select_ticket).setVisibility(View.GONE);
            helper.getView(R.id.select_water_num).setVisibility(View.GONE);
            helper.getView(R.id.select_deduction_nunm).setVisibility(View.GONE);
        }

        if (item.isShowReward()){
            helper.getView(R.id.upload_reward_view).setVisibility(View.VISIBLE);
            helper.getView(R.id.reward_deduction_nunm).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.upload_reward_view).setVisibility(View.GONE);
            helper.getView(R.id.reward_deduction_nunm).setVisibility(View.GONE);
        }

        if (item.isShowMonth()){
            helper.getView(R.id.upload_month_view).setVisibility(View.VISIBLE);
            helper.getView(R.id.month_deduction_nunm).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.upload_month_view).setVisibility(View.GONE);
            helper.getView(R.id.month_deduction_nunm).setVisibility(View.GONE);
        }

        ImageView uploadReward = helper.getView(R.id.upload_reward);
        if (item.getCouponImg()!=null && !"".equals(item.getCouponImg())){
            Utils.showImage(uploadReward,item.getCouponImg());
        }else {
            uploadReward.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.upload));
        }

         ImageView uploadMonth = helper.getView(R.id.upload_month);
        if (item.getMonthImg()!=null && !"".equals(item.getMonthImg())){
            Utils.showImage(uploadMonth,item.getMonthImg());
        }else {
            uploadMonth.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.upload));
        }


        lSettingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked, View view) {
                Log.i("tag===","SJJSDDDD");
                selectWaterListener.selectClick(helper.getPosition());
            }
        });


        LSettingItem select_water_num =  helper.getView(R.id.select_water_num);
        select_water_num.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setWaterNum(Integer.parseInt(text));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        LSettingItem select_deduction_nunm =  helper.getView(R.id.select_deduction_nunm);
        select_deduction_nunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setWaterDeductNum(Integer.parseInt(text));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        LSettingItem reward_deduction_nunm =  helper.getView(R.id.reward_deduction_nunm);
        reward_deduction_nunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setCouponDeductNum(Integer.parseInt(text));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        LSettingItem month_deduction_nunm =  helper.getView(R.id.month_deduction_nunm);
        month_deduction_nunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setMonthDeductNum(Integer.parseInt(text));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public interface SelectWaterListener{
        void selectClick(int position);
    }
}