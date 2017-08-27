package com.tuuzed.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public class EmptyItemProvider<Item> extends FastItemProvider<Item> {

    public EmptyItemProvider(@LayoutRes int itemLayoutId) {
        super(itemLayoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, Item item, int position) {

    }

}
