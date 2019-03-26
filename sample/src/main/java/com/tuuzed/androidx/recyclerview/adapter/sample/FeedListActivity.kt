package com.tuuzed.androidx.recyclerview.adapter.sample

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter
import com.tuuzed.androidx.recyclerview.adapter.loadmore.LoadMoreState
import com.tuuzed.androidx.recyclerview.adapter.loadmore.withLoadMore
import kotlinx.android.synthetic.main.feedlist_act.*
import kotlin.concurrent.thread

class FeedListActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FeedListActivity"
    }

    private lateinit var listAdapter: RecyclerViewAdapter

    private var page: Int = 0

    private lateinit var layoutManagerName: String
    private val loadMoreState = LoadMoreState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedlist_act)

        val random = Math.random()
        recyclerView.layoutManager = when {
            random > 0.6 -> {
                layoutManagerName = "Linear"
                LinearLayoutManager(this)
            }
            random > 0.3 -> {
                layoutManagerName = "Grid"
                GridLayoutManager(this, 2)
            }
            else -> {
                layoutManagerName = "StaggeredGrid"
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        listAdapter = RecyclerViewAdapter
                .with(recyclerView) {
                    bind(String::class.java, object : AbstractItemViewBinder<String>() {
                        override fun getLayoutId() = android.R.layout.simple_list_item_1
                        override fun onBindViewHolder(holder: CommonItemViewHolder, item: String, position: Int) {
                            holder.text(android.R.id.text1, item)
                        }
                    })
                }
                .withLoadMore(recyclerView, loadMoreState) {
                    loadData(page)
                }
        swipeRefreshLayout.setOnRefreshListener {
            loadMoreState.setRefresh()
            loadData(0)
        }
    }

    private fun loadData(page: Int) {
        Log.d(TAG, "loadData: page: $page")
        if (page == 5) {
            loadMoreState.setLoadComplete()
        }

        thread {
            val list = mutableListOf<String>()
            for (index in 1..10) {
                list.add("LayoutManager: $layoutManagerName, Page: $page, Index: $index")
            }
            SystemClock.sleep(200)
            if (Math.random() > 0.6) {
                runOnUiThread {
                    swipeRefreshLayout.isRefreshing = false
                    loadMoreState.setLoadFailed(true)
                }
            } else {
                runOnUiThread {
                    if (page == 0) {
                        listAdapter.items.clear()
                    }
                    loadMoreState.setLoadFailed(false)
                    listAdapter.appendItems(list)
                    listAdapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                    this.page = page + 1
                }
            }
        }
    }

}