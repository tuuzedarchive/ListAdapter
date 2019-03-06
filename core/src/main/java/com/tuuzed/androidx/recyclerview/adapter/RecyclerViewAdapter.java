package com.tuuzed.androidx.recyclerview.adapter;

import android.view.ViewGroup;

import com.tuuzed.androidx.recyclerview.adapter.internal.ItemTypePool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    private List<Object> mItems;
    @NonNull
    private final ItemTypePool mItemTypePool;

    public RecyclerViewAdapter() {
        this(new ArrayList<>(), 16);
    }

    public RecyclerViewAdapter(@NonNull List<Object> items, @IntRange(from = 1) int initTypeCapacity) {
        mItems = items;
        this.mItemTypePool = new ItemTypePool(initTypeCapacity);
    }

    @Override
    public final int getItemViewType(int position) {
        Object item = mItems.get(position);
        return mItemTypePool.indexOf(item.getClass());
    }

    @NonNull
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int indexViewType) {
        ItemTypeViewBinder<?, ?> itemTypeViewBinder = mItemTypePool.findItemTypeViewBinder(indexViewType);
        return itemTypeViewBinder.onCreateViewHolder(parent, indexViewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int indexViewType = getItemViewType(position);
        Object item = mItems.get(position);
        ItemTypeViewBinder binder = mItemTypePool.findItemTypeViewBinder(indexViewType);
        //noinspection unchecked
        binder.onBindViewHolder(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @NonNull
    public <T, VH extends RecyclerView.ViewHolder> RecyclerViewAdapter bind(
            @NonNull final Class<T> itemType,
            @NonNull final ItemTypeViewBinder<T, VH> itemTypeViewBinder
    ) {
        mItemTypePool.register(itemType, itemTypeViewBinder);
        return this;
    }

    @NonNull
    public <T, VH extends RecyclerView.ViewHolder> ItemTypeViewBinder<T, VH> bind(
            @NonNull final Class<T> itemType,
            @NonNull final OnCreateViewHolder<VH> onCreateViewHolder,
            @NonNull final OnBindViewHolder<T, VH> onBindViewHolder
    ) {
        ItemTypeViewBinder<T, VH> binder = new ItemTypeViewBinder<T, VH>() {
            @Override
            public void onBindViewHolder(@NonNull VH holder, @NonNull T item, int position) {
                onBindViewHolder.onBindViewHolder(holder, item, position);
            }

            @NonNull
            @Override
            public VH onCreateViewHolder(ViewGroup parent, int viewType) {
                return onCreateViewHolder.onCreateViewHolder(parent, viewType);
            }
        };
        bind(itemType, binder);
        return binder;
    }

    @NonNull
    public <T> ItemTypeViewBinder<T, CommonItemViewHolder> bind(
            @NonNull final Class<T> itemType,
            @LayoutRes final int layoutId,
            @NonNull final OnBindViewHolder<T, CommonItemViewHolder> onBindViewHolder
    ) {
        ItemTypeViewBinder<T, CommonItemViewHolder> binder = new AbstractItemViewBinder<T>() {
            @Override
            public int getLayoutId() {
                return layoutId;
            }

            @Override
            public void onBindViewHolder(@NonNull CommonItemViewHolder holder, @NonNull T item, int position) {
                onBindViewHolder.onBindViewHolder(holder, item, position);
            }
        };
        bind(itemType, binder);
        return binder;
    }

    @NonNull
    public RecyclerViewAdapter with(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(this);
        return this;
    }

    @NonNull
    public List<Object> items() {
        return getItems();
    }

    @NonNull
    public List<Object> getItems() {
        return mItems;
    }

    @NonNull
    public RecyclerViewAdapter setItems(@NonNull List<Object> items) {
        this.mItems = items;
        return this;
    }


    @NonNull
    public RecyclerViewAdapter appendItems(@NonNull List<Object> items) {
        this.mItems.addAll(items);
        return this;
    }

    @NonNull
    public RecyclerViewAdapter appendItems(@NonNull Object... items) {
        Collections.addAll(mItems, items);
        return this;
    }
}