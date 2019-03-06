package com.tuuzed.androidx.recyclerview.adapter;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTypeViewBinder<T, VH extends RecyclerView.ViewHolder>
        extends OnCreateViewHolder<VH>, OnBindViewHolder<T, VH> {
}
