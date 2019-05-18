package com.tuuzed.recyclerview.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecyclerViewAdapter @JvmOverloads constructor(
    var items: MutableList<Any> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object Factory {

        @JvmOverloads
        fun with(recyclerView: RecyclerView,
                 items: MutableList<Any> = mutableListOf(),
                 block: (RecyclerViewAdapter.() -> Unit)? = null
        ): RecyclerViewAdapter {
            return RecyclerViewAdapter(items).also {
                it.with(recyclerView)
                if (block != null) {
                    it.with(recyclerView, block)
                }
            }
        }
    }

    private val binders = mutableListOf<ItemTypeViewBinder<*, *>>()
    private val types = mutableListOf<Class<*>>()

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        val type = item::class.java
        return types.indexOf(type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (binders.size < viewType - 1) {
            throw IllegalStateException("Not viewType ($viewType) type.")
        }
        val binder = binders[viewType]
        return binder.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val item = items[position]

        if (binders.size < viewType - 1) {
            throw IllegalStateException("Not viewType ($viewType) type.")
        }
        @Suppress("UNCHECKED_CAST")
        val binder = binders[viewType]
            as ItemTypeViewBinder<Any, RecyclerView.ViewHolder>

        binder.onBindViewHolder(holder, item, position)
    }

    override fun getItemCount(): Int = items.size

    fun <T, VH : RecyclerView.ViewHolder> bind(type: Class<in T>, binder: ItemTypeViewBinder<T, VH>): RecyclerViewAdapter {
        val index = types.indexOf(type)
        if (index == -1) {
            types.add(type)
            binders.add(binder)
        } else {
            binders[index] = binder
        }
        return this
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

    fun setItems(items: List<*>): RecyclerViewAdapter {
        if (items is MutableList<*>) {
            @Suppress("UNCHECKED_CAST")
            this.items = items as MutableList<Any>
        } else {
            this.items = mutableListOf()
            appendItems(items)
        }
        return this
    }

    fun appendItems(items: List<*>): RecyclerViewAdapter {
        @Suppress("UNCHECKED_CAST")
        this.items.addAll(items as List<Any>)
        return this
    }

    fun appendItems(vararg items: Any): RecyclerViewAdapter {
        Collections.addAll(this.items, *items)
        return this
    }

}