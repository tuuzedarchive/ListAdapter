package com.tuuzed.androidx.list.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface ItemViewBinder<T, VH extends RecyclerView.ViewHolder> {

    @NonNull
    VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull VH holder, T t, int position);

}