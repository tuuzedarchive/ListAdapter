package com.tuuzed.recyclerview.adapter.prefs

import android.view.View
import androidx.annotation.LayoutRes
import com.tuuzed.androidx.exdialog.ExDialog
import com.tuuzed.androidx.exdialog.ext.singleChoiceItems
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

open class PrefSingleListItem(
        var title: String = "",
        var summary: String = "",
        var itemsLoader: ItemsLoader = { it(emptyList()) },
        var itemToString: ItemToString = { it.toString() },
        var checkedItem: Any? = null,
        var callback: PrefItemCallback<PrefSingleListItem> = { _, _ -> true }
)

open class PrefSingleListItemViewBinder<in T : PrefSingleListItem>(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_singlelist
) : AbstractItemViewBinder<T>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: T, position: Int) {
        item.itemsLoader { items ->
            ExDialog(view.context).show {
                singleChoiceItems<Any>(toReadable = { item.itemToString(it) }) {
                    title(text = item.title)
                    items(items, items.indexOf(item.checkedItem))
                    onSelectedItemChanged { dialog, _, selectedItem ->
                        val oldCheckedItem = item.checkedItem
                        val oldSummary = item.summary
                        item.checkedItem = selectedItem
                        item.summary = item.itemToString(item.checkedItem ?: "")
                        if (item.callback(item, position)) {
                            holder.text(R.id.pref_summary, item.summary)
                        } else {
                            item.checkedItem = oldCheckedItem
                            item.summary = oldSummary
                        }
                        dialog.dismiss()
                    }
                }
            }
        }
    }

}
