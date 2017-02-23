package io.github.tuuzed.adapter.simple.itemcomponent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.tuuzed.adapter.ItemComponent;
import io.github.tuuzed.adapter.simple.R;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */

public class LongItemComponent implements ItemComponent<Long, LongItemComponent.ViewHolder> {

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Long aLong) {
        holder.textView.setText("Long => " + aLong);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new LongItemComponent.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_long, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
