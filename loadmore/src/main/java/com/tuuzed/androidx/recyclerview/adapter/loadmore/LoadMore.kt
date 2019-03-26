package com.tuuzed.androidx.recyclerview.adapter.loadmore

import android.view.View

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


class LoadMore private constructor(private val mLoadMoreAdapter: LoadMoreAdapter) {

    companion object {
        fun with(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>): LoadMore {
            val loadMoreAdapter = LoadMoreAdapter(adapter)
            return LoadMore(loadMoreAdapter)
        }
    }

    val footerView: View?
        get() = mLoadMoreAdapter.footerView

    val noMoreView: View?
        get() = mLoadMoreAdapter.noMoreView

    val loadFailedView: View?
        get() = mLoadMoreAdapter.loadFailedView

    /**
     * 获取原来的 adapter
     */
    val originalAdapter: RecyclerView.Adapter<*>
        get() = mLoadMoreAdapter.originalAdapter

    fun setFooterView(@LayoutRes resId: Int): LoadMore {
        mLoadMoreAdapter.setFooterView(resId)
        return this
    }

    fun setFooterView(footerView: View): LoadMore {
        mLoadMoreAdapter.footerView = footerView
        return this
    }

    fun setNoMoreView(@LayoutRes resId: Int): LoadMore {
        mLoadMoreAdapter.setNoMoreView(resId)
        return this
    }

    fun setNoMoreView(noMoreView: View): LoadMore {
        mLoadMoreAdapter.noMoreView = noMoreView
        return this
    }

    fun setLoadFailedView(@LayoutRes resId: Int): LoadMore {
        mLoadMoreAdapter.setLoadFailedView(resId)
        return this
    }

    fun setLoadFailedView(view: View): LoadMore {
        mLoadMoreAdapter.loadFailedView = view
        return this
    }

    /**
     * 监听加载更多触发事件
     *
     * @param listener [OnLoadMoreListener]
     */
    fun setListener(listener: OnLoadMoreListener): LoadMore {
        mLoadMoreAdapter.setLoadMoreListener(listener)
        return this
    }

    /**
     * 设置是否启用加载更多
     *
     * @param enabled default true
     */
    fun setLoadMoreEnabled(enabled: Boolean): LoadMore {
        mLoadMoreAdapter.loadMoreEnabled = enabled
        if (!enabled) {
            mLoadMoreAdapter.setShouldRemove(true)
        }
        return this
    }

    /**
     * 设置全部加载完后是否显示没有更多视图
     *
     * @param enabled default false
     */
    fun setShowNoMoreEnabled(enabled: Boolean): LoadMore {
        mLoadMoreAdapter.setShowNoMoreEnabled(enabled)
        return this
    }

    /**
     * 设置加载失败
     */
    fun setLoadFailed(isLoadFailed: Boolean) {
        mLoadMoreAdapter.setLoadFailed(isLoadFailed)
    }

    fun into(recyclerView: RecyclerView): LoadMore {
        mLoadMoreAdapter.setHasStableIds(mLoadMoreAdapter.originalAdapter.hasStableIds())
        recyclerView.adapter = mLoadMoreAdapter
        return this
    }


}
