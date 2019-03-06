package com.tuuzed.androidx.recyclerview.adapter.sample

import android.content.Context
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuuzed.android.recyclerview.adapter.prefs.*
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cxt: Context
    private lateinit var listAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cxt = this
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = RecyclerViewAdapter()
                .with(recyclerView)
                .withPrefs()

        listAdapter.appendItems(
                prefCategory {
                    title = "Category"
                },
                prefGeneral {
                    title = "General"
                    summary = "General#summary"
                },
                prefEditText {
                    title = "EditText"
                    summary = "EditText#summary"
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                },
                prefDivider(),
                prefCheckBox {
                    title = "CheckBox"
                    summary = "CheckBox#summary"
                },
                prefSwitch {
                    title = "Switch"
                    summary = "Switch#summary"
                },
                prefRadio {
                    title = "Radio"
                    summary = "Radio#summary"
                },
                prefDivider(),
                prefSingleList {
                    title = "SingleList"
                    summary = "SingleList#summary"
                    valuesLoader = {
                        it(arrayListOf(
                                "Single Item 1",
                                "Single Item 2",
                                "Single Item 3",
                                "Single Item 4"
                        ))
                    }
                    displaysLoader = {
                        it(arrayListOf(
                                "Single Item 1",
                                "Single Item 2",
                                "Single Item 3",
                                "Single Item 4"
                        ))
                    }
                },
                prefMultiList {
                    title = "MultiList"
                    summary = "MultiList#summary"
                    valuesLoader = {
                        it(arrayListOf(
                                "Multi Item 1",
                                "Multi Item 2",
                                "Multi Item 3",
                                "Multi Item 4"
                        ))
                    }
                    displaysLoader = {
                        it(arrayListOf(
                                "Multi Item 1",
                                "Multi Item 2",
                                "Multi Item 3",
                                "Multi Item 4"
                        ))
                    }
                }
        ).notifyDataSetChanged()
    }
}
