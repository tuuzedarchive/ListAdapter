package com.tuuzed.androidx.list.preference;

import androidx.annotation.NonNull;
import com.tuuzed.androidx.list.adapter.ListAdapter;
import com.tuuzed.androidx.list.preference.interfaces.ItemToStringFunction;
import com.tuuzed.androidx.list.preference.interfaces.ItemsLoaderFunction;
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback;

import java.util.Collections;

public class Preferences {

    private static final PreferenceCallback DEFAULT_PREFERENCE_CALLBACK = new PreferenceCallback() {
        @Override
        public boolean invoke(Object o, int position) {
            return true;
        }
    };
    private static final ItemToStringFunction DEFAULT_ITEM_TO_STRING_FUNCTION = new ItemToStringFunction() {
        @NonNull
        @Override
        public CharSequence invoke(Object option) {
            return option == null ? "" : option.toString();
        }
    };

    private static final ItemsLoaderFunction DEFAULT_ITEMS_LOADER_FUNCTION = new ItemsLoaderFunction() {
        @Override
        public void invoke(@NonNull Callback callback) {
            //noinspection unchecked
            callback.invoke(Collections.emptyList());
        }
    };

    @NonNull
    public static <T> PreferenceCallback<T> defaultPreferenceCallback() {
        //noinspection unchecked
        return DEFAULT_PREFERENCE_CALLBACK;
    }

    @NonNull
    public static <T> ItemToStringFunction<T> defaultItemToStringFunction() {
        //noinspection unchecked
        return DEFAULT_ITEM_TO_STRING_FUNCTION;
    }

    @NonNull
    public static <T> ItemsLoaderFunction<T> defaultItemsLoaderFunction() {
        //noinspection unchecked
        return DEFAULT_ITEMS_LOADER_FUNCTION;
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
