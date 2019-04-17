package com.tuuzed.recyclerview.adapter.prefs

import android.view.View
import android.widget.CompoundButton
import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder


open class PrefCheckBoxItem(
        var title: String = "",
        var summary: String = "",
        var checked: Boolean = false,
        var callback: PrefItemCallback<PrefCheckBoxItem> = { _, _ -> true }
)

open class PrefCheckBoxItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_checkbox
) : AbstractItemViewBinder<PrefCheckBoxItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefCheckBoxItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.find<CompoundButton>(R.id.pref_checkbox_widget).isChecked = item.checked
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: PrefCheckBoxItem, position: Int) {
        val oldChecked = item.checked
        item.checked = !item.checked
        if (item.callback(item, position)) {
            holder.find<CompoundButton>(R.id.pref_checkbox_widget).isChecked = item.checked
        } else {
            item.checked = oldChecked
        }
    }
}
