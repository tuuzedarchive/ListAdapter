package com.tuuzed.androidx.recyclerview.adapter.prefs.dsl

import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter
import com.tuuzed.androidx.recyclerview.adapter.prefs.*

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

