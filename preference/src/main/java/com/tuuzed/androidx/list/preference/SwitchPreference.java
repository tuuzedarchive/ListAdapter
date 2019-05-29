package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.CompoundButtonPreference2;

public class SwitchPreference extends CompoundButtonPreference2<SwitchPreference> {

    public SwitchPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(
                SwitchPreference.class,
                new CompoundButtonPreference2.ItemViewBinderFactory<SwitchPreference>() {
                    @Override
                    public int getLayoutRes() {
                        return R.layout.preference_listitem_switch;
                    }
                }
        );
    }

}
