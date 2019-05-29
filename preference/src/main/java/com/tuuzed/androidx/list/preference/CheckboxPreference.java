package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.CompoundButtonPreference2;

public class CheckboxPreference extends CompoundButtonPreference2<CheckboxPreference> {

    public CheckboxPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(
                CheckboxPreference.class,
                new CompoundButtonPreference2.ItemViewBinderFactory<CheckboxPreference>() {
                    @Override
                    public int getLayoutRes() {
                        return R.layout.preference_listitem_checkbox;
                    }
                }
        );
    }

}
