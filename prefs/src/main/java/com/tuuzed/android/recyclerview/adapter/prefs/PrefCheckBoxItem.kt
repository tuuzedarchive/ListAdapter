package com.tuuzed.android.recyclerview.adapter.prefs

import android.widget.CompoundButton
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder


data class PrefCheckBoxItem(
        var title: String = "",
        var summary: String = "",
        var checked: Boolean = false,
        var callback: PrefItemCallback<PrefCheckBoxItem> = { _, _ -> true }
)

class PrefCheckBoxItemViewBinder : AbstractItemViewBinder<PrefCheckBoxItem>() {
    override fun getLayoutId() = R.layout.pref_listitem_checkbox

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefCheckBoxItem, position: Int) {
        holder.text(R.id.tv_Title, item.title)
        holder.text(R.id.tv_Summary, item.summary)
        holder.find<CompoundButton>(R.id.checkbox).isChecked = item.checked
        holder.click(R.id.itemLayout) {
            val oldChecked = item.checked
            item.checked = !item.checked
            if (item.callback(item, position)) {
                holder.find<CompoundButton>(R.id.checkbox).isChecked = item.checked
            } else {
                item.checked = oldChecked
            }
        }
    }
}
