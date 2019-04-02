package com.tuuzed.androidx.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

typealias PrefClickableItemClick = PrefItemCallback<PrefClickableItem>

data class PrefClickableItem(
        var title: String = "",
        var summary: String = "",
        var click: PrefClickableItemClick? = null
)

class PrefClickableItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_clickable
) : AbstractItemViewBinder<PrefClickableItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefClickableItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.itemLayout, null)
        item.click?.let { click ->
            holder.click(R.id.itemLayout) { click(item, position) }
        }
    }
}