package com.tuuzed.androidx.recyclerview.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

public class CommonItemViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final SparseArray<View> mViewHolder;

    public CommonItemViewHolder(View itemView) {
        super(itemView);
        mViewHolder = new SparseArray<>();
    }

    public CommonItemViewHolder(View itemView, int initialCapacity) {
        super(itemView);
        mViewHolder = new SparseArray<>(initialCapacity);
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }

    @NonNull
    public CommonItemViewHolder visibility(@IdRes int id, @Visibility int visibility) {
        find(id, View.class).setVisibility(visibility);
        return this;
    }

    @NonNull
    public CommonItemViewHolder text(@IdRes int id, @NonNull CharSequence text) {
        find(id, TextView.class).setText(text);
        return this;
    }

    @NonNull
    public CommonItemViewHolder text(@IdRes int id, @NonNull CharSequence text, TextView.BufferType type) {
        find(id, TextView.class).setText(text, type);
        return this;
    }

    @NonNull
    public CommonItemViewHolder text(@IdRes int id, char[] text, int start, int len) {
        find(id, TextView.class).setText(text, start, len);
        return this;
    }

    @NonNull
    public CommonItemViewHolder text(@IdRes int id, @StringRes int resId) {
        find(id, TextView.class).setText(resId);
        return this;
    }

    @NonNull
    public CommonItemViewHolder text(@IdRes int id, @StringRes int resId, TextView.BufferType type) {
        find(id, TextView.class).setText(resId, type);
        return this;
    }

    @NonNull
    public CommonItemViewHolder image(@IdRes int id, @DrawableRes int resId) {
        find(id, ImageView.class).setImageResource(resId);
        return this;
    }

    @NonNull
    public CommonItemViewHolder image(@IdRes int id, @NonNull Drawable drawable) {
        find(id, ImageView.class).setImageDrawable(drawable);
        return this;
    }

    @NonNull
    public CommonItemViewHolder image(@IdRes int id, @NonNull Bitmap bitmap) {
        find(id, ImageView.class).setImageBitmap(bitmap);
        return this;
    }

    @NonNull
    public CommonItemViewHolder click(@IdRes int id, View.OnClickListener listener) {
        find(id).setOnClickListener(listener);
        return this;
    }


    @NonNull
    public CommonItemViewHolder longClick(@IdRes int id, View.OnLongClickListener listener) {
        find(id).setOnLongClickListener(listener);
        return this;
    }

    public <T extends View> T find(@IdRes int id) {
        View view = mViewHolder.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            if (view == null) throw new IllegalArgumentException("find# id not find");
            mViewHolder.put(id, view);
        }
        //noinspection unchecked
        return (T) view;
    }

    public <T extends View> T find(@IdRes int id, @NonNull Class<T> type) {
        return find(id);
    }
}