package pro.haichuang.tzs144.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.GoodBeanModel;
import pro.haichuang.tzs144.view.LSettingItem;

/**
 * 需求列表适配器
 */
public class DemandListAdapter extends BaseQuickAdapter<GoodBeanModel, BaseViewHolder> {


    public DemandListAdapter() {
        super(R.layout.item_demand_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GoodBeanModel baseMedia) {

        baseViewHolder.setText(R.id.shop_num,baseMedia.getNum()+"");

        if (baseMedia.getName()==null || baseMedia.getName().equals("")){
        }else {
            baseViewHolder.setText(R.id.shop_name,baseMedia.getName());
        }

        EditText shopNUm = baseViewHolder.getView(R.id.shop_num);
        if (shopNUm.getText()!=null && shopNUm.getText().length()>0 && !shopNUm.getText().toString().equals("0")){
            shopNUm.setSelection(shopNUm.getText().toString().length());
        }

        shopNUm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if (shopNUm!=null){
                  try {
                      int num = 0;
                      try {
                          num = Integer.parseInt(shopNUm.getText().toString());
                          int position = baseViewHolder.getPosition();
                          baseMedia.setNum(num);
                          DemandListAdapter.this.setData(position,baseMedia);
                      }catch (NumberFormatException e){
                          e.printStackTrace();
                      }
                  }catch (Exception e){
                      e.printStackTrace();
                  }
              }
            }
        });

    }
}
