package com.tuuzed.adapter;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用ViewHolder
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    /**
     * 缓存View
     */
    private SparseArray<View> views = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void text(@IdRes int id, @NonNull CharSequence text) {
        find(id, TextView.class).setText(text);
    }

    public void text(@IdRes int id, @NonNull CharSequence text, TextView.BufferType type) {
        find(id, TextView.class).setText(text, type);
    }

    public void text(@IdRes int id, char[] text, int start, int len) {
        find(id, TextView.class).setText(text, start, len);
    }

    public void text(@IdRes int id, @StringRes int resId) {
        find(id, TextView.class).setText(resId);
    }

    public void text(@IdRes int id, @StringRes int resId, TextView.BufferType type) {
        find(id, TextView.class).setText(resId, type);
    }

    public void image(@IdRes int id, @DrawableRes int resId) {
        find(id, ImageView.class).setImageResource(resId);
    }

    public void image(@IdRes int id, @NonNull Drawable drawable) {
        find(id, ImageView.class).setImageDrawable(drawable);
    }

    public void image(@IdRes int id, @NonNull Bitmap bitmap) {
        find(id, ImageView.class).setImageBitmap(bitmap);
    }

    public <T extends View> T find(@IdRes int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        //noinspection unchecked
        return (T) view;
    }

    public <T extends View> T find(@IdRes int id, @NonNull Class<T> clazz) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        //noinspection unchecked
        return (T) view;
    }
}
