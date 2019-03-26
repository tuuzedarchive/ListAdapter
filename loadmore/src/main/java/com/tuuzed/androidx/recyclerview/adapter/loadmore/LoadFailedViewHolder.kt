package com.tuuzed.androidx.recyclerview.adapter.loadmore

import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class LoadFailedViewHolder(
        itemView: View,
        enabled: LoadMoreController,
        listener: OnLoadMoreListener?
) : RecyclerView.ViewHolder(itemView) {

    init {
        Utils.setItemViewFullSpan(itemView)
        itemView.setOnClickListener {
            enabled.setLoadFailed(false)
            listener?.onLoadMore(enabled)
        }
    }

}