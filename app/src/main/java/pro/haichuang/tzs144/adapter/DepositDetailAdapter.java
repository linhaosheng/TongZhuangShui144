package pro.haichuang.tzs144.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.DeposiDetailModel;
import pro.haichuang.tzs144.model.DepositManagerModel;

public class DepositDetailAdapter extends BaseQuickAdapter<DeposiDetailModel.DataBean.ListBean, BaseViewHolder> {


    public DepositDetailAdapter() {
        super(R.layout.item_deposit_detail);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DeposiDetailModel.DataBean.ListBean dataBean) {

        String deposit_no = "押金编号："+dataBean.getYjNo();
        String client_super = "客户名称："+dataBean.getKhName();
        String deposit_number = "数量："+dataBean.getNum();
        String price = "金额："+dataBean.getTotalPrice();
        String deposit_start_time = "开押时间："+dataBean.getTime();
        String deposit_end_time = "退押时间："+dataBean.getReturnTime();

        String deposit_type = "";
        if (dataBean.getType().equals("0")){
            deposit_type = "押金";
        }else  if (dataBean.getType().equals("1")){
            deposit_type = "借条";
        }else  if (dataBean.getType().equals("2")){
            deposit_type = "暂欠";
        }

        baseViewHolder.setText(R.id.deposit_no,deposit_no)
                .setText(R.id.client_super,client_super)
                .setText(R.id.deposit_number,deposit_number)
                .setText(R.id.price,price)
                .setText(R.id.deposit_end_time,deposit_end_time)
                .setText(R.id.deposit_start_time,deposit_start_time)
                .setText(R.id.deposit_type,deposit_type);

    }
}
