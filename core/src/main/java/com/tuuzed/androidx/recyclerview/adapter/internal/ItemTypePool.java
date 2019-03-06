package com.tuuzed.androidx.recyclerview.adapter.internal;

import com.tuuzed.androidx.recyclerview.adapter.ItemTypeViewBinder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public final class ItemTypePool {
    @NonNull
    private List<ItemTypeViewBinder<?, ?>> mItemTypeViewBinders;
    @NonNull
    private List<Class<?>> mItemTypes;

    public ItemTypePool(int initCapacity) {
        mItemTypeViewBinders = new ArrayList<>(initCapacity);
        mItemTypes = new ArrayList<>(initCapacity);
    }

    public <T> void register(Class<T> itemType, ItemTypeViewBinder<T, ?> itemTypeBinder) {
        int index = indexOfItemType(itemType);
        if (index == -1) {
            mItemTypes.add(itemType);
            mItemTypeViewBinders.add(itemTypeBinder);
        } else {
            mItemTypeViewBinders.set(index, itemTypeBinder);
        }
    }

    public int indexOf(@NonNull Class<?> itemType) {
        int itemViewType = indexOfItemType(itemType);
        if (itemViewType == -1) {
            throw new IllegalStateException("Not registered (" + itemType.getName() + ") type.");
        }
        return itemViewType;
    }

    @NonNull
    public ItemTypeViewBinder<?, ?> findItemTypeViewBinder(int viewType) {
        if (mItemTypeViewBinders.size() < viewType - 1) {
            throw new IllegalStateException("Not viewType (" + viewType + ") type.");
        }
        return mItemTypeViewBinders.get(viewType);
    }

    private int indexOfItemType(@NonNull Class<?> itemType) {
        return mItemTypes.indexOf(itemType);
    }

}