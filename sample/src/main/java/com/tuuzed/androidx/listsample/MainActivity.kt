package com.tuuzed.androidx.listsample

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.tuuzed.androidx.listsample.fragment.PageableFragment
import com.tuuzed.androidx.listsample.fragment.PreferenceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragments = listOf(
        lazy { PageableFragment.newInstance() },
        lazy { PreferenceFragment.newInstance() }
    )
    private val titles = listOf(
        "Pageable",
        "Preference"
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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_change_theme -> {
                val drak = getSharedPreferences("theme", Context.MODE_PRIVATE).getBoolean("dark", false)
                getSharedPreferences("theme", Context.MODE_PRIVATE).edit {
                    putBoolean("dark", !drak)
                }
                restartActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun restartActivity() {
        recreate()
    }

}
