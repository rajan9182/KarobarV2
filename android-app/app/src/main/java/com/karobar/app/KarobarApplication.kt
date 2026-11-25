package com.karobar.app

import android.app.Application
import com.karobar.app.security.SecurityUtils

class KarobarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Run initial integrity check on startup
        SecurityUtils.checkDeviceSecurity(this)
    }
}
