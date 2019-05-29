package com.tuuzed.androidx.list.preference;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.adapter.CommonViewHolder;
import com.tuuzed.androidx.list.adapter.ItemViewBinder;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference;
import com.tuuzed.androidx.list.preference.internal.PreferenceHelper;

public class CategoryPreference extends Preference<CategoryPreference> {

    public CategoryPreference(@NonNull String title) {
        super(title);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(CategoryPreference.class, new DefaultItemViewBinder());
    }

    public static final class DefaultItemViewBinder extends ItemViewBinderFactory<CategoryPreference, CommonViewHolder> {
        @NonNull
        @Override
        public CommonViewHolder createViewHolder(@NonNull View itemView) {
            return new CommonViewHolder(itemView);
        }

    }

    public static abstract class ItemViewBinderFactory<P extends CategoryPreference, VH extends RecyclerView.ViewHolder>
            extends ItemViewBinder.Factory<P, VH> {
        @Override
        public int getLayoutRes() {
            return R.layout.preference_listitem_category;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, P preference, int position) {
            super.onBindViewHolder(holder, preference, position);
            setPreference(holder, preference);
        }

        public void setPreference(@NonNull VH holder, @NonNull P preference) {
            PreferenceHelper.setPreference(holder, preference);
        }

    }

}
