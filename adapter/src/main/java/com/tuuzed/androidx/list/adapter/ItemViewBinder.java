package com.tuuzed.androidx.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface ItemViewBinder<T, VH extends RecyclerView.ViewHolder> {

    @NonNull
    VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull VH holder, T t, int position);

    abstract class Factory<T, VH extends RecyclerView.ViewHolder> implements ItemViewBinder<T, VH> {

        @LayoutRes
        private final int layoutRes;

        public Factory(int layoutRes) {
            this.layoutRes = layoutRes;
        }

        @NonNull
        public abstract VH createViewHolder(@NonNull View itemView);

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
            return createViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, T t, int position) {
        }

    }

}