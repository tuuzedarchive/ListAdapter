package com.tuuzed.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView
import java.util.*

internal class ItemTypePool(initCapacity: Int) {
    private val itemTypeViewBinders: MutableList<ItemTypeViewBinder<*, *>>
    private val itemTypes: MutableList<Class<*>>

    init {
        itemTypeViewBinders = ArrayList(initCapacity)
        itemTypes = ArrayList(initCapacity)
    }

    fun <T> register(itemType: Class<in T>, itemTypeBinder: ItemTypeViewBinder<T, *>) {
        val index = indexOfItemType(itemType)
        if (index == -1) {
            itemTypes.add(itemType)
            itemTypeViewBinders.add(itemTypeBinder)
        } else {
            itemTypeViewBinders[index] = itemTypeBinder
        }
    }

    fun indexOf(itemType: Class<*>): Int {
        val itemViewType = indexOfItemType(itemType)
        if (itemViewType == -1) {
            throw IllegalStateException("Not registered (" + itemType.name + ") type.")
        }
        return itemViewType
    }

    fun findItemTypeViewBinder(viewType: Int): ItemTypeViewBinder<Any, RecyclerView.ViewHolder> {
        if (itemTypeViewBinders.size < viewType - 1) {
            throw IllegalStateException("Not viewType ($viewType) type.")
        }
        @Suppress("UNCHECKED_CAST")
        return itemTypeViewBinders[viewType] as ItemTypeViewBinder<Any, RecyclerView.ViewHolder>
    }

    private fun indexOfItemType(itemType: Class<*>): Int {
        return itemTypes.indexOf(itemType)
    }


}