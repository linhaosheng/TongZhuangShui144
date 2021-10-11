package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.SaleSummaryModel;

public class SaleSummaryAdapter extends BaseQuickAdapter<SaleSummaryModel.DataBean, BaseViewHolder>implements LoadMoreModule {


    public SaleSummaryAdapter() {
        super(R.layout.item_sale_summary);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SaleSummaryModel.DataBean dataBean) {

        String shop_name = "商品名称 : " + dataBean.getGoodsName();
        String to_year_sale_num = "今年数量 : " + dataBean.getJnNum();
        String last_year_sale_num = "去年数量 : " + dataBean.getQnNum();
        String before_last_year_sale_num = "前年数量 : " + dataBean.getNnNum();

        baseViewHolder.setText(R.id.shop_name, shop_name)
                .setText(R.id.to_year_sale_num, to_year_sale_num)
                .setText(R.id.last_year_sale_num, last_year_sale_num)
                .setText(R.id.before_last_year_sale_num, before_last_year_sale_num);

    }
}
