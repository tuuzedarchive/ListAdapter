package com.tuuzed.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Items mItems;
    private Map<Class<?>, ItemProvider> mPool;

    public void register(@NonNull Class<?> clazz, @NonNull ItemProvider provider) {
        mPool.put(clazz, provider);
    }

    public RecyclerViewAdapter(@NonNull Items items) {
        mItems = items;
        mPool = new HashMap<>();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProvider itemView = mPool.get(mItems.get(viewType).getClass());
        if (itemView == null) {
            throw new NotFindItemProvider();
        }
        return itemView.onCreateViewHolder(parent, LayoutInflater.from(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mItems.get(position);
        ItemProvider itemView = mPool.get(item.getClass());
        if (itemView == null) {
            throw new NotFindItemProvider();
        } else {
            itemView.onBindViewHolder(holder, item, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
