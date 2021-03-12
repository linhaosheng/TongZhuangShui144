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

    private boolean showWater_type;
    private boolean showTicket_type;
    private Context context;
    private SelectWaterListener selectWaterListener;
    private CaculateMoneyListener caculateMoneyListener;

    public DeliverOrderDetailAdapter(Context context) {
        super(R.layout.item_deliver_order_detail);
        this.context = context;
    }

    public void setCaculateMoneyListener(CaculateMoneyListener caculateMoneyListener){
        this.caculateMoneyListener = caculateMoneyListener;
    }

    public void setSelectWaterListener(SelectWaterListener selectWaterListener){
        this.selectWaterListener = selectWaterListener;
    }

    public void setShowTicket_type(boolean showTicket_type){
        this.showTicket_type = showTicket_type;
    }

    public void setShowWater_type(boolean showWater_type){
        this.showWater_type = showWater_type;
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
            if (bindMaterList.size()==0){
                helper.getView(R.id.recycle_material_view).setVisibility(View.GONE);
            }else {
                helper.getView(R.id.recycle_material_view).setVisibility(View.VISIBLE);

                if (bindMaterList.size()==1){
                    helper.getView(R.id.recycle_type_view1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view2).setVisibility(View.GONE);
                    helper.getView(R.id.recycle_type_view3).setVisibility(View.GONE);
                    helper.getView(R.id.recycle_type_view4).setVisibility(View.GONE);
                    helper.getView(R.id.recycle_type_view5).setVisibility(View.GONE);
                    helper.setText(R.id.recycle_type,bindMaterList.get(0).getName())
                            .setText(R.id.shop_num_tong,bindMaterList.get(0).getNum()+"");

                }else if (bindMaterList.size()==2){
                    helper.getView(R.id.recycle_type_view1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view3).setVisibility(View.GONE);
                    helper.getView(R.id.recycle_type_view4).setVisibility(View.GONE);
                    helper.getView(R.id.recycle_type_view5).setVisibility(View.GONE);

                    helper.setText(R.id.recycle_type,bindMaterList.get(0).getName())
                            .setText(R.id.shop_num_tong,bindMaterList.get(0).getNum()+"");

                    helper.setText(R.id.recycle_type2,bindMaterList.get(1).getName())
                            .setText(R.id.shop_num_tong2,bindMaterList.get(1).getNum()+"");

                }else if (bindMaterList.size()==3){
                    helper.getView(R.id.recycle_type_view1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view3).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view4).setVisibility(View.GONE);
                    helper.getView(R.id.recycle_type_view5).setVisibility(View.GONE);

                    helper.setText(R.id.recycle_type,bindMaterList.get(0).getName())
                            .setText(R.id.shop_num_tong,bindMaterList.get(0).getNum()+"");

                    helper.setText(R.id.recycle_type2,bindMaterList.get(1).getName())
                            .setText(R.id.shop_num_tong2,bindMaterList.get(1).getNum()+"");

                    helper.setText(R.id.recycle_type3,bindMaterList.get(2).getName())
                            .setText(R.id.shop_num_tong3,bindMaterList.get(2).getNum()+"");
                }else if (bindMaterList.size()==4){
                    helper.getView(R.id.recycle_type_view1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view3).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view4).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view5).setVisibility(View.GONE);

                    helper.setText(R.id.recycle_type,bindMaterList.get(0).getName())
                            .setText(R.id.shop_num_tong,bindMaterList.get(0).getNum()+"");

                    helper.setText(R.id.recycle_type2,bindMaterList.get(1).getName())
                            .setText(R.id.shop_num_tong2,bindMaterList.get(1).getNum()+"");

                    helper.setText(R.id.recycle_type3,bindMaterList.get(2).getName())
                            .setText(R.id.shop_num_tong3,bindMaterList.get(2).getNum()+"");

                    helper.setText(R.id.recycle_type4,bindMaterList.get(3).getName())
                            .setText(R.id.shop_num_tong4,bindMaterList.get(3).getNum()+"");

                }else {
                    helper.getView(R.id.recycle_type_view1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view3).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view4).setVisibility(View.VISIBLE);
                    helper.getView(R.id.recycle_type_view5).setVisibility(View.VISIBLE);

                    helper.setText(R.id.recycle_type,bindMaterList.get(0).getName())
                            .setText(R.id.shop_num_tong,bindMaterList.get(0).getNum()+"");

                    helper.setText(R.id.recycle_type2,bindMaterList.get(1).getName())
                            .setText(R.id.shop_num_tong2,bindMaterList.get(1).getNum()+"");

                    helper.setText(R.id.recycle_type3,bindMaterList.get(2).getName())
                            .setText(R.id.shop_num_tong3,bindMaterList.get(2).getNum()+"");

                    helper.setText(R.id.recycle_type4,bindMaterList.get(3).getName())
                            .setText(R.id.shop_num_tong4,bindMaterList.get(3).getNum()+"");

                    helper.setText(R.id.recycle_type5,bindMaterList.get(4).getName())
                            .setText(R.id.shop_num_tong5,bindMaterList.get(4).getNum()+"");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (showWater_type){
            helper.getView(R.id.ticket_type).setVisibility(View.VISIBLE);
            helper.getView(R.id.reward_tickets).setVisibility(View.GONE);
            helper.getView(R.id.monthly).setVisibility(View.GONE);
        }else {

            if (showTicket_type){
                helper.getView(R.id.ticket_type).setVisibility(View.VISIBLE);
            }else {
                helper.getView(R.id.ticket_type).setVisibility(View.GONE);
            }
        }

        LSettingItem lSettingItem =  helper.getView(R.id.select_ticket);
        /**
         * 显示水票
         */
        if (item.isShowWater()){
            helper.getView(R.id.water_tickets).setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn17));
            helper.getView(R.id.select_ticket).setVisibility(View.VISIBLE);
            helper.getView(R.id.select_water_num).setVisibility(View.VISIBLE);
            helper.getView(R.id.select_deduction_nunm).setVisibility(View.VISIBLE);

            if (item.getWaterName()!=null){
                lSettingItem.setLeftText(item.getWaterName());
            }else {
                lSettingItem.setLeftText("水票");
            }
        }else {
            helper.getView(R.id.water_tickets).setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn33));
            helper.getView(R.id.select_ticket).setVisibility(View.GONE);
            helper.getView(R.id.select_water_num).setVisibility(View.GONE);
            helper.getView(R.id.select_deduction_nunm).setVisibility(View.GONE);
        }

        if (item.isShowReward()){
            helper.getView(R.id.reward_tickets).setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn17));
            helper.getView(R.id.upload_reward_view).setVisibility(View.VISIBLE);
            helper.getView(R.id.reward_deduction_nunm).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.upload_reward_view).setVisibility(View.GONE);
            helper.getView(R.id.reward_deduction_nunm).setVisibility(View.GONE);
            helper.getView(R.id.reward_tickets).setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn33));
        }

        if (item.isShowMonth()){
            helper.getView(R.id.monthly).setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn17));
            helper.getView(R.id.upload_month_view).setVisibility(View.VISIBLE);
            helper.getView(R.id.month_deduction_nunm).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.monthly).setBackground(ContextCompat.getDrawable(context,R.drawable.set_bg_btn33));
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
                selectWaterListener.selectClick(helper.getPosition());
            }
        });


        LSettingItem select_water_num =  helper.getView(R.id.select_water_num);
        if (item.getWaterNum()!=0){
            select_water_num.setEditinput(item.getWaterNum()+"");
        }else {
            select_water_num.setEditinput("");
        }
        select_water_num.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                Log.i("TAG===",text+"===position=="+position);
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setWaterNum(Integer.parseInt(text));
                    DeliverOrderDetailAdapter.this.getData().set(position,goodsListBean);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (caculateMoneyListener!=null){
                        caculateMoneyListener.caculate();
                    }
                }
            }
        });

        LSettingItem select_deduction_nunm =  helper.getView(R.id.select_deduction_nunm);
        if (item.getWaterDeductNum()!=0){
            select_deduction_nunm.setEditinput(item.getWaterDeductNum()+"");
        }else {
            select_deduction_nunm.setEditinput("");
        }
        select_deduction_nunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setWaterDeductNum(Integer.parseInt(text));
                    DeliverOrderDetailAdapter.this.getData().set(position,goodsListBean);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (caculateMoneyListener!=null){
                        caculateMoneyListener.caculate();
                    }
                }
            }
        });

        LSettingItem reward_deduction_nunm =  helper.getView(R.id.reward_deduction_nunm);
        if (item.getCouponDeductNum()!=0){
            reward_deduction_nunm.setEditinput(item.getCouponDeductNum()+"");
        }else {
            reward_deduction_nunm.setEditinput("");
        }
        reward_deduction_nunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setCouponDeductNum(Integer.parseInt(text));
                    DeliverOrderDetailAdapter.this.getData().set(position,goodsListBean);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (caculateMoneyListener!=null){
                        caculateMoneyListener.caculate();
                    }
                }
            }
        });

        LSettingItem month_deduction_nunm =  helper.getView(R.id.month_deduction_nunm);
        if (item.getMonthDeductNum()!=0){
            month_deduction_nunm.setEditinput(item.getMonthDeductNum()+"");
        }else {
            month_deduction_nunm.setEditinput("");
        }
        month_deduction_nunm.setEditTextListner(new LSettingItem.EditTextListner() {
            @Override
            public void editListner(String text) {
                int position = helper.getPosition();
                OrderDetailModel.DataBean.GoodsListBean goodsListBean = DeliverOrderDetailAdapter.this.getData().get(position);
                try {
                    goodsListBean.setMonthDeductNum(Integer.parseInt(text));
                    DeliverOrderDetailAdapter.this.getData().set(position,goodsListBean);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (caculateMoneyListener!=null){
                        caculateMoneyListener.caculate();
                    }
                }
            }
        });
    }

    public interface SelectWaterListener{
        void selectClick(int position);
    }

    public interface CaculateMoneyListener{
        void caculate();
    }
}
