package com.tuuzed.androidx.recyclerview.adapter.prefs

import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter

typealias PrefItemCallback<T> = (item: T, position: Int) -> Boolean
typealias ValuesLoader = ((List<Any>) -> Unit) -> Unit
typealias DisplaysLoader = ((List<String>) -> Unit) -> Unit

fun RecyclerViewAdapter.withPrefs(): RecyclerViewAdapter {
    return this.apply {
        bind(PrefCategoryItem::class.java, PrefCategoryItemViewBinder())
        bind(PrefGeneralItem::class.java, PrefGeneralItemViewBinder())
        bind(PrefEditTextItem::class.java, PrefEditTextItemViewBinder())
        bind(PrefCheckBoxItem::class.java, PrefCheckBoxItemViewBinder())

        bind(PrefSwitchItem::class.java, PrefSwitchItemViewBinder())
        bind(PrefRadioItem::class.java, PrefRadioItemViewBinder())
        bind(PrefSingleListItem::class.java, PrefSingleListItemViewBinder())
        bind(PrefMultiListItem::class.java, PrefMultiListItemViewBinder())

        bind(PrefDividerItem::class.java, PrefDividerItemViewBinder())
    }
}


inline fun prefCategory(func: PrefCategoryItem.() -> Unit) = PrefCategoryItem().apply(func)
inline fun prefGeneral(func: PrefGeneralItem.() -> Unit) = PrefGeneralItem().apply(func)
inline fun prefEditText(func: PrefEditTextItem.() -> Unit) = PrefEditTextItem().apply(func)
inline fun prefCheckBox(func: PrefCheckBoxItem.() -> Unit) = PrefCheckBoxItem().apply(func)

inline fun prefSwitch(func: PrefSwitchItem.() -> Unit) = PrefSwitchItem().apply(func)
inline fun prefRadio(func: PrefRadioItem.() -> Unit) = PrefRadioItem().apply(func)
inline fun prefSingleList(func: PrefSingleListItem.() -> Unit) = PrefSingleListItem().apply(func)
inline fun prefMultiList(func: PrefMultiListItem.() -> Unit) = PrefMultiListItem().apply(func)

fun prefDivider() = PrefDividerItem