package com.urtisi.baget.ui.feed.site

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.urtisi.baget.R
import com.urtisi.baget.databinding.FragmentFeedBinding
import com.urtisi.baget.util.RSSModel

class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding
    private val rssViewAdapter =
        RSSAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        binding.viewModel = feedViewModel
        binding.executePendingBindings()

        /**
         * setting up adapter for rss news
         */
        binding.feedRecView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.feedRecView.adapter = rssViewAdapter

        /**
         * observing rss news list/data
         */
        feedViewModel.feedList.observe(this, Observer<ArrayList<RSSModel>>{
            it?.let { rssViewAdapter.replaceData(it) }
        })

        /**
         * refresh handling
         */
        binding.swipeRefreshLayout.setOnRefreshListener {
            feedViewModel.loadData()
            //binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }
}