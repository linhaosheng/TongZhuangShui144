package pro.haichuang.tzs144.adapter;

import android.util.Log;
import android.view.View;

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
    }
}
