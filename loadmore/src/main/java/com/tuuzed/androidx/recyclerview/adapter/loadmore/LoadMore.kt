package com.tuuzed.androidx.recyclerview.adapter.loadmore

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter

@JvmOverloads
fun RecyclerViewAdapter.withLoadMore(
        recyclerView: RecyclerView,
        loadMoreState: LoadMoreState = LoadMoreState(),
        enableLoadMore: Boolean = true,
        enableShowNoMore: Boolean = true,
        footerView: View? = null,
        noMoreView: View? = null,
        loadFailedView: View? = null,
        @LayoutRes footerViewLayoutRes: Int = View.NO_ID,
        @LayoutRes noMoreViewLayoutRes: Int = View.NO_ID,
        @LayoutRes loadFailedViewLayoutRes: Int = View.NO_ID,
        listener: OnLoadMoreListener
): RecyclerViewAdapter {
    LoadMore.with(
            recyclerView,
            loadMoreState,
            enableLoadMore,
            enableShowNoMore,
            footerView, noMoreView,
            loadFailedView,
            footerViewLayoutRes,
            noMoreViewLayoutRes,
            loadFailedViewLayoutRes,
            listener
    )
    return this
}

object LoadMore {

    @JvmOverloads
    fun with(recyclerView: RecyclerView,
             loadMoreState: LoadMoreState = LoadMoreState(),
             enableLoadMore: Boolean = true,
             enableShowNoMore: Boolean = true,
             footerView: View? = null,
             noMoreView: View? = null,
             loadFailedView: View? = null,
             @LayoutRes footerViewLayoutRes: Int = View.NO_ID,
             @LayoutRes noMoreViewLayoutRes: Int = View.NO_ID,
             @LayoutRes loadFailedViewLayoutRes: Int = View.NO_ID,
             listener: OnLoadMoreListener
    ) {
        val adapter = recyclerView.adapter
                ?: throw NullPointerException("recyclerView.adapter == null")
        val loadMoreAdapter = LoadMoreAdapter(
                originalAdapter = adapter,
                loadMoreState = loadMoreState,
                enableShowNoMore = enableShowNoMore,
                footerView = footerView,
                noMoreView = noMoreView,
                loadFailedView = loadFailedView,
                footerViewLayoutRes = footerViewLayoutRes,
                noMoreViewLayoutRes = noMoreViewLayoutRes,
                loadFailedViewLayoutRes = loadFailedViewLayoutRes,
                onLoadMoreListener = listener
        ).also {
            it.setHasStableIds(it.originalAdapter.hasStableIds())
        }
        loadMoreAdapter.loadMoreEnabled = enableLoadMore
        recyclerView.adapter = loadMoreAdapter
    }


}
