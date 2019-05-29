package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.internal.CompoundButtonPreference;

public class SwitchPreference extends CompoundButtonPreference<SwitchPreference> {

    public SwitchPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }


    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(
                SwitchPreference.class,
                new CompoundButtonPreference.ViewBinder<SwitchPreference>(R.layout.preference_listitem_switch)
        );
    }

}
