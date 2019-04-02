package com.tuuzed.androidx.recyclerview.adapter.pageable

import androidx.recyclerview.widget.RecyclerView
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter

typealias FetcherCallback = (pageableList: PageableList) -> Unit

typealias PageableListViewConfigure = (recyclerView: RecyclerView, listAdapter: RecyclerViewAdapter) -> Unit


typealias OnAsyncFetchDataTask = (page: Int, size: Int, callback: FetcherCallback) -> Unit