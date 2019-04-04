package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.RecyclerViewAdapter

typealias PrefItemCallback<T> = (item: T, position: Int) -> Boolean

typealias ItemsCallback = (items: List<Any>) -> Unit
typealias ItemsLoader = (callback: ItemsCallback) -> Unit
typealias ItemToString = (item: Any) -> String

fun RecyclerViewAdapter.withPrefs(
        @LayoutRes categoryLayoutId: Int = R.layout.pref_listitem_category,
        @LayoutRes clickableLayoutId: Int = R.layout.pref_listitem_clickable,
        @LayoutRes generalLayoutId: Int = R.layout.pref_listitem_general,
        @LayoutRes editTextLayoutId: Int = R.layout.pref_listitem_edittext,
        @LayoutRes checkboxLayoutId: Int = R.layout.pref_listitem_checkbox,

        @LayoutRes switchLayoutId: Int = R.layout.pref_listitem_switch,
        @LayoutRes radioLayoutId: Int = R.layout.pref_listitem_radio,
        @LayoutRes singleListLayoutId: Int = R.layout.pref_listitem_singlelist,
        @LayoutRes multiListLayoutId: Int = R.layout.pref_listitem_multilist,
        @LayoutRes dividerLayoutId: Int = R.layout.pref_listitem_divider
): RecyclerViewAdapter {
    return this.apply {
        bind(PrefCategoryItem::class.java, PrefCategoryItemViewBinder(categoryLayoutId))
        bind(PrefClickableItem::class.java, PrefClickableItemViewBinder(clickableLayoutId))
        bind(PrefGeneralItem::class.java, PrefGeneralItemViewBinder(generalLayoutId))
        bind(PrefEditTextItem::class.java, PrefEditTextItemViewBinder(editTextLayoutId))
        bind(PrefCheckBoxItem::class.java, PrefCheckBoxItemViewBinder(checkboxLayoutId))

        bind(PrefSwitchItem::class.java, PrefSwitchItemViewBinder(switchLayoutId))
        bind(PrefRadioItem::class.java, PrefRadioItemViewBinder(radioLayoutId))
        bind(PrefSingleListItem::class.java, PrefSingleListItemViewBinder(singleListLayoutId))
        bind(PrefMultiListItem::class.java, PrefMultiListItemViewBinder(multiListLayoutId))
        bind(PrefDividerItem::class.java, PrefDividerItemViewBinder(dividerLayoutId))
    }
}




