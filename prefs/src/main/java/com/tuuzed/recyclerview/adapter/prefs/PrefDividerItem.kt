package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

open class PrefDividerItem

open class PrefDividerItemViewBinder<in T : PrefDividerItem>(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_divider
) : AbstractItemViewBinder<T>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {}

}