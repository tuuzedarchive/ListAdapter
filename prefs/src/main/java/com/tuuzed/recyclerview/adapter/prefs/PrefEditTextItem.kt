package com.tuuzed.recyclerview.adapter.prefs

import android.text.InputType
import android.view.View
import androidx.annotation.LayoutRes
import com.tuuzed.androidx.exdialog.ExDialog
import com.tuuzed.androidx.exdialog.ext.input
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

open class PrefEditTextItemViewBinder<in T : PrefEditTextItem>(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_edittext
) : AbstractItemViewBinder<T>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {
        holder.text(R.id.pref_title, item.title)
        holder.text(R.id.pref_summary, item.summary)
        holder.click(R.id.pref_item_layout) { handleItemLayoutClick(it, holder, item, position) }
    }

    open fun handleItemLayoutClick(view: View, holder: CommonItemViewHolder, item: T, position: Int) {

        ExDialog(view.context).show {
            input(
                    title = item.title,
                    hint = item.hint,
                    maxLength = item.maxLength,
                    prefill = item.summary,
                    inputType = item.inputType,
                    callback = { _, text ->
                        val oldSummary = item.summary
                        item.summary = text.toString()
                        if (item.callback(item, position)) {
                            holder.text(R.id.pref_summary, item.summary)
                        } else {
                            item.summary = oldSummary
                        }
                    }
            ) {
                onTextChanged { _, text ->
                    if (!item.allowEmpty) positiveButtonEnable(text.isNotEmpty())
                }
                positiveButton()
            }
        }
    }
}
