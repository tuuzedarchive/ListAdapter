package com.tuuzed.androidx.recyclerview.adapter.prefs

import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

data class PrefGeneralItem(
        var title: String = "",
        var summary: String = "",
        var callback: PrefItemCallback<PrefGeneralItem>? = null
)

class PrefGeneralItemViewBinder : AbstractItemViewBinder<PrefGeneralItem>() {
    override fun getLayoutId() = R.layout.pref_listitem_general

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefGeneralItem, position: Int) {
        holder.text(R.id.tv_Title, item.title)
        holder.text(R.id.tv_Summary, item.summary)
        holder.click(R.id.itemLayout, null)
        item.callback?.let { click ->
            holder.click(R.id.itemLayout) { click(item, position) }
        }
    }
}