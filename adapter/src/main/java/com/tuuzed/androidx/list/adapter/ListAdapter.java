package com.tuuzed.androidx.list.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ItemViewBinder<?, ? extends RecyclerView.ViewHolder>> itemViewBinderList;
    private final List<Class<?>> itemViewTypeList;
    private List<Object> items;


    public ListAdapter() {
        this(new LinkedList<>());
    }

    public ListAdapter(@NonNull List<Object> items) {
        this(items, -1);
    }

    public ListAdapter(@NonNull List<Object> items, int itemViewTypeCount) {
        this.items = items;
        if (itemViewTypeCount == -1) {
            itemViewBinderList = new ArrayList<>();
            itemViewTypeList = new ArrayList<>();
        } else {
            itemViewBinderList = new ArrayList<>(itemViewTypeCount);
            itemViewTypeList = new ArrayList<>(itemViewTypeCount);
        }
    }

    @NonNull
    public List<Object> getItems() {
        return items;
    }

    @NonNull
    public ListAdapter setItems(@NonNull List<Object> items) {
        this.items = items;
        return this;
    }

    @NonNull
    public <T> ListAdapter bind(@NonNull Class<T> clazz, @NonNull ItemViewBinder<T, ? extends RecyclerView.ViewHolder> binder) {
        final int index = itemViewTypeList.indexOf(clazz);
        if (index == -1) {
            itemViewTypeList.add(clazz);
            itemViewBinderList.add(binder);
        } else {
            itemViewBinderList.add(index, binder);
        }
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        Class<?> clazz = items.get(position).getClass();
        return itemViewTypeList.indexOf(clazz);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemViewBinder binder = getItemViewBinder(viewType);
        assert binder != null;
        return binder.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        final ItemViewBinder binder = getItemViewBinder(viewType);
        assert binder != null;
        final Object item = items.get(position);
        //noinspection unchecked
        binder.onBindViewHolder(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Nullable
    private ItemViewBinder<?, ? extends RecyclerView.ViewHolder> getItemViewBinder(int viewType) {
        if (itemViewBinderList.size() <= viewType) {
            return null;
        }
        return itemViewBinderList.get(viewType);
    }


}
