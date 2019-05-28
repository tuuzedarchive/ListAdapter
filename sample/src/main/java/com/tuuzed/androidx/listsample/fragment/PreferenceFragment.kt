package com.tuuzed.androidx.listsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuuzed.androidx.list.adapter.ListAdapter
import com.tuuzed.androidx.list.preference.PreferenceCallback
import com.tuuzed.androidx.list.preference.Preferences
import com.tuuzed.androidx.list.preference.ktx.*
import com.tuuzed.androidx.listsample.R
import com.tuuzed.androidx.listsample.common.ListItemDivider
import kotlinx.android.synthetic.main.pageable_fragment.*
import java.util.*

class PreferenceFragment : Fragment() {

    companion object {
        fun newInstance() = PreferenceFragment()
    }

    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.preference_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(LinkedList<Any>())
        Preferences.bindAllTo(listAdapter)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = listAdapter
        recyclerView.addItemDecoration(ListItemDivider(requireContext()))

        usePreferences(listAdapter) {
            category { title = "Category" }
            general {
                title = "General"
                summary = "Summary"
            }
            clickable {
                title = "Clickable"
                summary = "Summary"
                click = PreferenceCallback { _, _ ->
                    Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
                    true
                }
            }
            category { title = "CompoundButton" }
            checkBox {
                title = "Checkbox"
                summary = "Summary"
                isChecked = true
            }
            radio {
                title = "Radio"
                summary = "Summary"
                isChecked = true
            }
            switch {
                title = "Switch"
                summary = "Summary"
                isChecked = true
            }
            category { title = "EditView" }
            editText {
                title = "EditView"
                summary = "Summary"
            }
            category { title = "Items" }
            singleChoiceItems<String> {
                title = "SingleChoiceItems"
                summary = "Summary"
                options = (1..100).map { "SingleChoiceItem $it" }
            }
            multiChoiceItems<String> {
                title = "MultiChoiceItems"
                summary = "Summary"
                options = (1..100).map { "MultiChoiceItem $it" }
            }
        }
        listAdapter.notifyDataSetChanged()
    }
}
