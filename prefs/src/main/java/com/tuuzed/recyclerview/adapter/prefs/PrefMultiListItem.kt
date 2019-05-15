package com.tuuzed.recyclerview.adapter.prefs

import android.view.View
import androidx.annotation.LayoutRes
import com.tuuzed.androidx.exdialog.ExDialog
import com.tuuzed.androidx.exdialog.ext.multiChoiceItems
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder
import java.util.*

open class PrefMultiListItem(
        var title: String = "",
        var summary: String = "",
        var itemsLoader: ItemsLoader = { it(emptyList()) },
        var itemToString: ItemToString = { it.toString() },
        var itemSeparator: String = "\r\n",
        var allowEmptySelection: Boolean = false,
        var checkedItems: List<Any> = Collections.emptyList(),
        var callback: PrefItemCallback<PrefMultiListItem> = { _, _ -> true }
)

open class PrefMultiListItemViewBinder<in T : PrefMultiListItem>(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_multilist
) : AbstractItemViewBinder<T>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: T, position: Int) {
        item.itemsLoader { items ->
            val initialSelection = ArrayList<Int>()
            items.forEachIndexed { index, any ->
                if (item.checkedItems.contains(any)) {
                    initialSelection.add(index)
                }
            }
            ExDialog(view.context).show {
                multiChoiceItems<Any> {
                    title(text = item.title)
                    items(items, initialSelection)
                    onSelectedItemChanged { _, _, selectedItems ->
                        if (!item.allowEmptySelection) {
                            positiveButtonEnable(selectedItems.isNotEmpty())
                        }
                    }
                    callback { _, _, selectedItems ->
                        val oldCheckedItems = item.checkedItems
                        val oldSummary = item.summary
                        item.checkedItems = selectedItems
                        item.summary = item.checkedItems.joinToString(item.itemSeparator) { item.itemToString(it) }
                        if (item.callback(item, position)) {
                            holder.text(R.id.pref_summary, item.summary)
                        } else {
                            item.checkedItems = oldCheckedItems
                            item.summary = oldSummary
                        }
                    }
                    negativeButton()
                    positiveButton()
                }
            }
        }
    }


}
