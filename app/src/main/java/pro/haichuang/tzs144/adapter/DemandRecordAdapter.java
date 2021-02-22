package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.AllocationRecordModel;
import pro.haichuang.tzs144.model.DemandRecordModel;

/**
 * 调拨记录适配器
 */
public class DemandRecordAdapter extends BaseQuickAdapter<DemandRecordModel.DataBean, BaseViewHolder> {

    private Context context;

    public DemandRecordAdapter(Context context) {
        super(R.layout.item_demand_record);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DemandRecordModel.DataBean dataBean) {

        String subjectName = "库存主体："+dataBean.getStockMain();
        String time = "提交时间："+dataBean.getTime();
        String optionName = "操作人："+dataBean.getRealName();

        baseViewHolder.setText(R.id.subject_name,subjectName)
                .setText(R.id.time,time)
                .setText(R.id.option_name,optionName);

        List<DemandRecordModel.DataBean.ItemListBean> itemList = dataBean.getItemList();
        if (itemList!=null && itemList.size()>0){
            RelativeLayout shopListView = baseViewHolder.getView(R.id.shop_list_view);
            for (DemandRecordModel.DataBean.ItemListBean itemListBean : itemList){
                TextView nameTxt = new TextView(context);
                nameTxt.setText(itemListBean.getName());
                nameTxt.setTextSize(12);
                nameTxt.setTextColor(Color.parseColor("#333333"));
                RelativeLayout.LayoutParams nameLayoutParams = (RelativeLayout.LayoutParams )nameTxt.getLayoutParams();
                nameLayoutParams.leftMargin = 15;
                nameLayoutParams.topMargin = 10;
                nameLayoutParams.bottomMargin = 10;
                nameTxt.setLayoutParams(nameLayoutParams);


                TextView numTxt = new TextView(context);
                numTxt.setText(itemListBean.getNum());
                numTxt.setTextSize(12);
                numTxt.setTextColor(Color.parseColor("#3F3F3F"));

                RelativeLayout.LayoutParams numLayoutParams = (RelativeLayout.LayoutParams )nameTxt.getLayoutParams();
                numLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                numLayoutParams.rightMargin = 15;
                numLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

                shopListView.addView(nameTxt);
                shopListView.addView(numTxt);
            }
        }


    }
}
