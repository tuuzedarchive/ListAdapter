package com.tuuzed.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public class EmptyItemProvider<Item> extends ItemProvider<Item> {
    private int itemLayoutId;

    public EmptyItemProvider(@LayoutRes int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Item item, int position) {
        
    }

    @Override
    public int getItemLayoutId() {
        return itemLayoutId;
    }
}
