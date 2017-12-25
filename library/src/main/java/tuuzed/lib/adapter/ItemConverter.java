package tuuzed.lib.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public abstract class ItemConverter<T> {
    private RecyclerViewAdapter mAdapter;
    private int mLayoutId;

    public ItemConverter(@LayoutRes int layoutId) {
        this.mLayoutId = layoutId;
    }

    int getLayoutId() {
        return mLayoutId;
    }

    void setAdapter(@NonNull RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    public RecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    public abstract void onConvert(@NonNull RecyclerViewAdapter.ViewHolder holder, T item, int position);
}
