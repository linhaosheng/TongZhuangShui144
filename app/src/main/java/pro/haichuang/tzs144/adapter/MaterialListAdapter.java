package pro.haichuang.tzs144.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AccountListModel;
import pro.haichuang.tzs144.model.MaterialModel;

public class MaterialListAdapter extends BaseQuickAdapter<MaterialModel.DataBean, BaseViewHolder> {


    public MaterialListAdapter() {
        super(R.layout.item_material_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MaterialModel.DataBean dataBean) {

        baseViewHolder.setText(R.id.material_name,dataBean.getName());
        baseViewHolder.setText(R.id.shop_num_tong,dataBean.getNum()+"");
    }
}
