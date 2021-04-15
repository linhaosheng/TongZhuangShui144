package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.CustodyModel;

public class CustodySituationAdapter extends BaseQuickAdapter<CustodyModel.DataBean.DataListBean, BaseViewHolder> {


    private int type;

    public CustodySituationAdapter(int type) {
        super(R.layout.item_custody);
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CustodyModel.DataBean.DataListBean dataBean) {

        if (dataBean.getNum()==null){
            baseViewHolder.getView(R.id.sho_num).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.sho_num).setVisibility(View.VISIBLE);
        }

        if (dataBean.getPrice()==null){
            baseViewHolder.getView(R.id.shop_amount).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.shop_amount).setVisibility(View.VISIBLE);
        }

        String shop_name = "商品名称 : "+dataBean.getGoods_name();
        String sho_num = "商品数量 : "+dataBean.getNum() +"桶";
        String shop_amount = "商品金额 : "+dataBean.getPrice()+"元";
        baseViewHolder.setText(R.id.shop_name,shop_name)
                .setText(R.id.shop_type,dataBean.getType())
                .setText(R.id.sho_num,sho_num)
                .setText(R.id.shop_amount,shop_amount);

        baseViewHolder.getView(R.id.parent_view).setVisibility(View.VISIBLE);

//        if (type==0 && "退押".equals(dataBean.getType())){
//           baseViewHolder.getView(R.id.parent_view).setVisibility(View.GONE);
//        }
//
//        if (type==1 && "开押".equals(dataBean.getType())){
//            baseViewHolder.getView(R.id.parent_view).setVisibility(View.GONE);
//        }
    }
}
