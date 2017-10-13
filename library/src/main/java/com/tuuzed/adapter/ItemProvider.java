package com.tuuzed.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public abstract class ItemProvider<T> {
    private Context mContext;
    private RSimpleAdapter mAdapter;
    private int mLayoutId;

    void setLayoutId(@LayoutRes int layoutId) {
        this.mLayoutId = layoutId;
    }

    @LayoutRes
    public int getLayoutId() {
        return mLayoutId;
    }

    void setContext(@NonNull Context context) {
        this.mContext = context;
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    void setAdapter(@NonNull RSimpleAdapter adapter) {
        this.mAdapter = adapter;
    }

    @NonNull
    public RSimpleAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 全部参数准备完毕时回调
     */
    void onReady() {
    }

    public abstract void onBindViewHolder(@NonNull ViewHolder holder, T item, int position);
}
