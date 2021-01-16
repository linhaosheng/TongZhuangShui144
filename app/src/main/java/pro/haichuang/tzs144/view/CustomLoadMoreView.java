package pro.haichuang.tzs144.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import pro.haichuang.tzs144.R;

public class CustomLoadMoreView extends BaseLoadMoreView {

    @NotNull
    @Override
    public View getLoadComplete(@NotNull BaseViewHolder baseViewHolder) {
        return baseViewHolder.findView(R.id.load_more_load_complete_view);
    }

    @NotNull
    @Override
    public View getLoadEndView(@NotNull BaseViewHolder baseViewHolder) {
        // 布局中 “全部加载结束，没有数据”的View
        return baseViewHolder.findView(R.id.load_more_load_end_view);
    }

    @NotNull
    @Override
    public View getLoadFailView(@NotNull BaseViewHolder baseViewHolder) {
        // 布局中 “加载失败”的View
        return baseViewHolder.findView(R.id.load_more_load_fail_view);
    }

    @NotNull
    @Override
    public View getLoadingView(@NotNull BaseViewHolder baseViewHolder) {
        // 布局中 “加载中”的View
        return baseViewHolder.findView(R.id.load_more_loading_view);
    }

    @NotNull
    @Override
    public View getRootView(@NotNull ViewGroup parent) {
        // 整个 LoadMore 布局
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.view_load_more, parent, false);
    }
}
