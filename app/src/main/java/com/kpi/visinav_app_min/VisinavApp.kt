package com.kpi.visinav_app_min

import android.app.Application
import org.osmdroid.config.Configuration
import java.io.File

class VisinavApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().userAgentValue = packageName
        Configuration.getInstance().osmdroidBasePath = File(cacheDir, "osmdroid")
        Configuration.getInstance().osmdroidTileCache = File(cacheDir, "osmdroid/cache")
    }
}
