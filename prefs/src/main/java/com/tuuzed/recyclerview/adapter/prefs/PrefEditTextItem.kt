package com.tuuzed.recyclerview.adapter.prefs

import android.text.InputType
import android.view.View
import androidx.annotation.LayoutRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder


open class PrefEditTextItem(
        var title: String = "",
        var summary: String = "",
        var inputType: Int = InputType.TYPE_CLASS_TEXT,
        var hint: String? = null,
        var allowEmpty: Boolean = false,
        var maxLength: Int? = null,
        var callback: PrefItemCallback<PrefEditTextItem> = { _, _ -> true }
)

open class PrefEditTextItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_edittext
) : AbstractItemViewBinder<PrefEditTextItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefEditTextItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: PrefEditTextItem, position: Int) {
        MaterialDialog(view.context).show {
            title(text = item.title)
            input(
                    hint = item.hint,
                    maxLength = item.maxLength,
                    prefill = item.summary,
                    inputType = item.inputType,
                    allowEmpty = item.allowEmpty,
                    callback = { _, text ->
                        val oldSummary = item.summary
                        item.summary = text.toString()
                        if (item.callback(item, position)) {
                            holder.text(R.id.pref_summary, item.summary)
                        } else {
                            item.summary = oldSummary
                        }
                    }
            )
        }
    }
}
