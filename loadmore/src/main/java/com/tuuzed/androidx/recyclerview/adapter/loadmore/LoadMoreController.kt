package com.tuuzed.androidx.recyclerview.adapter.loadmore

/**
 * 控制加载更多的开关, 作为 [的参数][OnLoadMoreListener.onLoadMore]
 */
class LoadMoreController internal constructor(private val mCallback: Callback) {

    /** 获取是否启用了加载更多 */
    var loadMoreEnabled = true
        set(enabled) {
            val canNotify = loadMoreEnabled
            field = enabled

            if (canNotify && !loadMoreEnabled) {
                mCallback.notifyChanged()
            }
        }
    private var mIsLoadFailed = false

    /**
     * 设置是否加载失败
     *
     * @param isLoadFailed 是否加载失败
     */
    fun setLoadFailed(isLoadFailed: Boolean) {
        if (mIsLoadFailed != isLoadFailed) {
            mIsLoadFailed = isLoadFailed
            mCallback.notifyLoadFailed(isLoadFailed)
            loadMoreEnabled = !mIsLoadFailed
        }
    }

    /**
     * 设置加载完全部
     */
    fun setLoadComplete() {
        loadMoreEnabled = false
        mCallback.notifyLoadComplete()
    }

    internal interface Callback {
        fun notifyChanged()

        fun notifyLoadFailed(isLoadFailed: Boolean)

        fun notifyLoadComplete()
    }

}