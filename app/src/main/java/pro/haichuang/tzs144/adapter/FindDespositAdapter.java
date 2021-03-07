package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.DepositModel;
import pro.haichuang.tzs144.model.GoodBeanModel;

public class FindDespositAdapter extends BaseQuickAdapter<DepositModel.DataBean, BaseViewHolder> {


    public FindDespositAdapter() {
        super(R.layout.item_deposit_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DepositModel.DataBean baseMedia) {

        String yjID = "    ID : "+baseMedia.getId();
        String yjNum = "编号 : "+baseMedia.getYjNo();

        baseViewHolder.setText(R.id.yj_id,yjID)
                .setText(R.id.yaj_num,yjNum);
    }
}