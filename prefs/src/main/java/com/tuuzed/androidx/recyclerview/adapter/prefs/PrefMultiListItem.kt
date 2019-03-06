package com.tuuzed.androidx.recyclerview.adapter.prefs

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder
import java.util.*

data class PrefMultiListItem(
        var title: String = "",
        var summary: String = "",
        var valuesLoader: ValuesLoader = { it(emptyList()) },
        var displaysLoader: DisplaysLoader = { it(emptyList()) },
        var itemSeparator: String = "\r\n",
        var allowEmptySelection: Boolean = false,
        var checkedValues: List<Any> = Collections.emptyList(),
        var checkedDisplays: List<String> = summary.split(itemSeparator),
        var callback: PrefItemCallback<PrefMultiListItem> = { _, _ -> true }
)

class PrefMultiListItemViewBinder : AbstractItemViewBinder<PrefMultiListItem>() {
    override fun getLayoutId() = R.layout.pref_listitem_multilist

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefMultiListItem, position: Int) {
        holder.text(R.id.tv_Title, item.title)
        holder.text(R.id.tv_Summary, item.summary)
        holder.click(R.id.itemLayout) { v ->
            item.valuesLoader { values ->
                item.displaysLoader { displays ->
                    if (values.size != displays.size) {
                        throw IllegalArgumentException("values.size != displays.size")
                    }
                    val initialSelection = ArrayList<Int>()

                    values.forEachIndexed { index, any ->
                        if (item.checkedValues.contains(any)) {
                            initialSelection.add(index)
                        }
                    }
                    MaterialDialog(v.context).show {
                        title(text = item.title)
                        noAutoDismiss()
                        listItemsMultiChoice(
                                items = displays,
                                allowEmptySelection = item.allowEmptySelection,
                                initialSelection = initialSelection.toIntArray(),
                                selection = { _, indices, _ ->
                                    val oldCheckedValues = item.checkedValues
                                    val oldCheckedDisplays = item.checkedDisplays
                                    val oldSummary = item.summary
                                    item.checkedValues = values.filterIndexed { index, _ -> indices.contains(index) }
                                    item.checkedDisplays = displays.filterIndexed { index, _ -> indices.contains(index) }
                                    item.summary = item.checkedDisplays.joinToString(item.itemSeparator)
                                    if (item.callback(item, position)) {
                                        holder.text(R.id.tv_Summary, item.summary)
                                    } else {
                                        item.checkedValues = oldCheckedValues
                                        item.checkedDisplays = oldCheckedDisplays
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
    }

}
