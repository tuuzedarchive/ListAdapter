package com.tuuzed.androidx.list.preference.ktx

import com.tuuzed.androidx.list.adapter.ListAdapter
import com.tuuzed.androidx.list.preference.*

class UsePreferencesDSL(val adapter: ListAdapter)

inline fun usePreferences(listAdapter: ListAdapter, func: UsePreferencesDSL.() -> Unit) {
    func(UsePreferencesDSL(listAdapter))
}

inline fun UsePreferencesDSL.category(func: CategoryPreference.() -> Unit) =
    this.adapter.items.add(CategoryPreference("").apply(func))

inline fun UsePreferencesDSL.general(func: GeneralPreference.() -> Unit) =
    this.adapter.items.add(GeneralPreference("", "").apply(func))

inline fun UsePreferencesDSL.clickable(func: ClickablePreference.() -> Unit) =
    this.adapter.items.add(ClickablePreference("", "").apply(func))

inline fun UsePreferencesDSL.editText(func: EditTextPreference.() -> Unit) =
    this.adapter.items.add(EditTextPreference("", "").apply(func))

inline fun UsePreferencesDSL.checkBox(func: CheckboxPreference.() -> Unit) =
    this.adapter.items.add(CheckboxPreference("", "").apply(func))

inline fun UsePreferencesDSL.switch(func: SwitchPreference.() -> Unit) =
    this.adapter.items.add(SwitchPreference("", "").apply(func))

inline fun UsePreferencesDSL.radio(func: RadioPreference.() -> Unit) =
    this.adapter.items.add(RadioPreference("", "").apply(func))

inline fun <T> UsePreferencesDSL.singleChoiceItems(func: SingleChoiceItemsPreference<T>.() -> Unit) =
    this.adapter.items.add(SingleChoiceItemsPreference<T>("", "").apply(func))

inline fun <T> UsePreferencesDSL.multiChoiceItems(func: MultiChoiceItemsPreference<T>.() -> Unit) =
    this.adapter.items.add(MultiChoiceItemsPreference<T>("", "").apply(func))

