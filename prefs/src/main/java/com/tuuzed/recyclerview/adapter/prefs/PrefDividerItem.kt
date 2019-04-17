package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

object PrefDividerItem

open class PrefDividerItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_divider
) : AbstractItemViewBinder<PrefDividerItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefDividerItem, position: Int) {}
}