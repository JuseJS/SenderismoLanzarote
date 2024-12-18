package org.iesharia.senderismolanzarote

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration
import java.io.File

@HiltAndroidApp
class SenderismoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().let { config ->
            config.userAgentValue = packageName
            config.osmdroidBasePath = File(cacheDir, "osmdroid")
            config.osmdroidTileCache = File(config.osmdroidBasePath, "tiles")
        }
    }
}