package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;

public class Preferences {

    private static final PreferenceCallback DEFAULT_PREFERENCE_CALLBACK = new PreferenceCallback() {
        @Override
        public boolean invoke(Object o, int position) {
            return true;
        }
    };
    private static final OptionToStringFunction DEFAULT_OPTION_TO_STRING_FUNCTION = new OptionToStringFunction() {
        @NonNull
        @Override
        public CharSequence invoke(Object option) {
            return option == null ? "" : option.toString();
        }
    };

    @NonNull
    public static <T> PreferenceCallback<T> defaultPreferenceCallback() {
        //noinspection unchecked
        return DEFAULT_PREFERENCE_CALLBACK;
    }

    @NonNull
    public static <T> OptionToStringFunction<T> defaultOptionToStringFunction() {
        //noinspection unchecked
        return DEFAULT_OPTION_TO_STRING_FUNCTION;
    }

    public static void bindAllTo(@NonNull ListAdapter listAdapter) {
        CategoryPreference.bindTo(listAdapter);
        CheckboxPreference.bindTo(listAdapter);
        ClickablePreference.bindTo(listAdapter);
        EditTextPreference.bindTo(listAdapter);
        GeneralPreference.bindTo(listAdapter);
        MultiChoiceItemsPreference.bindTo(listAdapter);
        RadioPreference.bindTo(listAdapter);
        SwitchPreference.bindTo(listAdapter);
        SingleChoiceItemsPreference.bindTo(listAdapter);
    }

}
