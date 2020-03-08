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
    private lateinit var scheduleFragment: Fragment
    private lateinit var filesFragment: Fragment
    private lateinit var umkFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var binding: ActivityMainBinding
    private val fm = supportFragmentManager
    private lateinit var activeFragment: Fragment

    private lateinit var TAG_NEWS: String
    private lateinit var TAG_SCHEDULE: String
    private lateinit var TAG_FILES: String
    private lateinit var TAG_UMK: String
    private lateinit var TAG_PROFILE: String

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

        TAG_NEWS = applicationContext.getString(R.string.title_news)
        TAG_SCHEDULE = applicationContext.getString(R.string.title_schedule)
        TAG_FILES = applicationContext.getString(R.string.title_files)
        TAG_UMK = applicationContext.getString(R.string.title_umk)
        TAG_PROFILE = applicationContext.getString(R.string.title_profile)

        /**
         * initialize fragments when app is open
         */
        if (savedInstanceState == null) {
            supportActionBar!!.title = TAG_NEWS
            profileFragment = ProfileFragment()
            umkFragment = UmkFragment()
            filesFragment = FilesFragment()
            scheduleFragment = ScheduleFragment()
            newsFragment = MainNewsFragment()
            fm.beginTransaction().add(R.id.nav_host_fragment, profileFragment, TAG_PROFILE).hide(profileFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, umkFragment, TAG_UMK).hide(umkFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, filesFragment, TAG_FILES).hide(filesFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, scheduleFragment, TAG_SCHEDULE).hide(scheduleFragment).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment, newsFragment, TAG_NEWS).commit()
            activeFragment = newsFragment
        } else {
            /**
             * restore fragment view when activity has been destroyed
             */
            val fragments = ArrayList<Fragment?>()
            profileFragment = fm.findFragmentByTag(TAG_PROFILE)!!
            fragments.add(profileFragment)
            umkFragment = fm.findFragmentByTag(TAG_UMK)!!
            fragments.add(umkFragment)
            filesFragment = fm.findFragmentByTag(TAG_FILES)!!
            fragments.add(filesFragment)
            scheduleFragment = fm.findFragmentByTag(TAG_SCHEDULE)!!
            fragments.add(scheduleFragment)
            newsFragment = fm.findFragmentByTag(TAG_NEWS)!!
            fragments.add(newsFragment)

            fragments.forEach {
                it?.let {
                    if (!it.isHidden) {
                        activeFragment = it
                        supportActionBar!!.title = activeFragment.tag
                    }
                }
            }
            Log.i("fragments", "restored active fragment $activeFragment")
        }

    }

    /**
     * override nav bar listener
     */
    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            //TODO: add backstack class/handling
            when (item.itemId){
                R.id.navigation_news_main ->{
                    fm.beginTransaction().hide(activeFragment).show(newsFragment).commit()
                    activeFragment = newsFragment
                    supportActionBar!!.title = TAG_NEWS
                }

                R.id.navigation_dashboard ->{
                    fm.beginTransaction().hide(activeFragment).show(scheduleFragment).commit()
                    activeFragment = scheduleFragment
                    supportActionBar!!.title = TAG_SCHEDULE
                }

                R.id.navigation_files -> {
                    fm.beginTransaction().hide(activeFragment).show(filesFragment).commit()
                    activeFragment = filesFragment
                    supportActionBar!!.title = TAG_FILES
                }

                R.id.navigation_umk -> {
                    fm.beginTransaction().hide(activeFragment).show(umkFragment).commit()
                    activeFragment = umkFragment
                    supportActionBar!!.title = TAG_UMK
                }
            }
            true
        }

    /**
     * menu for profile button
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.action_bar_profile){
            fm.beginTransaction().hide(activeFragment).show(profileFragment).commit()
            activeFragment = profileFragment
            supportActionBar!!.title = TAG_PROFILE
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
