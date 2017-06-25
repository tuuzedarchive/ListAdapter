package com.tuuzed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author TuuZed
 */
public abstract class ItemProvider<Item, VH extends RecyclerView.ViewHolder> {
    private Context context;

    public ItemProvider(@NonNull Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public abstract void onBindViewHolder(@NonNull VH holder, @NonNull Item item, int position);


    public abstract
    @NonNull
    VH onCreateViewHolder(ViewGroup parent, LayoutInflater inflater);
}
