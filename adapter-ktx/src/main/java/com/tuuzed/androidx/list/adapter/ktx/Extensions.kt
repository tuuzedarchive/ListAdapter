package com.tuuzed.androidx.list.adapter.ktx

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.tuuzed.androidx.list.adapter.CommonViewHolder
import com.tuuzed.androidx.list.adapter.ItemViewBinder
import com.tuuzed.androidx.list.adapter.ListAdapter

inline fun <T : View> CommonViewHolder.withView(@IdRes id: Int, block: T.() -> Unit) {
    block(this.find(id))
}

fun <T> ListAdapter.bindType(
    type: Class<T>,
    @LayoutRes layoutRes: Int,
    onBindViewHolder: (holder: CommonViewHolder, item: T, position: Int) -> Unit
) {
    this.bind(type, object : ItemViewBinder.Factory<T, CommonViewHolder>(layoutRes) {
        override fun createViewHolder(itemView: View): CommonViewHolder = CommonViewHolder(itemView)
        override fun onBindViewHolder(holder: CommonViewHolder, t: T, position: Int) =
            onBindViewHolder(holder, t, position)
    })
}

fun <T, VH : RecyclerView.ViewHolder> ListAdapter.bindType(
    type: Class<T>,
    @LayoutRes layoutRes: Int,
    createViewHolder: (itemView: View) -> VH,
    onBindViewHolder: (holder: VH, item: T, position: Int) -> Unit
) {
    this.bind(type, object : ItemViewBinder.Factory<T, VH>(layoutRes) {
        override fun createViewHolder(itemView: View): VH = createViewHolder(itemView)
        override fun onBindViewHolder(holder: VH, t: T, position: Int) = onBindViewHolder(holder, t, position)
    })
}