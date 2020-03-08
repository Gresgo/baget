package com.urtisi.baget.util

import android.content.Context
import android.net.ConnectivityManager
import com.urtisi.baget.application.BagetApplication

class CheckConnection {

    fun isConnected() : Boolean {
        val connect = BagetApplication().getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connect.activeNetworkInfo

        if (info != null && info.isConnected) return true

        return false
    }

}