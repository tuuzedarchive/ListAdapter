package com.tuuzed.recyclerview.adapter.prefs.dsl

import com.tuuzed.recyclerview.adapter.RecyclerViewAdapter
import com.tuuzed.recyclerview.adapter.prefs.*

inline fun RecyclerViewAdapter.prefDsl(func: RecyclerViewAdapter.() -> Unit) = this.apply { func(this) }

inline fun RecyclerViewAdapter.prefCategory(func: PrefCategoryItem.() -> Unit) = this.appendItems(PrefCategoryItem().apply(func))
inline fun RecyclerViewAdapter.prefGeneral(func: PrefGeneralItem.() -> Unit) = this.appendItems(PrefGeneralItem().apply(func))
inline fun RecyclerViewAdapter.prefClickable(func: PrefClickableItem.() -> Unit) = this.appendItems(PrefClickableItem().apply(func))
inline fun RecyclerViewAdapter.prefEditText(func: PrefEditTextItem.() -> Unit) = this.appendItems(PrefEditTextItem().apply(func))
inline fun RecyclerViewAdapter.prefCheckBox(func: PrefCheckBoxItem.() -> Unit) = this.appendItems(PrefCheckBoxItem().apply(func))
inline fun RecyclerViewAdapter.prefSwitch(func: PrefSwitchItem.() -> Unit) = this.appendItems(PrefSwitchItem().apply(func))
inline fun RecyclerViewAdapter.prefRadio(func: PrefRadioItem.() -> Unit) = this.appendItems(PrefRadioItem().apply(func))
inline fun RecyclerViewAdapter.prefSingleList(func: PrefSingleListItem.() -> Unit) = this.appendItems(PrefSingleListItem().apply(func))
inline fun RecyclerViewAdapter.prefMultiList(func: PrefMultiListItem.() -> Unit) = this.appendItems(PrefMultiListItem().apply(func))
inline fun RecyclerViewAdapter.prefDivider(func: PrefDividerItem.() -> Unit) = this.appendItems(PrefDividerItem.apply(func))


class PrefDSL(val adapter: RecyclerViewAdapter)

inline fun RecyclerViewAdapter.dslPref(func: PrefDSL.() -> Unit) = this.apply { func(PrefDSL(this)) }

inline fun PrefDSL.category(func: PrefCategoryItem.() -> Unit) = this.adapter.appendItems(PrefCategoryItem().apply(func))
inline fun PrefDSL.general(func: PrefGeneralItem.() -> Unit) = this.adapter.appendItems(PrefGeneralItem().apply(func))
inline fun PrefDSL.clickable(func: PrefClickableItem.() -> Unit) = this.adapter.appendItems(PrefClickableItem().apply(func))
inline fun PrefDSL.editText(func: PrefEditTextItem.() -> Unit) = this.adapter.appendItems(PrefEditTextItem().apply(func))
inline fun PrefDSL.checkBox(func: PrefCheckBoxItem.() -> Unit) = this.adapter.appendItems(PrefCheckBoxItem().apply(func))
inline fun PrefDSL.switch(func: PrefSwitchItem.() -> Unit) = this.adapter.appendItems(PrefSwitchItem().apply(func))
inline fun PrefDSL.radio(func: PrefRadioItem.() -> Unit) = this.adapter.appendItems(PrefRadioItem().apply(func))
inline fun PrefDSL.singleList(func: PrefSingleListItem.() -> Unit) = this.adapter.appendItems(PrefSingleListItem().apply(func))
inline fun PrefDSL.multiList(func: PrefMultiListItem.() -> Unit) = this.adapter.appendItems(PrefMultiListItem().apply(func))
inline fun PrefDSL.divider(func: PrefDividerItem.() -> Unit) = this.adapter.appendItems(PrefDividerItem.apply(func))
