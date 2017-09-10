package com.tuuzed.adapter;


import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 通用ViewHolder
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    /**
     * 缓存View
     */
    private SparseArray<View> views = new SparseArray<>();

    public ViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T find(@IdRes int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T find(@IdRes int id, @NonNull Class<T> clazz) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }
}
