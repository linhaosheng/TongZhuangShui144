package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.DepositManagerModel;

public class DepositManagementSearchAdapter extends BaseQuickAdapter<DepositManagerModel.DataBean, BaseViewHolder> implements LoadMoreModule {


    public DepositManagementSearchAdapter() {
        super(R.layout.item_deposit_management_search);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DepositManagerModel.DataBean dataBean) {

        String deposit_number = "押金本编号："+dataBean.getDepNo();
        String start_code = "   开始编码："+dataBean.getStartNo();
        String end_code = "   结束编码："+dataBean.getEndNo();
        String code_time = "   领入时间："+dataBean.getCreateTime();
        baseViewHolder.setText(R.id.deposit_number,deposit_number)
                .setText(R.id.start_code,start_code)
                .setText(R.id.end_code,end_code)
        .setText(R.id.code_time,code_time);

    }
}
