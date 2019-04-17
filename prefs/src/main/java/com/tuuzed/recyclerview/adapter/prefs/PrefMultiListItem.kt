package com.tuuzed.recyclerview.adapter.prefs

import android.view.View
import androidx.annotation.LayoutRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
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

open class PrefMultiListItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_multilist
) : AbstractItemViewBinder<PrefMultiListItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefMultiListItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: PrefMultiListItem, position: Int) {
        item.itemsLoader { items ->
            val initialSelection = ArrayList<Int>()
            items.forEachIndexed { index, any ->
                if (item.checkedItems.contains(any)) {
                    initialSelection.add(index)
                }
            }
            MaterialDialog(view.context).show {
                title(text = item.title)
                noAutoDismiss()
                listItemsMultiChoice(
                        items = items.map { item.itemToString(it) },
                        allowEmptySelection = item.allowEmptySelection,
                        initialSelection = initialSelection.toIntArray(),
                        selection = { _, indices, _ ->
                            val oldCheckedItems = item.checkedItems
                            val oldSummary = item.summary
                            item.checkedItems = items.filterIndexed { index, _ -> indices.contains(index) }
                            item.summary = item.checkedItems.joinToString(item.itemSeparator) { item.itemToString(it) }
                            if (item.callback(item, position)) {
                                holder.text(R.id.pref_summary, item.summary)
                            } else {
                                item.checkedItems = oldCheckedItems
                                item.summary = oldSummary
                            }
                        }
                )
                positiveButton { dismiss() }
                negativeButton { dismiss() }
            }

        }
    }


}
