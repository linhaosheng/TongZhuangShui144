package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.CustodyModel;

public class CustodySituationAdapter extends BaseQuickAdapter<CustodyModel.DataBean.DataListBean, BaseViewHolder> {


    public CustodySituationAdapter() {
        super(R.layout.item_custody);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CustodyModel.DataBean.DataListBean dataBean) {


        String shop_name = "商品名称 : "+dataBean.getGoods_name();
        String sho_num = "商品数量 : "+dataBean.getNum() +"桶";
        String shop_amount = "商品金额 : "+dataBean.getPrice()+"元";
        baseViewHolder.setText(R.id.shop_name,shop_name)
                .setText(R.id.shop_type,dataBean.getType())
                .setText(R.id.sho_num,sho_num)
                .setText(R.id.shop_amount,shop_amount);

    }
}
