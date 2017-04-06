package io.github.tuuzed.adapter.simple.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.tuuzed.adapter.ItemProvider;
import io.github.tuuzed.adapter.simple.R;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class LongItemProvider extends ItemProvider<Long, LongItemProvider.ViewHolder> {

    @Override
    public void onBindViewHolder(ViewHolder holder, Long item, int position) {
        holder.textView.setText("Long => " + item);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new LongItemProvider.ViewHolder(inflater.inflate(R.layout.item_long, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
