package com.tuuzed.recyclerview.adapter.prefs

import android.widget.CompoundButton
import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

data class PrefRadioItem(
        var title: String = "",
        var summary: String = "",
        var checked: Boolean = false,
        var callback: PrefItemCallback<PrefRadioItem> = { _, _ -> true }
)

open class PrefRadioItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_radio
) : AbstractItemViewBinder<PrefRadioItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefRadioItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.find<CompoundButton>(R.id.pref_radio_widget).isChecked = item.checked
        holder.click(R.id.pref_item_layout) {
            val oldChecked = item.checked
            item.checked = !item.checked
            if (item.callback(item, position)) {
                holder.find<CompoundButton>(R.id.pref_radio_widget).isChecked = item.checked
            } else {
                item.checked = oldChecked
            }
        }
    }
}
