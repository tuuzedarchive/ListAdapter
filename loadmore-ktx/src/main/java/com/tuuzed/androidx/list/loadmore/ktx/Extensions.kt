package com.tuuzed.androidx.list.loadmore.ktx

import androidx.recyclerview.widget.RecyclerView
import com.tuuzed.androidx.list.loadmore.LoadMore

fun useLoadMore(adapter: RecyclerView.Adapter<*>) = LoadMore.of(adapter)


