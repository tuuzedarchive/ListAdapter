package com.tuuzed.androidx.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface OnCreateViewHolder<out VH : RecyclerView.ViewHolder> {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
}
