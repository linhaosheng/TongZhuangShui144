package pro.haichuang.tzs144.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.TypeListModel;

public class TypeListAdapter extends BaseQuickAdapter<TypeListModel.DataBean, BaseViewHolder> {


    private int selectIndex = 0;

    public TypeListAdapter() {
        super(R.layout.item_type_list);
    }

    public void setSelectIndex(int index){
        this.selectIndex = index;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TypeListModel.DataBean dataBean) {

        TextView view = baseViewHolder.getView(R.id.type_name);
        view.setText(dataBean.getName());
        if (baseViewHolder.getPosition()==selectIndex){
            view.setTextColor(Color.parseColor("#000000"));
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            view.setTextColor(Color.parseColor("#999999"));
            view.setBackgroundColor(Color.parseColor("#F4F5F6"));
        }
    }
}
