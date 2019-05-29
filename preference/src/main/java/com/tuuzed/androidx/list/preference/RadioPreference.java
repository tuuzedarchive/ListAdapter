package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.CompoundButtonPreference;

public class RadioPreference extends CompoundButtonPreference<RadioPreference> {

    public RadioPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(
                RadioPreference.class,
                new CompoundButtonPreference.ViewBinder<RadioPreference>(R.layout.preference_listitem_radio)
        );
    }

}
