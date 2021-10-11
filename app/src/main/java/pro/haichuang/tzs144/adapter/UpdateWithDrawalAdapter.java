package pro.haichuang.tzs144.adapter;

import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.WithDrawalOrderModel;

public class UpdateWithDrawalAdapter extends BaseQuickAdapter<WithDrawalOrderModel.DataBean, BaseViewHolder> {


    public UpdateWithDrawalAdapter() {
        super(R.layout.item_update_with_drawal);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WithDrawalOrderModel.DataBean dataBean) {

       EditText num =  baseViewHolder.getView(R.id.num);
        num.setText(String.valueOf(dataBean.getNum()));

        EditText money =  baseViewHolder.getView(R.id.money);
        money.setText(String.valueOf(dataBean.getTotalPrice()));
    }
}
