package com.tuuzed.androidx.list.pageable;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import com.tuuzed.androidx.list.loadmore.LoadMoreController;
import com.tuuzed.androidx.list.loadmore.OnLoadMoreListener;

import java.util.List;

public abstract class PagedListDataFetcher<T> implements OnLoadMoreListener {
    private static final String TAG = "PagedListDataFetcher";

    public interface FetchFunction<T> {
        @UiThread
        void complete(@NonNull PagedList<T> pagedList);

        @UiThread
        void cancel();
    }

    /**
     * 初始页码
     */
    private final int initialPage;
    /**
     * 单页数据数量
     */
    private final int pageSize;
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 标识是否正在拉取数据
     */
    private volatile boolean fetching = false;

    public PagedListDataFetcher() {
        this(1, 20);
    }

    public PagedListDataFetcher(int initialPage, int pageSize) {
        this.initialPage = initialPage;
        this.pageSize = pageSize;
        this.currentPage = initialPage;
    }

    @Override
    public final void onLoadMore(@NonNull LoadMoreController controller) {
        fetchListData(false, controller);
    }

    @UiThread
    public final void fetchListData(final boolean refresh, final LoadMoreController loadMoreController) {
        final int page = refresh ? initialPage : currentPage;
        Log.d(TAG, "fetchListData: page= " + page + ", pageSize=" + pageSize);
        this.fetching = true;
        onFetchListData(page, pageSize, new FetchFunction<T>() {
            @Override
            public void complete(@NonNull PagedList<T> pagedList) {
                if (pagedList.error) {
                    // 加载失败
                    loadMoreController.setLoadFailed(true);
                    onLoadFailed(initialPage, page, pagedList.cause);
                } else {
                    // 加载成功
                    loadMoreController.setLoadFailed(false);
                    final List<T> payload = pagedList.payload;
                    onLoadSucceed(initialPage, page, payload);
                    if (refresh) {
                        currentPage = initialPage;
                        loadMoreController.reset();
                    }
                    currentPage++;
                    if (payload.size() < pageSize) {
                        // 已经全部加载完毕, 禁用加载更多功能
                        loadMoreController.setNoMore();
                        Log.v(TAG, "已经全部加载完毕, 禁用加载更多功能");
                    }
                }
                PagedListDataFetcher.this.fetching = false;
            }

            @Override
            public void cancel() {
                PagedListDataFetcher.this.fetching = false;
            }
        });
    }

    public boolean isFetching() {
        return fetching;
    }

    @UiThread
    public abstract void onFetchListData(int page, int pageSize, @NonNull FetchFunction<T> fetchFunction);

    @UiThread
    public abstract void onLoadFailed(int initialPage, int page, @NonNull Throwable cause);

    @UiThread
    public abstract void onLoadSucceed(int initialPage, int page, @NonNull List<T> listData);


}
