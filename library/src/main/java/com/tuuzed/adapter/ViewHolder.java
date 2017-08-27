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
    private SparseArray<View> views = new SparseArray<>();

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public View find(@IdRes int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return view;
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

    @Deprecated
    public View $(@IdRes int id) {
        return find(id);
    }

    @Deprecated
    public <T extends View> T $(@IdRes int id, @NonNull Class<T> clazz) {
        return find(id, clazz);
    }
}
