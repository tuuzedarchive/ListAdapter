package com.tuuzed.androidx.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

data class PrefGeneralItem(
        var title: String = "",
        var summary: String = "",
        var callback: PrefItemCallback<PrefGeneralItem>? = null
)

class PrefGeneralItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_general
) : AbstractItemViewBinder<PrefGeneralItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefGeneralItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.itemLayout, null)
        item.callback?.let { click ->
            holder.click(R.id.itemLayout) { click(item, position) }
        }
    }
}