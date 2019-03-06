package com.tuuzed.androidx.recyclerview.adapter.prefs

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

data class PrefSingleListItem(
        var title: String = "",
        var summary: String = "",
        var valuesLoader: ValuesLoader = { it(emptyList()) },
        var displaysLoader: DisplaysLoader = { it(emptyList()) },
        var checkedValue: Any? = null,
        var checkedDisplay: String = summary,
        var callback: PrefItemCallback<PrefSingleListItem> = { _, _ -> true }
)

class PrefSingleListItemViewBinder : AbstractItemViewBinder<PrefSingleListItem>() {
    override fun getLayoutId() = R.layout.pref_listitem_singlelist

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefSingleListItem, position: Int) {
        holder.text(R.id.tv_Title, item.title)
        holder.text(R.id.tv_Summary, item.summary)
        holder.click(R.id.itemLayout) { v ->
            item.valuesLoader { values ->
                item.displaysLoader { displays ->
                    if (values.size != displays.size) {
                        throw IllegalArgumentException("values.size != displays.size")
                    }
                    MaterialDialog(v.context).show {
                        title(text = item.title)
                        listItemsSingleChoice(
                                items = displays,
                                initialSelection = values.indexOf(item.checkedValue),
                                selection = { _, index, text ->
                                    val oldCheckedValue = item.checkedValue
                                    val oldCheckedDisplay = item.checkedDisplay
                                    val oldSummary = item.summary
                                    item.checkedValue = values[index]
                                    item.checkedDisplay = text
                                    item.summary = text
                                    if (item.callback(item, position)) {
                                        holder.text(R.id.tv_Summary, item.summary)
                                    } else {
                                        item.checkedValue = oldCheckedValue
                                        item.checkedDisplay = oldCheckedDisplay
                                        item.summary = oldSummary
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}
