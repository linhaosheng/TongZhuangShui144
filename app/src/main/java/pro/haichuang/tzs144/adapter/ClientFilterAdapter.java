package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.model.ClientTypeModel;

public class ClientFilterAdapter extends BaseQuickAdapter<ClientTypeModel.DataBean, BaseViewHolder> {


    private Context context;

    public ClientFilterAdapter(Context context) {
        super(R.layout.item_client_filter);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClientTypeModel.DataBean dataBean) {
        ImageView view = baseViewHolder.getView(R.id.check);

        if (dataBean.isCheck()){
            view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check_box));
        }else {
            view.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.check));
        }
        baseViewHolder.setText(R.id.name,dataBean.getName());
    }
}
