package com.tuuzed.androidx.list.adapter;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;

    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    public CommonViewHolder(@NonNull View itemView, @IntRange(from = 1) int initialCapacity) {
        super(itemView);
        views = new SparseArray<>(initialCapacity);
    }

    @Nullable
    public <T extends View> T find(@IdRes int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            if (view != null) {
                views.put(id, view);
            }
        }
        if (view != null) {
            //noinspection unchecked
            return (T) view;
        }
        return null;
    }

}
