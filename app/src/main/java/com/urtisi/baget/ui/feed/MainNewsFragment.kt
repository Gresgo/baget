package com.urtisi.baget.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.urtisi.baget.R
import com.urtisi.baget.databinding.FragmentNewsMainBinding
import com.urtisi.baget.ui.feed.site.FeedFragment
import com.urtisi.baget.ui.feed.vk.VKFragment

class MainNewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsMainBinding

    /**
     * fragment for news screen, contains two fragments for website and changes
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_news_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsPager.adapter =
            PagerAdapter(
                fragmentManager!!
            )
        binding.newsTab.setupWithViewPager(binding.newsPager)
    }

    class PagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        private val pageCount = 2
        private val titles = arrayOf("news", "changes")
        private val fragments = arrayOf(FeedFragment(), VKFragment())

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = pageCount

        override fun getPageTitle(position: Int): CharSequence? = titles[position]

    }
}

