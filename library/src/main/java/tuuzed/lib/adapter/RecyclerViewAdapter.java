package tuuzed.lib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LYH on 2017/2/22.
 *
 * @author LYH
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Object> mItems;
    private Map<Class<?>, ItemConverter> mItemConverterPools;

    public RecyclerViewAdapter() {
        this(new ArrayList<>());
    }

    public RecyclerViewAdapter(@NonNull List<Object> items) {
        this.mItems = items;
        this.mItemConverterPools = new HashMap<>();
    }

    public <T> RecyclerViewAdapter register(@NonNull Class<T> type,
                                            @NonNull ItemConverter<T> converter) {
        converter.setAdapter(this);
        mItemConverterPools.put(type, converter);
        return this;
    }

    public RecyclerViewAdapter attach(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(this);
        return this;
    }


    public List<Object> items() {
        return mItems;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Class<?> clazz = mItems.get(viewType).getClass();
        ItemConverter converter = mItemConverterPools.get(clazz);
        if (converter == null) {
            throw new UnregisteredItemConverterException(clazz);
        }
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(converter.getLayoutId(), parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Object item = mItems.get(position);
        Class<?> clazz = item.getClass();
        ItemConverter converter = mItemConverterPools.get(clazz);
        if (converter == null) {
            throw new UnregisteredItemConverterException(clazz);
        } else {
            //noinspection unchecked
            converter.onConvert(holder, item, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews = new SparseArray<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public Context getContext() {
            return itemView.getContext();
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
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            //noinspection unchecked
            return (T) view;
        }

        public <T extends View> T find(@IdRes int id, @NonNull Class<T> clazz) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            //noinspection unchecked
            return (T) view;
        }
    }
}
