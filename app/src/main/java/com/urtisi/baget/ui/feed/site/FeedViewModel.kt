package com.urtisi.baget.ui.feed.site


import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.urtisi.baget.ui.feed.site.OnRSSReadyCallback
import com.urtisi.baget.util.RSSModel
import com.urtisi.baget.util.RSSParser

class FeedViewModel : ViewModel(),
    OnRSSReadyCallback {

    val feedList = MutableLiveData<ArrayList<RSSModel>>()
    var isRefreshing = ObservableField<Boolean>()
    //private val repository = FeedRepository()

    init {
        loadData()
    }

    /**
     * start getting data from website and receive it in interface
     */
    fun loadData(){
        isRefreshing.set(true)
        val rss = RSSParser(this)
        rss.execute()
    }

    override fun onDataReady(data: ArrayList<RSSModel>) {
        feedList.value = data
        isRefreshing.set(false)
    }
}