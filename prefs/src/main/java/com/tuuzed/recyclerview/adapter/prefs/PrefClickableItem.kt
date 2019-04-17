package com.tuuzed.recyclerview.adapter.prefs

import android.view.View
import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

typealias PrefClickableItemClick = PrefItemCallback<PrefClickableItem>

open class PrefClickableItem(
        var title: String = "",
        var summary: String = "",
        var click: PrefClickableItemClick = { _, _ -> true }
)

open class PrefClickableItemViewBinder<in T : PrefClickableItem>(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_clickable
) : AbstractItemViewBinder<T>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: T, position: Int) {
        item.click(item, position)
    }
}