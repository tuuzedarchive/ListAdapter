package com.tuuzed.androidx.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface OnBindViewHolder<T, VH extends RecyclerView.ViewHolder> {
    void onBindViewHolder(@NonNull VH holder, @NonNull T item, int position);
}
