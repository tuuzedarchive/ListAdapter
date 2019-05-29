package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference;

public class CategoryPreference extends Preference<CategoryPreference> {

    public CategoryPreference(@NonNull String title) {
        super(title);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(CategoryPreference.class, new Preference.ItemViewBinderFactory<CategoryPreference>() {
            @Override
            public int getLayoutRes() {
                return R.layout.preference_listitem_category;
            }
        });
    }

}
