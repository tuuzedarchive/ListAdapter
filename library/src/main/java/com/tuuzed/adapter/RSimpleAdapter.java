package com.tuuzed.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class RSimpleAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Items mItems;
    private Map<Class<?>, ItemProvider> mItemProviderPools;

    public static RSimpleAdapter create() {
        return new RSimpleAdapter();
    }

    private RSimpleAdapter() {
        mItems = new Items();
        mItemProviderPools = new HashMap<>();
    }

    public <T> RSimpleAdapter register(@NonNull Class<T> clazz,
                                       @LayoutRes int layoutId,
                                       @NonNull ItemProvider<T> itemProvider) {
        itemProvider.setAdapter(this);
        itemProvider.setLayoutId(layoutId);
        mItemProviderPools.put(clazz, itemProvider);
        itemProvider.onReady();
        return this;
    }

    public RSimpleAdapter attach(@NonNull RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        Collection<ItemProvider> values = mItemProviderPools.values();
        for (ItemProvider itemProvider : values) {
            itemProvider.setContext(context);
        }
        recyclerView.setAdapter(this);
        return this;
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
        ItemProvider itemProvider = mItemProviderPools.get(clazz);
        if (itemProvider == null) {
            throw new UnregisteredItemProviderException(clazz);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(itemProvider.getLayoutId(), parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item = mItems.get(position);
        Class<?> clazz = item.getClass();
        ItemProvider itemProvider = mItemProviderPools.get(clazz);
        if (itemProvider == null) {
            throw new UnregisteredItemProviderException(clazz);
        } else {
            //noinspection unchecked
            itemProvider.onBindViewHolder(holder, item, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
