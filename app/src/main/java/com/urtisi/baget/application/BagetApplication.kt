package com.urtisi.baget.application

import android.app.Application
import android.content.Context

class BagetApplication : Application() {

    private companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    fun getContext() : Context{
        return context!!
    }

}