package com.tuuzed.androidx.recyclerview.adapter.sample

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tuuzed.androidx.recyclerview.adapter.pageable.PageableList
import com.tuuzed.androidx.recyclerview.adapter.pageable.PageableListView
import kotlinx.android.synthetic.main.feedlist_act.*
import kotlin.concurrent.thread

class FeedListActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FeedListActivity"
    }

    private var index: Int = 0

    private lateinit var layoutManagerName: String
    private lateinit var pageableListView: PageableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedlist_act)
        pageableListView = PageableListView(
                container = container_PageableListView,
                configure = { recyclerView, listAdapter ->
                    listAdapter.apply {
                        bind(String::class.java, android.R.layout.simple_list_item_1) { holder, item, _ ->
                            holder.text(android.R.id.text1, item)
                        }
                    }
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
                },
                onAsyncFetchDataTask = { page, size, callback ->
                    thread {
                        val list = mutableListOf<String>()
                        if (Math.random() > 0.9) {
                            for (i in 1..5) {
                                list.add("LayoutManager: $layoutManagerName, Page: $page, Index: ${++index}")
                            }
                        } else {
                            for (i in 1..size) {
                                list.add("LayoutManager: $layoutManagerName, Page: $page, Index: ${++index}")
                            }
                        }
                        SystemClock.sleep(200)
                        runOnUiThread {
                            if (Math.random() > 0.9) {
                                callback(PageableList(error = true, msg = "加载失败"))
                            } else {
                                callback(PageableList(error = false, payload = list))
                            }
                        }
                    }
                }
        )
    }

}