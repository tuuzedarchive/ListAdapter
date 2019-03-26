package com.tuuzed.androidx.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView

interface OnBindViewHolder<in T, in VH : RecyclerView.ViewHolder> {
    fun onBindViewHolder(holder: VH, item: T, position: Int)
}
