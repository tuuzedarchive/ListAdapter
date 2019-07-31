package com.tuuzed.androidx.listsample.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tuuzed.androidx.list.adapter.ListItemDivider
import com.tuuzed.androidx.list.adapter.ktx.bindType
import com.tuuzed.androidx.list.adapter.ktx.withView
import com.tuuzed.androidx.listsample.R
import com.tuuzed.androidx.listsample.model.NameItem
import kotlinx.android.synthetic.main.baselist_fragment.*

class HorizontalListFragment : BaseListFragment() {

    companion object {
        fun newInstance() = HorizontalListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter.bindType(
            NameItem::class.java,
            R.layout.listitem_horiz_name
        ) { holder, item, _ ->
            holder.withView<TextView>(R.id.text1) {
                text = item.name
            }
            holder.itemView.setOnClickListener {
                Snackbar.make(it, item.name, Snackbar.LENGTH_SHORT).show()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        recyclerView.adapter = listAdapter
        recyclerView.addItemDecoration(
            ListItemDivider.builder()
                .setPaddingStart(8)
                .setPaddingEnd(8)
                .setSize(1)
                .setOrientation(ListItemDivider.VERTICAL)
                .build(requireContext())
        )
        for (i in 1..100) {
            listAdapter.items.add(NameItem("Item $i"))
        }
        listAdapter.notifyDataSetChanged()
    }

}
