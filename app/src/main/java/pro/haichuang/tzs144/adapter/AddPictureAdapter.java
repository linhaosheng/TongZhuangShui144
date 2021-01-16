package pro.haichuang.tzs144.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import pro.bilibili.boxing.model.entity.BaseMedia;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import pro.haichuang.tzs144.R;
import pro.haichuang.tzs144.util.Utils;

public class AddPictureAdapter extends BaseQuickAdapter<BaseMedia, BaseViewHolder> {


    public AddPictureAdapter() {
        super(R.layout.item_add_picture);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BaseMedia item) {

        ImageView imageView = helper.getView(R.id.image);
        helper.getView(R.id.delete).setVisibility(View.GONE);
        helper.getView(R.id.video).setVisibility(View.GONE);
        imageView.setImageResource(R.mipmap.add_pic);

        if (!item.isSelectIcon){
            Log.i("test===",item.getPath());
            Utils.showImage(imageView,item.getPath());
            helper.getView(R.id.delete).setVisibility(View.VISIBLE);
            if (item.getType().equals("video")){
                helper.getView(R.id.video).setVisibility(View.VISIBLE);
            }
        }
    }
}
