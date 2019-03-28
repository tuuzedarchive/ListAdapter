package com.tuuzed.androidx.recyclerview.adapter.prefs

import android.widget.CompoundButton
import androidx.annotation.LayoutRes
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

data class PrefSwitchItem(
        var title: String = "",
        var summary: String = "",
        var checked: Boolean = false,
        var callback: PrefItemCallback<PrefSwitchItem> = { _, _ -> true }
)

class PrefSwitchItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_switch
) : AbstractItemViewBinder<PrefSwitchItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefSwitchItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.find<CompoundButton>(R.id.pref_switch_widget).isChecked = item.checked
        holder.click(R.id.itemLayout) {
            val oldChecked = item.checked
            item.checked = !item.checked
            if (item.callback(item, position)) {
                holder.find<CompoundButton>(R.id.pref_switch_widget).isChecked = item.checked
            } else {
                item.checked = oldChecked
            }
        }
    }
}
