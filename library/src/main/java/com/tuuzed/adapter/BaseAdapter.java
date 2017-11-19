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
public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Items mItems;
    private Map<Class<?>, BaseItemProvider> mItemProviderPools;

    public static BaseAdapter create() {
        return new BaseAdapter();
    }

    private BaseAdapter() {
        mItems = new Items();
        mItemProviderPools = new HashMap<>();
    }

    public <T> BaseAdapter register(@NonNull Class<T> type,
                                    @LayoutRes int layoutId,
                                    @NonNull BaseItemProvider<T> itemProvider) {
        itemProvider.setAdapter(this);
        itemProvider.setLayoutId(layoutId);
        mItemProviderPools.put(type, itemProvider);
        return this;
    }

    public BaseAdapter attach(@NonNull RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final Collection<BaseItemProvider> values = mItemProviderPools.values();
        for (BaseItemProvider itemProvider : values) {
            itemProvider.setContext(context);
            itemProvider.onReady();
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
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Class<?> clazz = mItems.get(viewType).getClass();
        BaseItemProvider itemProvider = mItemProviderPools.get(clazz);
        if (itemProvider == null) {
            throw new UnregisteredItemProviderException(clazz);
        }
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(itemProvider.getLayoutId(), parent, false);
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object item = mItems.get(position);
        Class<?> clazz = item.getClass();
        BaseItemProvider itemProvider = mItemProviderPools.get(clazz);
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
