package com.tuuzed.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ItemTypeViewBinder<in T, VH : RecyclerView.ViewHolder> {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    fun onBindViewHolder(holder: VH, item: T, position: Int)
}

