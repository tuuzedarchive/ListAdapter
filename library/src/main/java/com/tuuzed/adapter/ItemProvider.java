package com.tuuzed.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public abstract class ItemProvider<Item> {
    private Context context;
    private RecyclerViewAdapter adapter;

    void setContext(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    void setAdapter(@NonNull RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @NonNull
    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    /**
     * 全部参数准备完毕时回调
     */
    void onReady() {
    }

    public abstract void onBindViewHolder(@NonNull ViewHolder holder, Item item, int position);

    @LayoutRes
    public abstract int getItemLayoutId();
}
