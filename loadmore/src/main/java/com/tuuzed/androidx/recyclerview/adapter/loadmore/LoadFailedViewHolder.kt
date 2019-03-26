package com.tuuzed.androidx.recyclerview.adapter.loadmore

import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class LoadFailedViewHolder(
        itemView: View,
        loadMoreState: LoadMoreState,
        listener: OnLoadMoreListener?
) : RecyclerView.ViewHolder(itemView) {

    init {
        Utils.setItemViewFullSpan(itemView)
        itemView.setOnClickListener {
            loadMoreState.setLoadFailed(false)
            listener?.let { it(loadMoreState) }
        }
    }

}