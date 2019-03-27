package com.tuuzed.androidx.recyclerview.adapter.sample

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
    private var index: Int = 0

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
                    bind(String::class.java, android.R.layout.simple_list_item_1) { holder, item, _ ->
                        holder.text(android.R.id.text1, item)
                    }
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
        thread {
            val list = mutableListOf<String>()
            for (i in 1..10) {
                list.add("LayoutManager: $layoutManagerName, Page: $page, Index: ${++index}")
            }
            SystemClock.sleep(200)
            runOnUiThread {
                if (Math.random() > 0.9) {
                    Log.d(TAG, "LoadFailed")

                    if (page == 0) {
                        swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
                    } else {
                        loadMoreState.setLoadFailed(true)
                    }
                } else {
                    Log.d(TAG, "LoadSuccess")
                    if (page == 0) {
                        listAdapter.items.clear()
                    }
                    loadMoreState.setLoadFailed(false)
                    listAdapter.appendItems(list)
                    swipeRefreshLayout.isRefreshing = false
                    this.page = page + 1
                    if (page >= 5) {
                        loadMoreState.setLoadComplete()
                    }
                    listAdapter.notifyDataSetChanged()
                }
            }
        }
    }

}