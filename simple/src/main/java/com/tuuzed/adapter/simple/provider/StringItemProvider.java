package com.tuuzed.adapter.simple.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuuzed.adapter.ItemProvider;
import com.tuuzed.adapter.simple.R;


public class StringItemProvider extends ItemProvider<String, StringItemProvider.ViewHolder> {


    public StringItemProvider(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item, int position) {
        holder.textView.setText("STRING => " + item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new StringItemProvider.ViewHolder(inflater.inflate(R.layout.item_string, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

}
