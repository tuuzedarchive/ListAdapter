package com.tuuzed.recyclerview.adapter.kotlin

import androidx.recyclerview.widget.RecyclerView
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter


inline fun RecyclerViewAdapter.with(
        recyclerView: RecyclerView,
        block: RecyclerViewAdapter.() -> Unit
): RecyclerViewAdapter {
    this.with(recyclerView)
    this.block()
    return this
}



