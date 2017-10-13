package com.tuuzed.adapter;

import android.support.annotation.NonNull;

public class EmptyItemProvider<T> extends ItemProvider<T> {

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, T item, int position) {

    }

}
