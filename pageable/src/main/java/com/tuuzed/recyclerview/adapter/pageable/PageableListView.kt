@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.tuuzed.recyclerview.adapter.pageable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tuuzed.recyclerview.adapter.RecyclerViewAdapter

open class PageableListView(
        container: ViewGroup,
        startPage: Int = 0,
        pageSize: Int = 10,
        configure: PageableListViewConfigure,
        onAsyncFetchDataTask: OnAsyncFetchDataTask
) {

    private val listAdapter: RecyclerViewAdapter
    private val pageableListDataFetcher: PageableListDataFetcher

    private val recyclerView: RecyclerView
    private val swipeRefreshLayout: SwipeRefreshLayout
    private val listContainer: ViewGroup
    private val emptyContainer: ViewGroup
    private val emptyIcon: ImageView
    private val emptyMessage: TextView

    init {
        container.removeAllViews()
        val cxt = container.context
        LayoutInflater.from(cxt).inflate(R.layout.pageable_listview, container, true).let {
            recyclerView = it.findViewById(R.id.pageable_RecyclerView)
            swipeRefreshLayout = it.findViewById(R.id.pageable_SwipeRefreshLayout)
            listContainer = it.findViewById(R.id.pageable_ContainerList)
            emptyContainer = it.findViewById(R.id.pageable_ContainerEmpty)
            emptyIcon = it.findViewById(R.id.pageable_EmptyIcon)
            emptyMessage = it.findViewById(R.id.pageable_EmptyMessage)
        }

        listContainer.visibility = View.VISIBLE
        emptyContainer.visibility = View.GONE

        listAdapter = RecyclerViewAdapter.with(recyclerView)

        configure(recyclerView, listAdapter)

        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = GridLayoutManager(cxt, 1)
        }
        pageableListDataFetcher = PageableListDataFetcher(
                listAdapter = listAdapter,
                swipeRefreshLayout = swipeRefreshLayout,
                recyclerView = recyclerView,
                startPage = startPage,
                pageSize = pageSize,
                export = object : PageableListDataFetcher.Export {
                    override fun onFetchDataTaskAsync(page: Int, size: Int, callback: FetcherCallback) =
                            onAsyncFetchDataTask(page, size, callback)

                    override fun onShowEmptyView(enable: Boolean, msg: String, error: Throwable?) =
                            this@PageableListView.onShowEmptyView(enable, msg, error)
                }
        )
    }

    fun refreshListData() {
        swipeRefreshLayout.isRefreshing = true
        pageableListDataFetcher.fetchData(true)
    }


    open fun onShowEmptyView(enable: Boolean, msg: String, error: Throwable?) {
        if (enable) {
            listContainer.visibility = View.GONE
            emptyContainer.visibility = View.VISIBLE
            emptyMessage.text = msg
            emptyContainer.setOnClickListener {
                refreshListData()
            }
        } else {
            listContainer.visibility = View.VISIBLE
            emptyContainer.visibility = View.GONE
            emptyContainer.setOnClickListener(null)
        }
    }


}