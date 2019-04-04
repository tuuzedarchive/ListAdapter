@file:Suppress("MemberVisibilityCanBePrivate")

package com.tuuzed.recyclerview.adapter.pageable

import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tuuzed.recyclerview.adapter.RecyclerViewAdapter
import com.tuuzed.recyclerview.adapter.loadmore.LoadMore
import com.tuuzed.recyclerview.adapter.loadmore.LoadMoreState


open class PageableListDataFetcher(
        private val listAdapter: RecyclerViewAdapter,
        private val swipeRefreshLayout: SwipeRefreshLayout,
        recyclerView: RecyclerView,
        private val startPage: Int = 0,
        private val pageSize: Int = 10,
        footerView: View? = null,
        noMoreView: View? = null,
        loadFailedView: View? = null,
        @LayoutRes footerViewLayoutRes: Int = View.NO_ID,
        @LayoutRes noMoreViewLayoutRes: Int = View.NO_ID,
        @LayoutRes loadFailedViewLayoutRes: Int = View.NO_ID,
        private val export: Export
) {

    interface Export {
        fun onFetchDataTaskAsync(page: Int, size: Int, callback: FetcherCallback)
        fun onShowEmptyView(enable: Boolean, msg: String = "暂无数据", error: Throwable? = null)
    }

    companion object {
        private const val TAG = "PageableListDataFetcher"
    }

    var page = startPage
        private set
    val loadMoreState = LoadMoreState()

    init {
        swipeRefreshLayout.setOnRefreshListener { fetchData(true) }
        LoadMore.with(
                recyclerView = recyclerView,
                loadMoreState = loadMoreState,

                footerView = footerView,
                noMoreView = noMoreView,
                loadFailedView = loadFailedView,

                footerViewLayoutRes = footerViewLayoutRes,
                noMoreViewLayoutRes = noMoreViewLayoutRes,
                loadFailedViewLayoutRes = loadFailedViewLayoutRes
        ) {
            fetchData(false)
        }
    }


    /** 拉取数据 */
    fun fetchData(refresh: Boolean) {
        export.onShowEmptyView(false)
        val fetchPage = if (refresh) startPage else page
        export.onFetchDataTaskAsync(fetchPage, pageSize) {
            swipeRefreshLayout.isRefreshing = false
            if (it.error) {
                loadMoreState.setLoadFailed(true)
                Log.e(TAG, "fetchPage=$fetchPage => 加载列表失败", it.tr)
                export.onShowEmptyView(listAdapter.items.isEmpty(), "加载失败，点击重新加载（${it.msg}）", it.tr)
            } else {
                loadMoreState.setLoadFailed(false)
                val original = it.payload
                if (refresh) {
                    // 刷新列表，启用加载更多功能
                    page = startPage
                    listAdapter.items.clear()
                    loadMoreState.setRefresh()
                }
                @Suppress("UNCHECKED_CAST")
                listAdapter.items.addAll(original as List<Any>)
                page++
                if (original.size < pageSize) {
                    // 已经全部加载完毕, 禁用加载更多功能
                    loadMoreState.setLoadComplete()
                    Log.v(TAG, "已经全部加载完毕, 禁用加载更多功能")
                }
                Log.d(TAG, "fetchPage=$fetchPage, fetchSize=${original.size}, ListSize=${listAdapter.items.size}")
                listAdapter.notifyDataSetChanged()
                export.onShowEmptyView(listAdapter.items.isEmpty())
            }
        }
    }
}