package com.tuuzed.androidx.listsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuuzed.androidx.list.adapter.ListAdapter
import com.tuuzed.androidx.listsample.R
import java.util.*

abstract class BaseListFragment : Fragment() {
    lateinit var listAdapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.preference_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(LinkedList<Any>())
    }

}
