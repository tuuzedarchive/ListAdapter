package com.tuuzed.adapter;

import android.content.Context;
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
    private Context mContext;
    private Items mItems;
    private Map<Class<?>, ItemProvider> mPool;

    public void register(@NonNull Class<?> clazz, @NonNull ItemProvider provider) {
        provider.setAdapter(this);
        provider.setContext(mContext);
        mPool.put(clazz, provider);
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull Items items) {
        mContext = context;
        mItems = items;
        mPool = new HashMap<>();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<?> clazz = mItems.get(viewType).getClass();
        ItemProvider itemView = mPool.get(clazz);
        if (itemView == null) {
            throw new UnregisteredItemProviderException(clazz);
        }
        return itemView.onCreateViewHolder(parent, LayoutInflater.from(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mItems.get(position);
        Class<?> clazz = item.getClass();
        ItemProvider itemView = mPool.get(clazz);
        if (itemView == null) {
            throw new UnregisteredItemProviderException(clazz);
        } else {
            itemView.onBindViewHolder(holder, item, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
