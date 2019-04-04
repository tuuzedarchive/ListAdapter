package com.tuuzed.recyclerview.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class AbstractItemViewBinder<in T> : ItemTypeViewBinder<T, CommonItemViewHolder> {

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
        return CommonItemViewHolder(itemView)
    }

}