package com.tuuzed.adapter.simple.provider;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.tuuzed.adapter.ItemProvider;
import com.tuuzed.adapter.ViewHolder;
import com.tuuzed.adapter.simple.R;


public class StringItemProvider extends ItemProvider<String> {


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item, int position) {
        holder.$(R.id.text, TextView.class).setText("String =>" + item);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_string;
    }
}
