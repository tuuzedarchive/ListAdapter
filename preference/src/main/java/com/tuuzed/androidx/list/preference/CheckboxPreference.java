package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.CompoundButtonPreference;

public class CheckboxPreference extends CompoundButtonPreference<CheckboxPreference> {

    public CheckboxPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(
                CheckboxPreference.class,
                new ViewBinder<CheckboxPreference>() {
                    @Override
                    public int getLayoutRes() {
                        return R.layout.preference_listitem_checkbox;
                    }
                }
        );
    }

}
