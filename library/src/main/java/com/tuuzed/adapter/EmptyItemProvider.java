package com.tuuzed.adapter;

import android.support.annotation.NonNull;

public class EmptyItemProvider<T> extends BaseItemProvider<T> {

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, T item, int position) {

    }

}
