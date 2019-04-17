package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

data class PrefSingleListItem(
        var title: String = "",
        var summary: String = "",
        var itemsLoader: ItemsLoader = { it(emptyList()) },
        var itemToString: ItemToString = { it.toString() },
        var checkedItem: Any? = null,
        var callback: PrefItemCallback<PrefSingleListItem> = { _, _ -> true }
)

open class PrefSingleListItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_singlelist
) : AbstractItemViewBinder<PrefSingleListItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefSingleListItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { v ->
            item.itemsLoader { items ->
                MaterialDialog(v.context).show {
                    title(text = item.title)
                    listItemsSingleChoice(
                            items = items.map { item.itemToString(it) },
                            initialSelection = items.indexOf(item.checkedItem),
                            selection = { _, index, text ->
                                val oldCheckedItem = item.checkedItem
                                val oldSummary = item.summary
                                item.checkedItem = items[index]
                                item.summary = text
                                if (item.callback(item, position)) {
                                    holder.text(R.id.pref_summary, item.summary)
                                } else {
                                    item.checkedItem = oldCheckedItem
                                    item.summary = oldSummary
                                }
                            }
                    )
                }
            }
        }
    }
}
