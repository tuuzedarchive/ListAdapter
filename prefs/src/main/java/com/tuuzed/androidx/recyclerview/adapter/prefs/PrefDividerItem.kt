package com.tuuzed.androidx.recyclerview.adapter.prefs

import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

object PrefDividerItem

class PrefDividerItemViewBinder : AbstractItemViewBinder<PrefDividerItem>() {
    override fun getLayoutId() = R.layout.pref_listitem_divider

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefDividerItem, position: Int) {
    }
}