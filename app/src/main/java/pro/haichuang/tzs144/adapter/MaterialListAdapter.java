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


        EditText shop_num_edit = baseViewHolder.getView(R.id.shop_num_tong);

        if (shop_num_edit.getText()!=null && shop_num_edit.getText().length()>0){
            shop_num_edit.setSelection(shop_num_edit.getText().toString().length());
        }

        shop_num_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (shop_num_edit.getText()!=null){
                   int position = baseViewHolder.getPosition();
                   MaterialModel.DataBean dataBean1 = MaterialListAdapter.this.getData().get(position);
                   try {

                       int num = Integer.parseInt(shop_num_edit.getText().toString());
                       dataBean1.setNum(num);
                       MaterialListAdapter.this.setData(position,dataBean1);

                   }catch (Exception e){
                       e.printStackTrace();
                   }

               }
            }
        });
    }
}
