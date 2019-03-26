package com.tuuzed.androidx.recyclerview.adapter.loadmore

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 在不改动 RecyclerView 原有 adapter 的情况下，使其拥有加载更多功能和自定义底部视图。
 */
internal class LoadMoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    companion object {

        private const val TAG = "LoadMoreAdapter"

        private const val TYPE_FOOTER = -2
        private const val TYPE_NO_MORE = -3
        private const val TYPE_LOAD_FAILED = -4

        /**
         * 取到最后的一个节点
         */
        private fun last(lastPositions: IntArray): Int {
            var last = lastPositions[0]
            for (value in lastPositions) {
                if (value > last) {
                    last = value
                }
            }
            return last
        }
    }

    lateinit var originalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
        private set

    private var mFooterResId = View.NO_ID
    private var mNoMoreResId = View.NO_ID
    private var mLoadFailedResId = View.NO_ID

    var footerView: View? = null
    var noMoreView: View? = null
    var loadFailedView: View? = null

    private var mRecyclerView: RecyclerView? = null
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var mLoadMoreController: LoadMoreController? = null

    private var mIsLoading: Boolean = false
    private var mShouldRemove: Boolean = false
    private var mShowNoMoreEnabled: Boolean = false
    private var mIsLoadFailed: Boolean = false

    constructor(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>) {
        registerAdapter(adapter)
    }

    constructor(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>, footerView: View) {
        registerAdapter(adapter)
        this.footerView = footerView
    }

    constructor(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>, @LayoutRes resId: Int) {
        registerAdapter(adapter)
        mFooterResId = resId
    }

    /**
     * Deciding whether to trigger loading
     * 判断是否触发加载更多
     */
    private val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!loadMoreEnabled || mIsLoading) {
                return
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mOnLoadMoreListener != null) {
                var isBottom = false
                val layoutManager = recyclerView.layoutManager
                when (layoutManager) {
                    is GridLayoutManager -> isBottom = layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 1
                    is LinearLayoutManager -> isBottom = layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 1
                    is StaggeredGridLayoutManager -> {
                        val sgLayoutManager = layoutManager as StaggeredGridLayoutManager?
                        val into = IntArray(sgLayoutManager!!.spanCount)
                        sgLayoutManager.findLastVisibleItemPositions(into)
                        isBottom = last(into) >= layoutManager.itemCount - 1
                    }
                }
                if (isBottom) {
                    mIsLoading = true
                    mOnLoadMoreListener!!.onLoadMore(mLoadMoreController!!)
                }
            }
        }
    }

    var loadMoreEnabled: Boolean
        get() = mLoadMoreController!!.loadMoreEnabled && originalAdapter.getItemCount() >= 0
        set(enabled) {
            mLoadMoreController!!.loadMoreEnabled = enabled
        }


    private val mObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            if (mShouldRemove) {
                mShouldRemove = false
            }
            this@LoadMoreAdapter.notifyDataSetChanged()
            mIsLoading = false
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            if (mShouldRemove && positionStart == originalAdapter.getItemCount()) {
                mShouldRemove = false
            }
            this@LoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount)
            mIsLoading = false
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            if (mShouldRemove && positionStart == originalAdapter.getItemCount()) {
                mShouldRemove = false
            }
            this@LoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
            mIsLoading = false
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            // when no data is initialized (has loadMoreView)
            // should remove loadMoreView before notifyItemRangeInserted
            if (mRecyclerView!!.childCount == 1) {
                this@LoadMoreAdapter.notifyItemRemoved(0)
            }
            this@LoadMoreAdapter.notifyItemRangeInserted(positionStart, itemCount)
            notifyFooterHolderChanged()
            mIsLoading = false
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            if (mShouldRemove && positionStart == originalAdapter.getItemCount()) {
                mShouldRemove = false
            }
            /*
               use notifyItemRangeRemoved after clear item, can throw IndexOutOfBoundsException
               @link RecyclerView#tryGetViewHolderForPositionByDeadline
               fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position
             */
            var shouldSync = false
            if (mLoadMoreController!!.loadMoreEnabled && originalAdapter.getItemCount() == 0) {
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
                    (fromPosition == originalAdapter.getItemCount() || toPosition == originalAdapter.getItemCount())
            ) {
                throw IllegalArgumentException("can not move last position after setLoadMoreEnabled(false)")
            }
            this@LoadMoreAdapter.notifyItemMoved(fromPosition, toPosition)
            mIsLoading = false
        }
    }

    private fun registerAdapter(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>) {
        originalAdapter = adapter
        originalAdapter.registerAdapterDataObserver(mObserver)
        mLoadMoreController = LoadMoreController(object : LoadMoreController.Callback {
            override fun notifyChanged() {
                mShouldRemove = true
            }

            override fun notifyLoadFailed(isLoadFailed: Boolean) {
                mIsLoadFailed = isLoadFailed
                notifyFooterHolderChanged()
            }

            override fun notifyLoadComplete() {
                setShowNoMoreEnabled(true)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_FOOTER -> {
                if (mFooterResId != View.NO_ID) {
                    footerView = Utils.inflateView(parent, mFooterResId)
                }
                if (footerView != null) {
                    return FooterViewHolder(footerView!!)
                }
                val view = Utils.inflateView(parent, R.layout.loadmore_footer)
                return FooterViewHolder(view)
            }
            TYPE_NO_MORE -> {
                if (mNoMoreResId != View.NO_ID) {
                    noMoreView = Utils.inflateView(parent, mNoMoreResId)
                }
                if (noMoreView != null) {
                    return NoMoreViewHolder(noMoreView!!)
                }
                val view = Utils.inflateView(parent, R.layout.loadmore_nomore)
                return NoMoreViewHolder(view)
            }
            TYPE_LOAD_FAILED -> {
                if (mLoadFailedResId != View.NO_ID) {
                    loadFailedView = Utils.inflateView(parent, mLoadFailedResId)
                }
                var view = loadFailedView
                if (view == null) {
                    view = Utils.inflateView(parent, R.layout.loadmore_loadfailed)
                }
                return LoadFailedViewHolder(view, mLoadMoreController!!, mOnLoadMoreListener)
            }
            else -> {
                return originalAdapter.onCreateViewHolder(parent, viewType)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (holder is FooterViewHolder) {
            // 当 RecyclerView 不能滚动的时候(item 不能铺满屏幕的时候也是不能滚动的)
            // call loadMore
            if (!canScroll() && mOnLoadMoreListener != null && !mIsLoading) {
                mIsLoading = true
                // 修复当RecyclerView正在计算布局或滚动时无法调用到OnLoadMoreListener#onLoadMore方法的Bug
                mRecyclerView!!.post { mOnLoadMoreListener!!.onLoadMore(mLoadMoreController!!) }
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
            mShowNoMoreEnabled -> count + 1
            else -> count + if (mShouldRemove) 1 else 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == originalAdapter.itemCount && mIsLoadFailed) {
            return TYPE_LOAD_FAILED
        }
        if (position == originalAdapter.itemCount && (loadMoreEnabled || mShouldRemove)) {
            return TYPE_FOOTER
        } else if (position == originalAdapter.itemCount && mShowNoMoreEnabled && !loadMoreEnabled) {
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

    fun setFooterView(@LayoutRes resId: Int) {
        mFooterResId = resId
    }

    fun setNoMoreView(@LayoutRes resId: Int) {
        mNoMoreResId = resId
    }

    fun setLoadFailedView(@LayoutRes resId: Int) {
        mLoadFailedResId = resId
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
        if (mRecyclerView == null) {
            throw NullPointerException("mRecyclerView is null, you should setAdapter(recyclerAdapter);")
        }
        return mRecyclerView!!.canScrollVertically(-1)
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(mOnScrollListener)
        originalAdapter.unregisterAdapterDataObserver(mObserver)
        mRecyclerView = null
    }

    fun setLoadMoreListener(listener: OnLoadMoreListener) {
        mOnLoadMoreListener = listener
    }

    fun setShouldRemove(shouldRemove: Boolean) {
        mShouldRemove = shouldRemove
    }

    fun setShowNoMoreEnabled(showNoMoreEnabled: Boolean) {
        mShowNoMoreEnabled = showNoMoreEnabled
    }

    fun setLoadFailed(isLoadFailed: Boolean) {
        mLoadMoreController!!.setLoadFailed(isLoadFailed)
    }

    /**
     * update last item
     */
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
            val viewHolder = mRecyclerView!!.findViewHolderForAdapterPosition(position)
            if (viewHolder is FooterViewHolder) {
                this@LoadMoreAdapter.notifyItemRemoved(position)
            } else {
                this@LoadMoreAdapter.notifyItemChanged(position)
            }
        }
    }


}
