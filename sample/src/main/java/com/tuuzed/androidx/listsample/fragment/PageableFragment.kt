package com.tuuzed.androidx.listsample.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tuuzed.androidx.list.adapter.ItemViewBinder
import com.tuuzed.androidx.list.adapter.ListAdapter
import com.tuuzed.androidx.list.loadmore.LoadMoreController
import com.tuuzed.androidx.list.loadmore.ktx.useLoadMore
import com.tuuzed.androidx.list.pageable.PagedList
import com.tuuzed.androidx.list.pageable.PagedListDataFetcher
import com.tuuzed.androidx.list.preference.Preferences
import com.tuuzed.androidx.listsample.R
import com.tuuzed.androidx.listsample.common.ListItemDivider
import com.tuuzed.androidx.listsample.model.NameItem
import kotlinx.android.synthetic.main.pageable_fragment.*
import java.util.*
import kotlin.random.Random

class PageableFragment : Fragment() {

    companion object {
        fun newInstance() = PageableFragment()
    }

    private lateinit var listAdapter: ListAdapter
    private val mainHandler = Handler(Looper.getMainLooper())

    private val loadMoreController = LoadMoreController()
    private val pagedListDataFetcher = object : PagedListDataFetcher<Any>(1, 5) {

        override fun onFetchListData(page: Int, pageSize: Int, fetchFunction: FetchFunction<Any>) {
            Log.d("MainActivity", "onFetchListData: page=$page,pageSize=$pageSize")
            mainHandler.postDelayed({
                if (page >= 10) {
                    fetchFunction.complete(PagedList.correct(emptyList()))
                    return@postDelayed
                }
                if (Random.nextFloat() < 0.8f) {
                    val range = (((page - 1) * pageSize + 1)..page * pageSize)
                    val listData = range.map { NameItem("Name $it") }
                    fetchFunction.complete(PagedList.correct(listData))

                } else {
                    fetchFunction.complete(PagedList.wrong(Exception("加载失败")))
                }
            }, 200)
        }

        override fun onLoadFailed(initialPage: Int, page: Int, cause: Throwable) {
            if (initialPage == page) {
                // 刷新界面
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(requireContext(), "刷新失败", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onLoadSucceed(initialPage: Int, page: Int, listData: MutableList<Any>) {
            if (initialPage == page) {
                // 刷新界面
                swipeRefreshLayout.isRefreshing = false
                listAdapter.items.clear()
                listAdapter.items.addAll(listData)
                listAdapter.notifyDataSetChanged()
            } else {
                // 加载更多
                val positionStart = listAdapter.items.size
                listAdapter.items.addAll(listData)
                listAdapter.notifyItemRangeInserted(positionStart, listData.size)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pageable_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(LinkedList<Any>())
        listAdapter.bind(NameItem::class.java, NameItemViewBinder())
        Preferences.bindAllTo(listAdapter)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = listAdapter
        recyclerView.addItemDecoration(ListItemDivider(requireContext()))

        swipeRefreshLayout.setOnRefreshListener {
            if (pagedListDataFetcher.isFetching) {
                swipeRefreshLayout.isRefreshing = false
            } else {
                pagedListDataFetcher.fetchListData(true, loadMoreController)
            }
        }
        useLoadMore(listAdapter)
            .setLoadMoreController(loadMoreController)
            .setOnLoadMoreListener(pagedListDataFetcher)
            .into(recyclerView)
    }

    private class NameItemViewBinder : ItemViewBinder<NameItem, NameItemViewHolder> {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameItemViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_name, parent, false)
            return NameItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: NameItemViewHolder, item: NameItem, position: Int) {
            holder.setNameItem(item)
        }
    }

    private class NameItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var item: NameItem? = null
        private val textView: TextView = itemView.findViewById(R.id.text1)

        fun setNameItem(item: NameItem?) {
            this.item = item
            textView.text = item?.name
            itemView.setOnClickListener {
                Snackbar.make(it, textView.text.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}