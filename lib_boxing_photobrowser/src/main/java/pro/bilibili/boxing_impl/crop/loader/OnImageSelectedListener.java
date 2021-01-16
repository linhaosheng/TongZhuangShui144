package pro.bilibili.boxing_impl.crop.loader;

import pro.bilibili.boxing_impl.crop.bean.ImageItem;

/**
 * 图片选中的监听
 */

public interface OnImageSelectedListener {

    void onImageSelected(int position, ImageItem item, boolean isAdd);
}
