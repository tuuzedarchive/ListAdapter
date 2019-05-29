package com.tuuzed.androidx.listsample.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tuuzed.androidx.list.adapter.ListAdapter
import com.tuuzed.androidx.list.preference.interfaces.ItemsLoaderFunction
import com.tuuzed.androidx.list.preference.interfaces.PreferenceCallback
import com.tuuzed.androidx.list.preference.Preferences
import com.tuuzed.androidx.list.preference.interfaces.Validator
import com.tuuzed.androidx.list.preference.ktx.*
import com.tuuzed.androidx.listsample.R
import com.tuuzed.androidx.listsample.common.ListItemDivider
import kotlinx.android.synthetic.main.pageable_fragment.*
import java.util.*

class PreferenceFragment : Fragment() {

    companion object {
        private const val TAG = "PreferenceFragment"
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
        withPreferences(listAdapter) {
            category { title = "Category" }
            general {
                title = "General"
                summary = "Summary"
            }
            clickable {
                title = "Clickable"
                summary = "Summary"
                click = PreferenceCallback { _, _ ->
                    showTip("Click")
                    true
                }
            }
            category { title = "CompoundButton" }
            checkBox {
                title = "Checkbox"
                summary = "Summary"
                isChecked = true
                callback = PreferenceCallback { preference, _ ->
                    showTip("isChecked: ${preference.isChecked}")
                    preference.summary = if (preference.isChecked) "On" else "Off"
                    true
                }
            }
            radio {
                title = "Radio"
                summary = "Summary"
                isChecked = true
                callback = PreferenceCallback { preference, _ ->
                    showTip("isChecked: ${preference.isChecked}")
                    preference.summary = if (preference.isChecked) "On" else "Off"
                    true
                }
            }
            switch {
                title = "Switch"
                summary = "Summary"
                isChecked = true
                callback = PreferenceCallback { preference, _ ->
                    showTip("isChecked: ${preference.isChecked}")
                    preference.summary = if (preference.isChecked) "On" else "Off"
                    true
                }
            }
            category { title = "EditView" }
            editText {
                title = "EditView"
                summary = "Summary"
                callback = PreferenceCallback { preference, _ ->
                    showTip(preference.summary)
                    true
                }
            }
            editText {
                title = "EditView#Range"
                summary = "Summary"
                maxLength = 10
                helperText = "helperText"
                callback = PreferenceCallback { preference, _ ->
                    showTip(preference.summary)
                    true
                }
            }
            editText {
                title = "EditView#Validator"
                summary = "123456"
                helperText = "输入数字"
                textValidator = Validator { text, errorText ->
                    Log.d(TAG, "textValidator: $text")
                    try {
                        text.toString().toInt()
                        true
                    } catch (e: Exception) {
                        Log.d(TAG, "textValidator: ${e.message}", e)
                        errorText[0] = "错误"
                        false
                    }
                }
                callback = PreferenceCallback { preference, _ ->
                    showTip(preference.summary)
                    true
                }
            }
            category { title = "Items" }
            singleChoiceItems {
                title = "SingleChoiceItems"
                summary = "Summary"
                itemsLoaderFunction = ItemsLoaderFunction { callback ->
                    callback((1..100).map { "SingleChoiceItem $it" })
                }
                callback = PreferenceCallback { preference, _ ->
                    showTip(preference.summary)
                    true
                }
            }
            singleChoiceItems {
                title = "SingleChoiceItems#NeedConfirm"
                summary = "Summary"
                isNeedConfirm = true
                itemsLoaderFunction = ItemsLoaderFunction { callback ->
                    callback((1..100).map { "SingleChoiceItem $it" })
                }
                callback = PreferenceCallback { preference, _ ->
                    showTip(preference.summary)
                    true
                }
            }
            multiChoiceItems {
                title = "MultiChoiceItems"
                summary = "Summary"
                itemsLoaderFunction = ItemsLoaderFunction { callback ->
                    callback((1..100).map { "SingleChoiceItem $it" })
                }
                callback = PreferenceCallback { preference, _ ->
                    showTip(preference.summary)
                    true
                }
            }
        }
        listAdapter.notifyDataSetChanged()
    }

    private fun showTip(text: CharSequence) {
        Snackbar.make(recyclerView, text, Snackbar.LENGTH_SHORT).show()
    }
}
