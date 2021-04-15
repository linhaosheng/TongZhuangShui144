package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.CustodyModel;
import pro.haichuang.tzs144.model.RecycleCountModel;

public class RecycleCountAdapter extends BaseQuickAdapter<RecycleCountModel.DataBean.DataListBean, BaseViewHolder> {


    private int type;

    public RecycleCountAdapter(int type) {
        super(R.layout.item_recycle);
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecycleCountModel.DataBean.DataListBean dataBean) {

        if (dataBean.getNum()==null){
            baseViewHolder.getView(R.id.sho_num).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.sho_num).setVisibility(View.VISIBLE);
        }

        if (dataBean.getStorehouse_name()==null || "".equals(dataBean.getStorehouse_name())){
            baseViewHolder.getView(R.id.storehouse_name).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.storehouse_name).setVisibility(View.VISIBLE);
        }

        if (dataBean.getStock_name()==null || "".equals(dataBean.getStock_name())){
            baseViewHolder.getView(R.id.stock_name).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.stock_name).setVisibility(View.VISIBLE);
        }

        if (dataBean.getMinTime()==null || "".equals(dataBean.getMinTime())){
            baseViewHolder.getView(R.id.minTime).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.minTime).setVisibility(View.VISIBLE);
        }

        if (dataBean.getMaxTime()==null || "".equals(dataBean.getMaxTime())){
            baseViewHolder.getView(R.id.maxTime).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.maxTime).setVisibility(View.VISIBLE);
        }

        String shop_name = "商品名称 : "+dataBean.getGoods_name();
        String sho_num = "商品数量 : "+dataBean.getNum() +"桶";
        String storehouse_name = "仓库 : "+dataBean.getStorehouse_name();
        String stock_name = "主体 : "+dataBean.getStock_name();
        String minTime = "最小时间 : "+dataBean.getMinTime();
        String maxTime = "最大时间 : "+dataBean.getMaxTime();

        baseViewHolder.setText(R.id.shop_name,shop_name)
                .setText(R.id.sho_num,sho_num)
        .setText(R.id.storehouse_name,storehouse_name)
        .setText(R.id.stock_name,stock_name)
        .setText(R.id.minTime,minTime)
        .setText(R.id.maxTime,maxTime);





    }
}
