package com.tuuzed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private Items mItems;
    private Map<Class<?>, ItemProvider> mItemProviderPool;

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        this(recyclerView.getContext());
        recyclerView.setAdapter(this);
    }
    
    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView, @NonNull Items items) {
        this(recyclerView.getContext(), items);
        recyclerView.setAdapter(this);
    }

    public RecyclerViewAdapter(@NonNull Context context) {
        this(context, new Items());
    }

    public RecyclerViewAdapter(@NonNull Context context, @NonNull Items items) {
        mContext = context;
        mItems = items;
        mItemProviderPool = new HashMap<>();
    }

    public <T> void register(@NonNull Class<T> clazz, @NonNull ItemProvider<T> provider) {
        provider.setAdapter(this);
        provider.setContext(mContext);
        mItemProviderPool.put(clazz, provider);
        provider.onReady();
    }

    @NonNull
    public Items items() {
        return mItems;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<?> clazz = mItems.get(viewType).getClass();
        ItemProvider itemProvider = mItemProviderPool.get(clazz);
        if (itemProvider == null) {
            throw new UnregisteredItemProviderException(clazz);
        }
        View itemView = LayoutInflater.from(mContext).inflate(itemProvider.getItemLayoutId(), parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item = mItems.get(position);
        Class<?> clazz = item.getClass();
        ItemProvider itemProvider = mItemProviderPool.get(clazz);
        if (itemProvider == null) {
            throw new UnregisteredItemProviderException(clazz);
        } else {
            itemProvider.onBindViewHolder(holder, item, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
