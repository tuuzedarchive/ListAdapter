package com.tuuzed.adapter;

import android.support.annotation.LayoutRes;

public abstract class SimpleItemProvider<Item> extends ItemProvider<Item> {
    private int itemLayoutId;

    public SimpleItemProvider(@LayoutRes int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getItemLayoutId() {
        return itemLayoutId;
    }
}
