package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.SaleDataModel;

/**
 * 客户销售搜索适配器
 */
public class SaleSearcAdapter extends BaseQuickAdapter<SaleDataModel.DataBean, BaseViewHolder> implements LoadMoreModule {


    public SaleSearcAdapter() {
        super(R.layout.item_sale_search);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SaleDataModel.DataBean dataBean) {

        baseViewHolder.setText(R.id.name,dataBean.getName())
                .setText(R.id.phone,dataBean.getPhone())
                .setText(R.id.address,dataBean.getAddress())
                .setText(R.id.detail_address,dataBean.getAddressName() +" >");
    }

}
