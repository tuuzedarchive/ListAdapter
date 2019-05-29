package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.base.CompoundButtonPreference2;

public class RadioPreference extends CompoundButtonPreference2<RadioPreference> {

    public RadioPreference(@NonNull String title, @NonNull String summary) {
        super(title, summary);
    }

    public static void bindTo(@NonNull ListAdapter listAdapter) {
        listAdapter.bind(
                RadioPreference.class,
                new CompoundButtonPreference2.ItemViewBinderFactory<RadioPreference>() {
                    @Override
                    public int getLayoutRes() {
                        return R.layout.preference_listitem_radio;
                    }
                }
        );
    }

}
