package com.tuuzed.adapter;

import android.support.annotation.LayoutRes;

public abstract class FastItemProvider<Item> extends ItemProvider<Item> {
    private int itemLayoutId;

    public FastItemProvider(@LayoutRes int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getItemLayoutId() {
        return itemLayoutId;
    }
}
