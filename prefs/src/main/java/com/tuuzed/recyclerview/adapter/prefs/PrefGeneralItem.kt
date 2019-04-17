package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

open class PrefGeneralItem(
        var title: String = "",
        var summary: String = ""
)

open class PrefGeneralItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_general
) : AbstractItemViewBinder<PrefGeneralItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefGeneralItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
    }
}