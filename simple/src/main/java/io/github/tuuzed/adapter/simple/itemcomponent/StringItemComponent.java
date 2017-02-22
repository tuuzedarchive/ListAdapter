package io.github.tuuzed.adapter.simple.itemcomponent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.tuuzed.adapter.ItemComponent;
import io.github.tuuzed.adapter.simple.R;


public class StringItemComponent implements ItemComponent<String, StringItemComponent.ViewHolder> {


    @Override
    public void onBindViewHolder(ViewHolder holder, String s) {
        holder.textView.setText("String => " + s);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string, parent, false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}
