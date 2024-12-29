package com.kpi.visinav_app_min

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.gson.Gson
import com.kpi.visinav_app_min.ui.components.DroneControlScreen
import kotlinx.coroutines.*

class ControlPanelActivity : ComponentActivity() {
    private lateinit var socketManager: SocketManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ipAddress = intent.getStringExtra("IP_ADDRESS") ?: ""
        if (!isValidIpPort(ipAddress)) {
            navigateToInitialActivity("Invalid IP address or port. Please try again.")
            return
        }

        val webSocketUrl = "http://$ipAddress/"
        socketManager = SocketManager(webSocketUrl)
        initSocketManager()

        val display = windowManager.defaultDisplay
        val rotation = display.rotation
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        //socketManager.emit("start_camera", Gson().toJson(mapOf("camera_name" to "front_left")))

        setContent {
            DroneControlScreen()
        }
    }

    private fun initSocketManager() {
        socketManager.initializeSocket(
            onConnect = {
                socketManager.emit("hello", "Hello from Android client!")
            },
            onDisconnect = {
                Log.d("ControlPanelActivity", "Socket disconnected")
            },
            onError = { error ->
                navigateToInitialActivity(error)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        socketManager.disconnect()
    }

    private fun isValidIpPort(ipPort: String): Boolean {
        val regex = Regex("^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}):(\\d{1,5})$")
        return ipPort.matches(regex)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun navigateToInitialActivity(errorMessage: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val intent = Intent(this@ControlPanelActivity, InitialActivity::class.java).apply {
                putExtra("ERROR_MESSAGE", errorMessage)
            }
            startActivity(intent)
            finish()
        }
    }
}
