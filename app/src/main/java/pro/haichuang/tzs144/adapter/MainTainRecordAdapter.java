package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientDetailModel;

public class MainTainRecordAdapter extends BaseQuickAdapter<ClientDetailModel.DataBean.MaintainListBean, BaseViewHolder> {


    public MainTainRecordAdapter() {
        super(R.layout.item_main_tain_record);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClientDetailModel.DataBean.MaintainListBean data) {

        baseViewHolder.setText(R.id.time,data.getTime())
                .setText(R.id.record_txt,data.getMaintainInfo());
    }
}
