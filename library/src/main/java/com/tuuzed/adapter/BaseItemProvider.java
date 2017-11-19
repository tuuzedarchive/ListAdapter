package com.tuuzed.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public abstract class BaseItemProvider<T> {
    private Context mContext;
    private BaseAdapter mAdapter;
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

    void setAdapter(@NonNull BaseAdapter adapter) {
        this.mAdapter = adapter;
    }

    @NonNull
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 全部参数准备完毕时回调
     */
    public void onReady() {
    }

    public abstract void onBindViewHolder(@NonNull BaseViewHolder holder, T item, int position);
}
