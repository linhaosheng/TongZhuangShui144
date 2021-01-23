package pro.haichuang.tzs144.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import pro.bilibili.boxing.model.entity.BaseMedia;
import pro.haichuang.tzs144.R;

public class OrderInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int type;
    private Context context;

    public OrderInfoAdapter(Context context, int mType) {
        super(R.layout.item_order_info);
        this.context = context;
        this.type = mType;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String baseMedia) {

        ImageView order_state_img = baseViewHolder.getView(R.id.order_state_img);
        order_state_img.setVisibility(View.GONE);

        if (type == 0) {
            baseViewHolder.setText(R.id.take_orders, "抢单/接单");
        } else if (type == 1) {
            baseViewHolder.setText(R.id.take_orders, "配送");
        } else if (type == 2) {
            baseViewHolder.setText(R.id.take_orders, "知道了");
        }else if (type==3){
            baseViewHolder.setText(R.id.take_orders, "知道了");

            order_state_img.setVisibility(View.VISIBLE);

        }

        TextView shopOrder = baseViewHolder.getView(R.id.shop_order);
        ImageView time_out_icon = baseViewHolder.getView(R.id.time_out_icon);
        TextView timeout = baseViewHolder.getView(R.id.timeout);

        if (baseMedia.equals("0")) {
            shopOrder.setText("商城订单");
            shopOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.set_bg_btn12));
            time_out_icon.setVisibility(View.VISIBLE);
            timeout.setVisibility(View.VISIBLE);
        } else if (baseMedia.equals("1")) {
            shopOrder.setText("电话订单");
            shopOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.set_bg_btn22));
            time_out_icon.setVisibility(View.INVISIBLE);
            timeout.setVisibility(View.INVISIBLE);
        } else if (baseMedia.equals("2")) {
            shopOrder.setText("外卖订单");
            shopOrder.setBackground(ContextCompat.getDrawable(context, R.drawable.set_bg_btn23));
            time_out_icon.setVisibility(View.INVISIBLE);
            timeout.setVisibility(View.INVISIBLE);
        }
    }
}
