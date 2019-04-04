package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

data class PrefCategoryItem(
        var title: String = ""
)

class PrefCategoryItemViewBinder(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_category
) : AbstractItemViewBinder<PrefCategoryItem>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefCategoryItem, position: Int) {
        holder.text(R.id.pref_title, item.title)
    }
}