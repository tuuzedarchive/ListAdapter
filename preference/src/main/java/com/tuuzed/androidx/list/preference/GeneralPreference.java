package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.Preference2;

public class GeneralPreference extends Preference2<GeneralPreference> {

    public GeneralPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(GeneralPreference.class, new Preference2.ItemViewBinderFactory<GeneralPreference>() {
            @Override
            public int getLayoutRes() {
                return R.layout.preference_listitem_general;
            }
        });
    }
}
