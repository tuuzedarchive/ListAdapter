package com.tuuzed.adapter.simple.provider;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.tuuzed.adapter.ItemProvider;
import com.tuuzed.adapter.ViewHolder;
import com.tuuzed.adapter.simple.R;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class LongItemProvider extends ItemProvider<Long> {

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Long item, int position) {
        holder.$(R.id.text, TextView.class).setText("Long =>" + item);
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.item_long;
    }

}
