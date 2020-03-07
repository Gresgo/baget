package com.urtisi.baget.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.urtisi.baget.R
import com.urtisi.baget.ui.schedule.ScheduleFragment
import com.urtisi.baget.databinding.ActivityMainBinding
import com.urtisi.baget.ui.feed.MainNewsFragment
import com.urtisi.baget.ui.files.FilesFragment
import com.urtisi.baget.ui.profile.ProfileFragment
import com.urtisi.baget.ui.umk.UmkFragment


class MainActivity : AppCompatActivity() {

    private lateinit var newsFragment: Fragment
    private lateinit var dashboardFragment: Fragment
    private lateinit var filesFragment: Fragment
    private lateinit var umkFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var binding: ActivityMainBinding
    private val fm = supportFragmentManager
    private lateinit var activeFragment: Fragment

    /**
     * activity after logging in, contains all bottom menu screens fragments
     * (handling bottom navigation clicks and top right corner "profile" button)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.navView.setOnNavigationItemSelectedListener(navListener)
        supportActionBar!!.title = "News"

        if (savedInstanceState == null) {
            profileFragment = ProfileFragment()
            umkFragment = UmkFragment()
            filesFragment = FilesFragment()
            dashboardFragment = ScheduleFragment()
            newsFragment = MainNewsFragment()
            fm.beginTransaction().add(R.id.nav_host_fragment, profileFragment, "profile").hide(profileFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, umkFragment, "umk").hide(umkFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, filesFragment, "files").hide(filesFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, dashboardFragment, "schedule").hide(dashboardFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, newsFragment, "news").commit()
            activeFragment = newsFragment
        } else {

            val fragments = ArrayList<Fragment?>()
            profileFragment = fm.findFragmentByTag("profile")!!
            fragments.add(profileFragment)
            umkFragment = fm.findFragmentByTag("umk")!!
            fragments.add(umkFragment)
            filesFragment = fm.findFragmentByTag("files")!!
            fragments.add(filesFragment)
            dashboardFragment = fm.findFragmentByTag("schedule")!!
            fragments.add(dashboardFragment)
            newsFragment = fm.findFragmentByTag("news")!!
            fragments.add(newsFragment)

            fragments.forEach {
                it?.let {
                    if (!it.isHidden) {
                        activeFragment = it

                    }
                }
            }
            Log.i("fragments", "active fragment $activeFragment")
        }

    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            //TODO: add backstack class/handling
            when (item.itemId){
                R.id.navigation_news_main ->{
                    fm.beginTransaction().hide(activeFragment).show(newsFragment).commit()
                    activeFragment = newsFragment
                    supportActionBar!!.title = "News"
                }

                R.id.navigation_dashboard ->{
                    fm.beginTransaction().hide(activeFragment).show(dashboardFragment).commit()
                    activeFragment = dashboardFragment
                    supportActionBar!!.title = "Dashboard"
                }

                R.id.navigation_files -> {
                    fm.beginTransaction().hide(activeFragment).show(filesFragment).commit()
                    activeFragment = filesFragment
                    supportActionBar!!.title = "Files"
                }

                R.id.navigation_umk -> {
                    fm.beginTransaction().hide(activeFragment).show(umkFragment).commit()
                    activeFragment = umkFragment
                    supportActionBar!!.title = "Umk"
                }
            }
            true
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.action_bar_profile){
            fm.beginTransaction().hide(activeFragment).show(profileFragment).commit()
            activeFragment = profileFragment
            supportActionBar!!.title = "Profile"
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
