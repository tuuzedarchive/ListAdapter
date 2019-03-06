package com.tuuzed.android.recyclerview.adapter.prefs

import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder

data class PrefCategoryItem(
        var title: String = ""
)

class PrefCategoryItemViewBinder : AbstractItemViewBinder<PrefCategoryItem>() {
    override fun getLayoutId() = R.layout.pref_listitem_category

    override fun onBindViewHolder(holder: CommonItemViewHolder, item: PrefCategoryItem, position: Int) {
        holder.text(R.id.tv_Title, item.title)
    }
}