package com.tuuzed.androidx.recyclerview.adapter.loadmore

/**
 * 加载更多状态
 */
class LoadMoreState {

    /** 获取是否启用了加载更多 */
    var loadMoreEnabled = true
        internal set(value) {
            val canNotify = loadMoreEnabled
            field = value
            if (canNotify && !loadMoreEnabled) {
                callback?.notifyChanged()
            }
        }

    private var mIsLoadFailed = false

    internal var callback: Callback? = null

    /**
     * 设置是否加载失败
     *
     * @param isLoadFailed 是否加载失败
     */
    fun setLoadFailed(isLoadFailed: Boolean) {
        if (mIsLoadFailed != isLoadFailed) {
            mIsLoadFailed = isLoadFailed
            callback?.notifyLoadFailed(isLoadFailed)
            loadMoreEnabled = !mIsLoadFailed
        }
    }

    fun setRefresh() {
        loadMoreEnabled = true
    }

    /** 设置加载完全部 */
    fun setLoadComplete() {
        loadMoreEnabled = false
        callback?.notifyLoadComplete()
    }


    internal interface Callback {

        fun notifyChanged()

        fun notifyLoadFailed(isLoadFailed: Boolean)

        fun notifyLoadComplete()

    }

}