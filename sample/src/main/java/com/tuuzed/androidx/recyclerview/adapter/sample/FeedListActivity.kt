package com.tuuzed.androidx.recyclerview.adapter.sample

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuuzed.androidx.recyclerview.adapter.AbstractItemViewBinder
import com.tuuzed.androidx.recyclerview.adapter.CommonItemViewHolder
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter
import com.tuuzed.androidx.recyclerview.adapter.loadmore.LoadMoreState
import com.tuuzed.androidx.recyclerview.adapter.loadmore.withLoadMore
import kotlinx.android.synthetic.main.feedlist_act.*
import kotlin.concurrent.thread

class FeedListActivity : AppCompatActivity() {
    private lateinit var listAdapter: RecyclerViewAdapter

    private var page: Int = 0

    private val loadMoreState = LoadMoreState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedlist_act)
        recyclerView.layoutManager = LinearLayoutManager(this)
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
                    it.setLoadFailed(false)
                    loadData(page)
                }
        swipeRefreshLayout.setOnRefreshListener {
            loadMoreState.setRefresh()
            loadData(0)
        }
        loadData(page)
    }

    private fun loadData(page: Int) {
        if (page == 5) {
            loadMoreState.setLoadComplete()
        }
        if (page == 0) {
            listAdapter.items.clear()
        }
        thread {
            for (index in 1..5) {
                listAdapter.items.add("Page: $page, Index: $index")
            }
            SystemClock.sleep(1000)
            runOnUiThread {
                listAdapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
                this.page = page + 1
            }
        }
    }

}