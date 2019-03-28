package com.tuuzed.androidx.recyclerview.adapter.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuuzed.androidx.recyclerview.adapter.RecyclerViewAdapter
import com.tuuzed.androidx.recyclerview.adapter.prefs.prefDsl
import com.tuuzed.androidx.recyclerview.adapter.prefs.withPrefs
import kotlinx.android.synthetic.main.main_act.*

class MainActivity : AppCompatActivity() {

    private lateinit var cxt: Context
    private lateinit var listAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cxt = this
        setContentView(R.layout.main_act)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = RecyclerViewAdapter.with(recyclerView).withPrefs().prefDsl {
            prefGeneral {
                title = "Feed List"
                summary = "Feed List"
                callback = { _, _ ->
                    startActivity(Intent(cxt, FeedListActivity::class.java))
                    true
                }
            }
            prefCategory {
                title = "Prefs"
            }
            prefGeneral {
                title = "General"
                summary = "General#summary"
            }
            prefEditText {
                title = "EditText"
                summary = "EditText#summary"
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            }
            prefDivider {}
            prefCheckBox {
                title = "CheckBox"
                summary = "CheckBox#summary"
            }
            prefSwitch {
                title = "Switch"
                summary = "Switch#summary"
            }
            prefRadio {
                title = "Radio"
                summary = "Radio#summary"
            }
            prefDivider {}
            prefSingleList {
                title = "SingleList"
                summary = "SingleList#summary"
                itemsLoader = {
                    it(arrayListOf(
                            "Single Item 1",
                            "Single Item 2",
                            "Single Item 3",
                            "Single Item 4"
                    ))
                }
                itemsLoader = {
                    it(arrayListOf(
                            "Single Item 1",
                            "Single Item 2",
                            "Single Item 3",
                            "Single Item 4"
                    ))
                }
            }
            prefMultiList {
                title = "MultiList"
                summary = "MultiList#summary"
                itemsLoader = {
                    it(arrayListOf(
                            "Multi Item 1",
                            "Multi Item 2",
                            "Multi Item 3",
                            "Multi Item 4"
                    ))
                }
                itemsLoader = {
                    it(arrayListOf(
                            "Multi Item 1",
                            "Multi Item 2",
                            "Multi Item 3",
                            "Multi Item 4"
                    ))
                }
            }
        }
        listAdapter.notifyDataSetChanged()
    }
}
