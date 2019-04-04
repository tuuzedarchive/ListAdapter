package com.tuuzed.recyclerview.adapter.loadmore

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


internal class LoadMoreAdapter @JvmOverloads constructor(
        val originalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        val loadMoreState: LoadMoreState,
        var enableShowNoMore: Boolean = false,
        var footerView: View? = null,
        var noMoreView: View? = null,
        var loadFailedView: View? = null,
        @LayoutRes var footerViewLayoutRes: Int = R.layout.loadmore_footer,
        @LayoutRes var noMoreViewLayoutRes: Int = R.layout.loadmore_nomore,
        @LayoutRes var loadFailedViewLayoutRes: Int = R.layout.loadmore_loadfailed,
        val onLoadMoreListener: OnLoadMoreListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val TAG = "LoadMoreAdapter"

        private const val TYPE_FOOTER = -2
        private const val TYPE_NO_MORE = -3
        private const val TYPE_LOAD_FAILED = -4

    }

    private val mObserver: RecyclerView.AdapterDataObserver
    /** 滑动监听器，判断是否触发加载更多 */
    private val mOnScrollListener: RecyclerView.OnScrollListener


    private var mRecyclerView: RecyclerView? = null
    private var mIsLoading: Boolean = false
    private var mShouldRemove: Boolean = false
    private var mIsLoadFailed: Boolean = false

    init {
        mObserver = AdapterDataObserver()
        mOnScrollListener = OnScrollListener()
        originalAdapter.registerAdapterDataObserver(mObserver)
        loadMoreState.callback = object : LoadMoreState.Callback {
            override fun notifyChanged() {
                mShouldRemove = true
            }

            override fun notifyLoadFailed(isLoadFailed: Boolean) {
                mIsLoadFailed = isLoadFailed
                notifyItemChanged(itemCount)
            }

            override fun notifyLoadComplete() {
                enableShowNoMore = true
                notifyItemChanged(itemCount)
            }
        }
    }

    var loadMoreEnabled: Boolean
        get() = loadMoreState.loadMoreEnabled && originalAdapter.itemCount >= 0
        set(value) {
            loadMoreState.loadMoreEnabled = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FOOTER -> FooterViewHolder(getFooterView(parent))
            TYPE_NO_MORE -> NoMoreViewHolder(getNoMoreView(parent))
            TYPE_LOAD_FAILED -> LoadFailedViewHolder(getLoadFailedView(parent), loadMoreState, onLoadMoreListener)
            else -> originalAdapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (holder is FooterViewHolder) {
            // 当 RecyclerView 不能滚动的时候(item 不能铺满屏幕的时候也是不能滚动的)
            if (!canScroll() && !mIsLoading) {
                mIsLoading = true
                // 修复当RecyclerView正在计算布局或滚动时无法调用到OnLoadMoreListener#onLoadMore方法的Bug
                mRecyclerView?.post { onLoadMoreListener(loadMoreState) }
            }
        } else if (holder is NoMoreViewHolder || holder is LoadFailedViewHolder) {
            // pass
        } else {
            originalAdapter.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        val count = originalAdapter.itemCount
        return when {
            loadMoreEnabled -> count + 1
            enableShowNoMore -> count + 1
            else -> count + if (mShouldRemove) 1 else 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == originalAdapter.itemCount && mIsLoadFailed) {
            return TYPE_LOAD_FAILED
        }
        if (position == originalAdapter.itemCount && (loadMoreEnabled || mShouldRemove)) {
            return TYPE_FOOTER
        } else if (position == originalAdapter.itemCount && enableShowNoMore && !loadMoreEnabled) {
            return TYPE_NO_MORE
        }
        return originalAdapter.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        val itemViewType = getItemViewType(position)
        return if (originalAdapter.hasStableIds()
                && itemViewType != TYPE_FOOTER
                && itemViewType != TYPE_LOAD_FAILED
                && itemViewType != TYPE_NO_MORE
        ) {
            originalAdapter.getItemId(position)
        } else {
            super.getItemId(position)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        recyclerView.addOnScrollListener(mOnScrollListener)
        // 当为 GridLayoutManager 的时候, 设置 footerView 占据整整一行.
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            // 获取原来的 SpanSizeLookup,当不为 null 的时候,除了 footerView 都应该返回原来的 spanSize
            val originalSizeLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val itemViewType = getItemViewType(position)
                    if (itemViewType == TYPE_FOOTER || itemViewType == TYPE_NO_MORE ||
                            itemViewType == TYPE_LOAD_FAILED) {
                        return layoutManager.spanCount
                    } else if (originalSizeLookup != null) {
                        return originalSizeLookup.getSpanSize(position)
                    }
                    return 1
                }
            }
        }
    }

    private fun canScroll(): Boolean {
        return mRecyclerView?.canScrollVertically(-1)
                ?: throw NullPointerException("mRecyclerView is null, you should setAdapter(recyclerAdapter);")
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(mOnScrollListener)
        originalAdapter.unregisterAdapterDataObserver(mObserver)
        mRecyclerView = null
    }


    /** update last item */
    private fun notifyFooterHolderChanged() {
        if (loadMoreEnabled) {
            this@LoadMoreAdapter.notifyItemChanged(originalAdapter.itemCount)
        } else if (mShouldRemove) {
            mShouldRemove = false
            /*
              fix IndexOutOfBoundsException when setLoadMoreEnabled(false) and then use onItemRangeInserted
              @see android.support.v7.widget.RecyclerView.Recycler#validateViewHolderForOffsetPosition(RecyclerView.ViewHolder)
             */
            val position = originalAdapter.itemCount
            val viewHolder = mRecyclerView?.findViewHolderForAdapterPosition(position)
            if (viewHolder is FooterViewHolder) {
                this@LoadMoreAdapter.notifyItemRemoved(position)
            } else {
                this@LoadMoreAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun getFooterView(parent: ViewGroup): View {
        if (footerViewLayoutRes != View.NO_ID) {
            footerView = Utils.inflateView(parent, footerViewLayoutRes)
        }
        var view = noMoreView
        if (view == null) {
            view = Utils.inflateView(parent, R.layout.loadmore_footer)
        }
        return view
    }

    private fun getNoMoreView(parent: ViewGroup): View {
        if (noMoreViewLayoutRes != View.NO_ID) {
            noMoreView = Utils.inflateView(parent, noMoreViewLayoutRes)
        }
        var view = noMoreView
        if (view == null) {
            view = Utils.inflateView(parent, R.layout.loadmore_nomore)
        }
        return view
    }

    private fun getLoadFailedView(parent: ViewGroup): View {
        if (loadFailedViewLayoutRes != View.NO_ID) {
            loadFailedView = Utils.inflateView(parent, loadFailedViewLayoutRes)
        }
        var view = loadFailedView
        if (view == null) {
            view = Utils.inflateView(parent, R.layout.loadmore_loadfailed)
        }
        return view
    }

    private inner class AdapterDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (mShouldRemove) {
                mShouldRemove = false
            }
            this@LoadMoreAdapter.notifyDataSetChanged()
            mIsLoading = false
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            if (mShouldRemove && positionStart == originalAdapter.itemCount) {
                mShouldRemove = false
            }
            this@LoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount)
            mIsLoading = false
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            if (mShouldRemove && positionStart == originalAdapter.itemCount) {
                mShouldRemove = false
            }
            this@LoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
            mIsLoading = false
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            // when no data is initialized (has loadMoreView)
            // should remove loadMoreView before notifyItemRangeInserted
            mRecyclerView?.apply {
                if (this.childCount == 1) {
                    this@LoadMoreAdapter.notifyItemRemoved(0)
                }
                this@LoadMoreAdapter.notifyItemRangeInserted(positionStart, itemCount)
                notifyFooterHolderChanged()
                mIsLoading = false
            }

        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            if (mShouldRemove && positionStart == originalAdapter.itemCount) {
                mShouldRemove = false
            }
            /*
               use notifyItemRangeRemoved after clear item, can throw IndexOutOfBoundsException
               @link RecyclerView#tryGetViewHolderForPositionByDeadline
               fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position
             */
            var shouldSync = false
            if (loadMoreState.loadMoreEnabled && originalAdapter.itemCount == 0) {
                loadMoreEnabled = false
                shouldSync = true
                // when use onItemRangeInserted(0, count) after clear item
                // recyclerView will auto scroll to bottom, because has one item(loadMoreView)
                // remove loadMoreView
                if (getItemCount() == 1) {
                    this@LoadMoreAdapter.notifyItemRemoved(0)
                }
            }
            this@LoadMoreAdapter.notifyItemRangeRemoved(positionStart, itemCount)
            if (shouldSync) {
                loadMoreEnabled = true
            }
            mIsLoading = false
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            if (mShouldRemove &&
                    (fromPosition == originalAdapter.itemCount || toPosition == originalAdapter.itemCount)
            ) {
                throw IllegalArgumentException("can not move last position after setLoadMoreEnabled(false)")
            }
            this@LoadMoreAdapter.notifyItemMoved(fromPosition, toPosition)
            mIsLoading = false
        }
    }

    private inner class OnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!loadMoreEnabled || mIsLoading) {
                return
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                var isBottom = false
                val layoutManager = recyclerView.layoutManager
                when (layoutManager) {
                    is GridLayoutManager -> isBottom = layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 1
                    is LinearLayoutManager -> isBottom = layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 1
                    is StaggeredGridLayoutManager -> {
                        val into = IntArray(layoutManager.spanCount)
                        layoutManager.findLastVisibleItemPositions(into)
                        isBottom = Utils.lastPosition(into) >= layoutManager.itemCount - 1
                    }
                }
                if (isBottom) {
                    mIsLoading = true
                    onLoadMoreListener(loadMoreState)
                }
            }
        }
    }
}
