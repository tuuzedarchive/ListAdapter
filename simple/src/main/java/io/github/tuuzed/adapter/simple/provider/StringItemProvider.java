package io.github.tuuzed.adapter.simple.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.tuuzed.adapter.ItemProvider;
import io.github.tuuzed.adapter.simple.R;


public class StringItemProvider extends ItemProvider<String, StringItemProvider.ViewHolder> {


    @Override
    public void onBindViewHolder(ViewHolder holder, String item, int position) {
        holder.textView.setText("String => " + item);
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
