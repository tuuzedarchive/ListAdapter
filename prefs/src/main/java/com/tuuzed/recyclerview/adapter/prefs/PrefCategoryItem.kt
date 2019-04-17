package com.tuuzed.recyclerview.adapter.prefs

import androidx.annotation.LayoutRes
import com.tuuzed.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.recyclerview.adapter.CommonItemViewHolder

open class PrefCategoryItem(
        var title: String = ""
)

open class PrefCategoryItemViewBinder<in T : PrefCategoryItem>(
        @LayoutRes private val layoutId: Int = R.layout.pref_listitem_category
) : AbstractItemViewBinder<T>() {
    override fun getLayoutId() = layoutId

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {
        holder.text(R.id.pref_title, item.title)
    }
}