package com.tuuzed.androidx.listsample

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.tuuzed.androidx.listsample.fragment.HorizontalListFragment
import com.tuuzed.androidx.listsample.fragment.PageableFragment
import com.tuuzed.androidx.listsample.fragment.PreferenceFragment
import com.tuuzed.androidx.listsample.fragment.VerticalListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragments = listOf(
        lazy { PageableFragment.newInstance() },
        lazy { PreferenceFragment.newInstance() },
        lazy { VerticalListFragment.newInstance() },
        lazy { HorizontalListFragment.newInstance() }
    )
    private val titles = listOf(
        "Pageable",
        "Preference",
        "Vertical List",
        "Horizontal List"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        if (
            getSharedPreferences("theme", Context.MODE_PRIVATE).getBoolean("dark", false)
        ) {
            setTheme(R.style.AppDarkTheme_NoActionBar)
        } else {
            setTheme(R.style.AppTheme_NoActionBar)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = object :
            FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position].value
            }

            override fun getCount(): Int {
                return fragments.size
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_theme -> {
                val dark = getSharedPreferences("theme", Context.MODE_PRIVATE).getBoolean("dark", false)
                getSharedPreferences("theme", Context.MODE_PRIVATE).edit {
                    putBoolean("dark", !dark)
                }
                restartActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun restartActivity() = recreate()

}
