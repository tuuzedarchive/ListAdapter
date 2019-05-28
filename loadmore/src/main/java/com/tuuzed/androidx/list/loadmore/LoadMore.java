package com.tuuzed.androidx.list.loadmore;

import android.util.Log;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("unused")
public class LoadMore {
    private static final String TAG = "LoadMore";
    private RecyclerView.Adapter originalAdapter;
    private OnLoadMoreListener onLoadMoreListener;
    private LoadMoreController loadMoreController;

    private View footerView;
    private int footerResId = View.NO_ID;

    private View noMoreView;
    private int noMoreResId = View.NO_ID;

    private View loadFailedView;
    private int loadFailedResId = View.NO_ID;

    @NonNull
    public static LoadMore of(RecyclerView.Adapter originalAdapter) {
        return new LoadMore(originalAdapter);
    }

    public LoadMore(@NonNull RecyclerView.Adapter originalAdapter) {
        this.originalAdapter = originalAdapter;
    }

    @NonNull
    public LoadMore setOnLoadMoreListener(@NonNull OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
        return this;
    }

    @NonNull
    public LoadMore setLoadMoreController(@NonNull LoadMoreController loadMoreController) {
        this.loadMoreController = loadMoreController;
        return this;
    }

    @NonNull
    public LoadMore setFooterView(View footerView) {
        this.footerView = footerView;
        return this;
    }

    @NonNull
    public LoadMore setFooterResId(@LayoutRes int footerResId) {
        this.footerResId = footerResId;
        return this;
    }

    @NonNull
    public LoadMore setNoMoreView(View noMoreView) {
        this.noMoreView = noMoreView;
        return this;
    }

    @NonNull
    public LoadMore setNoMoreResId(@LayoutRes int noMoreResId) {
        this.noMoreResId = noMoreResId;
        return this;
    }

    @NonNull
    public LoadMore setLoadFailedView(View loadFailedView) {
        this.loadFailedView = loadFailedView;
        return this;
    }

    @NonNull
    public LoadMore setLoadFailedResId(@LayoutRes int loadFailedResId) {
        this.loadFailedResId = loadFailedResId;
        return this;
    }

    public void into(@NonNull RecyclerView recyclerView) {
        if (loadMoreController == null) {
            loadMoreController = new LoadMoreController();
        }
        if (onLoadMoreListener == null) {
            onLoadMoreListener = new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull LoadMoreController controller) {
                    Log.w(TAG, "未设置OnLoadMoreListener");
                }
            };
        }
        LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(
                originalAdapter, onLoadMoreListener, loadMoreController,
                footerView, footerResId,
                noMoreView, noMoreResId,
                loadFailedView, loadFailedResId
        );
        loadMoreAdapter.setHasStableIds(originalAdapter.hasStableIds());
        recyclerView.setAdapter(loadMoreAdapter);
    }
}