package pro.haichuang.tzs144.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

public class MainTainRecordAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MainTainRecordAdapter() {
        super(R.layout.item_main_tain_record);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

    }
}
