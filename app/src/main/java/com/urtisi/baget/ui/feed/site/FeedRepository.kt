package com.urtisi.baget.ui.feed.site

import com.urtisi.baget.util.RSSModel

interface OnRSSReadyCallback{
    fun onDataReady(data: ArrayList<RSSModel>)
}