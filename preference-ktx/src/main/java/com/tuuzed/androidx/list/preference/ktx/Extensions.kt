package com.tuuzed.androidx.list.preference.ktx

import com.tuuzed.androidx.list.adapter.ListAdapter
import com.tuuzed.androidx.list.preference.*


class UsePreferencesScope(val adapter: ListAdapter)

inline fun usePreferences(listAdapter: ListAdapter, func: UsePreferencesScope.() -> Unit) {
    func(UsePreferencesScope(listAdapter))
}

inline fun UsePreferencesScope.category(func: CategoryPreference.() -> Unit) =
    this.adapter.items.add(CategoryPreference("").apply(func))

inline fun UsePreferencesScope.general(func: GeneralPreference.() -> Unit) =
    this.adapter.items.add(GeneralPreference("", "").apply(func))

inline fun UsePreferencesScope.clickable(func: ClickablePreference.() -> Unit) =
    this.adapter.items.add(ClickablePreference("", "").apply(func))

inline fun UsePreferencesScope.editText(func: EditTextPreference.() -> Unit) =
    this.adapter.items.add(EditTextPreference("", "").apply(func))

inline fun UsePreferencesScope.checkBox(func: CheckboxPreference.() -> Unit) =
    this.adapter.items.add(CheckboxPreference("", "").apply(func))

inline fun UsePreferencesScope.switch(func: SwitchPreference.() -> Unit) =
    this.adapter.items.add(SwitchPreference("", "").apply(func))

inline fun UsePreferencesScope.radio(func: RadioPreference.() -> Unit) =
    this.adapter.items.add(RadioPreference("", "").apply(func))

inline fun <T> UsePreferencesScope.singleChoiceItems(func: SingleChoiceItemsPreference<T>.() -> Unit) =
    this.adapter.items.add(SingleChoiceItemsPreference<T>("", "").apply(func))

inline fun <T> UsePreferencesScope.multiChoiceItems(func: MultiChoiceItemsPreference<T>.() -> Unit) =
    this.adapter.items.add(MultiChoiceItemsPreference<T>("", "").apply(func))

