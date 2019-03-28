package com.tuuzed.androidx.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter

typealias PrefItemCallback<T> = (item: T, position: Int) -> Boolean

typealias ItemsCallback = (items: List<Any>) -> Unit
typealias ItemsLoader = (callback: ItemsCallback) -> Unit
typealias ItemToString = (item: Any) -> String

@JvmOverloads
fun RecyclerViewAdapter.withPrefs(
        @LayoutRes categoryLayoutId: Int = R.layout.pref_listitem_category,
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

class PrefDSL(private val adapter: RecyclerViewAdapter) {
    fun prefCategory(func: PrefCategoryItem.() -> Unit) = adapter.appendItems(PrefCategoryItem().apply(func))
    fun prefGeneral(func: PrefGeneralItem.() -> Unit) = adapter.appendItems(PrefGeneralItem().apply(func))
    fun prefEditText(func: PrefEditTextItem.() -> Unit) = adapter.appendItems(PrefEditTextItem().apply(func))
    fun prefCheckBox(func: PrefCheckBoxItem.() -> Unit) = adapter.appendItems(PrefCheckBoxItem().apply(func))
    fun prefSwitch(func: PrefSwitchItem.() -> Unit) = adapter.appendItems(PrefSwitchItem().apply(func))
    fun prefRadio(func: PrefRadioItem.() -> Unit) = adapter.appendItems(PrefRadioItem().apply(func))
    fun prefSingleList(func: PrefSingleListItem.() -> Unit) = adapter.appendItems(PrefSingleListItem().apply(func))
    fun prefMultiList(func: PrefMultiListItem.() -> Unit) = adapter.appendItems(PrefMultiListItem().apply(func))
    fun prefDivider(func: PrefDividerItem.() -> Unit) = adapter.appendItems(PrefDividerItem.apply(func))
}

fun RecyclerViewAdapter.prefDsl(block: PrefDSL.() -> Unit): RecyclerViewAdapter {
    block(PrefDSL((this)))
    return this
}


