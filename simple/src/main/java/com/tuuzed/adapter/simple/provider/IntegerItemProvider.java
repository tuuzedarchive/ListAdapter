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
public class IntegerItemProvider extends ItemProvider<Integer> {


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Integer item, int position) {
        holder.$(R.id.text, TextView.class).setText("Integer =>" + item);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_integer;
    }
}
