package com.tuuzed.androidx.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView

interface ItemTypeViewBinder<in T, VH : RecyclerView.ViewHolder> : OnCreateViewHolder<VH>, OnBindViewHolder<T, VH>
