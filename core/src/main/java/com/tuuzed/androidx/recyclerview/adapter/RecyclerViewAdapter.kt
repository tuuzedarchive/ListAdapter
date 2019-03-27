package com.tuuzed.androidx.recyclerview.adapter

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecyclerViewAdapter constructor(
        var items: MutableList<Any>,
        @IntRange(from = 1) initTypeCapacity: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        @JvmOverloads
        fun with(recyclerView: RecyclerView,
                 items: MutableList<Any> = ArrayList(),
                 @IntRange(from = 1) initTypeCapacity: Int = 16,
                 block: (RecyclerViewAdapter.() -> Unit)? = null
        ): RecyclerViewAdapter {
            return RecyclerViewAdapter(items, initTypeCapacity).also {
                it.with(recyclerView)
                if (block != null) {
                    it.with(recyclerView, block)
                }
            }
        }

    }

    private val mItemTypePool: ItemTypePool = ItemTypePool(initTypeCapacity)

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return mItemTypePool.indexOf(item.javaClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, indexViewType: Int): RecyclerView.ViewHolder {
        val itemTypeViewBinder = mItemTypePool.findItemTypeViewBinder(indexViewType)
        return itemTypeViewBinder.onCreateViewHolder(parent, indexViewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val indexViewType = getItemViewType(position)
        val item = items[position]
        mItemTypePool.findItemTypeViewBinder(indexViewType).onBindViewHolder(holder, item, position)
    }

    override fun getItemCount(): Int = items.size

    fun <T, VH : RecyclerView.ViewHolder> bind(itemType: Class<in T>, itemTypeViewBinder: ItemTypeViewBinder<T, VH>): RecyclerViewAdapter {
        mItemTypePool.register(itemType, itemTypeViewBinder)
        return this
    }

    fun <T, VH : RecyclerView.ViewHolder> bind(itemType: Class<T>, onCreateViewHolder: OnCreateViewHolder<VH>, onBindViewHolder: OnBindViewHolder<T, VH>): ItemTypeViewBinder<T, VH> {
        val binder = object : ItemTypeViewBinder<T, VH> {
            override fun onBindViewHolder(holder: VH, item: T, position: Int) = onBindViewHolder(holder, item, position)
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = onCreateViewHolder(parent, viewType)
        }
        bind(itemType, binder)
        return binder
    }

    fun <T> bind(itemType: Class<T>, @LayoutRes layoutId: Int, onBindViewHolder: OnBindViewHolder<T, CommonItemViewHolder>): ItemTypeViewBinder<T, CommonItemViewHolder> {
        val binder = object : AbstractItemViewBinder<T>() {
            override fun getLayoutId() = layoutId
            override fun onBindViewHolder(holder: CommonItemViewHolder, item: T, position: Int) {
                onBindViewHolder(holder, item, position)
            }
        }
        bind(itemType, binder)
        return binder
    }

    fun with(recyclerView: RecyclerView): RecyclerViewAdapter {
        recyclerView.adapter = this
        return this
    }

    fun with(recyclerView: RecyclerView, block: RecyclerViewAdapter.() -> Unit): RecyclerViewAdapter {
        this.with(recyclerView)
        this.block()
        return this
    }

    fun setItems(items: MutableList<Any>): RecyclerViewAdapter {
        this.items = items
        return this
    }

    fun appendItems(items: List<Any>): RecyclerViewAdapter {
        this.items.addAll(items)
        return this
    }

    fun appendItems(vararg items: Any): RecyclerViewAdapter {
        Collections.addAll(this.items, *items)
        return this
    }

}