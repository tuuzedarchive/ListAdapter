package tuuzed.lib.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

public class EmptyItemConverter<T> extends ItemConverter<T> {

    public EmptyItemConverter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    public void onConvert(@NonNull RecyclerViewAdapter.ViewHolder holder, T item, int position) {

    }
}
