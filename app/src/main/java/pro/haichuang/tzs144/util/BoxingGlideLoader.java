package pro.haichuang.tzs144.util;


import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pro.bilibili.boxing.loader.IBoxingCallback;
import pro.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


/**
 * use https://github.com/bumptech/glide as media loader.
 * can <b>not</b> be used in Production Environment.
 *
 * @author ChenSL
 */
public class BoxingGlideLoader implements IBoxingMediaLoader {

    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
            Glide.with(img.getContext()).load(path).centerCrop().override(width, height).into(img);

        } catch (IllegalArgumentException ignore) {
        }

    }

    @Override
    public void displayRaw(@NonNull final ImageView img, @NonNull String absPath, int width, int height, final IBoxingCallback callback) {
        String path = "file://" + absPath;

        RequestBuilder<Bitmap> request = Glide.with(img.getContext())
                .asBitmap()
                .load(path);

        if (width > 0 && height > 0) {
            request.override(width, height);
        }

        request.listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onFail(e);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (resource != null && callback != null) {
                    img.setImageBitmap(resource);
                    callback.onSuccess();
                    return true;
                }
                return false;
            }
        }).into(img);

    }

}