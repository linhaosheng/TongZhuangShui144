package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.SaleModel;

public class SaleAdapter extends BaseQuickAdapter<SaleModel.DataBean.DataListBean, BaseViewHolder> {

    private int type;

    public SaleAdapter(int type) {
        super(R.layout.item_sale);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SaleModel.DataBean.DataListBean dataBean) {

        String shop_name = "";
        String return_num="";
        String recycle_num="";
        if (type==0){
            shop_name = "商品 : "+dataBean.getGoods_name();
            return_num = "取水数量 : "+dataBean.getQsNum()+"桶";
            recycle_num = "销售数量 : "+dataBean.getSaleNum()+"桶";
            if (dataBean.getQsNum()==null){
                baseViewHolder.getView(R.id.return_num).setVisibility(View.GONE);
            }else {
                baseViewHolder.getView(R.id.return_num).setVisibility(View.VISIBLE);
            }
            if (dataBean.getSaleNum()==null){
                baseViewHolder.getView(R.id.recycle_num).setVisibility(View.GONE);
            }else {
                baseViewHolder.getView(R.id.recycle_num).setVisibility(View.VISIBLE);
            }

            if (dataBean.getSaleNum()==null && dataBean.getQsNum()==null){
                baseViewHolder.getView(R.id.shop_name).setVisibility(View.GONE);
            }else {
                baseViewHolder.getView(R.id.shop_name).setVisibility(View.VISIBLE);
            }
        }else if (type==1){
            shop_name = "回收材料 : "+dataBean.getGoods_name();
            return_num = "还桶数量 : "+dataBean.getHtNum()+"桶";
            recycle_num = "回收数量 : "+dataBean.getHsNum()+"桶";

            if (dataBean.getHtNum()==null){
                baseViewHolder.getView(R.id.return_num).setVisibility(View.GONE);
            }else {
                baseViewHolder.getView(R.id.return_num).setVisibility(View.VISIBLE);
            }
            if (dataBean.getHsNum()==null){
                baseViewHolder.getView(R.id.recycle_num).setVisibility(View.GONE);
            }else {
                baseViewHolder.getView(R.id.recycle_num).setVisibility(View.VISIBLE);
            }

        }

        baseViewHolder.setText(R.id.shop_name,shop_name)
                .setText(R.id.return_num,return_num)
                .setText(R.id.recycle_num,recycle_num);

    }
}
