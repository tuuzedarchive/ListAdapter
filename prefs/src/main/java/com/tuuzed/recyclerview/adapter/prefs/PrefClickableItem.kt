package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

typealias PrefClickableItemClick = PrefItemCallback<PrefClickableItem>

data class PrefClickableItem(
        var title: String = "",
        var summary: String = "",
        var click: PrefClickableItemClick = { _, _ -> true }
)

class PrefClickableItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_clickable
) : AbstractItemViewBinder<PrefClickableItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefClickableItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        item.click.let { click ->
            holder.click(R.id.pref_item_layout) { click(item, position) }
        }
    }
}