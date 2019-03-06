package com.tuuzed.androidx.recyclerview.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface OnCreateViewHolder<VH extends RecyclerView.ViewHolder> {
    @NonNull
    VH onCreateViewHolder(ViewGroup parent, int viewType);
}
