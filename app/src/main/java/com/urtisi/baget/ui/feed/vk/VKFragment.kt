package com.urtisi.baget.ui.feed.vk

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.urtisi.baget.R
import com.urtisi.baget.databinding.FragmentVkBinding

class VKFragment : Fragment() {

    private lateinit var binding: FragmentVkBinding
    private val url = "https://vk.com/raspisanie_urtisi"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vk, container, false)
        binding.vkView.webViewClient = WebView()
//        binding.vkView.settings.javaScriptEnabled = true
        binding.vkView.loadUrl(url)

        return binding.root
    }

    private class WebView : WebViewClient(){

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, request: WebResourceRequest?): Boolean {
            view!!.loadUrl(request!!.url.toString())
            return true
        }

        @SuppressWarnings("deprecation")
        override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
            view!!.loadUrl(url)
            return true
        }
    }

}