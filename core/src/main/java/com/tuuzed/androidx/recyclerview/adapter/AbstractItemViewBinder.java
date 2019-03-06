package com.tuuzed.androidx.recyclerview.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public abstract class AbstractItemViewBinder<T> implements ItemTypeViewBinder<T, CommonItemViewHolder> {

    @LayoutRes
    public abstract int getLayoutId();

    @NonNull
    @Override
    public CommonItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new CommonItemViewHolder(itemView);
    }

}