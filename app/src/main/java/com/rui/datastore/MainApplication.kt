package com.rui.datastore

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
    }

}