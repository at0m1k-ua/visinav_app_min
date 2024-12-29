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
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import kotlinx.coroutines.*

class ControlPanelActivity : ComponentActivity() {
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ipAddress = intent.getStringExtra("IP_ADDRESS") ?: ""
        if (!isValidIpPort(ipAddress)) {
            navigateToInitialActivity("Invalid IP address or port. Please try again.")
            return
        }

        val webSocketUrl = "http://$ipAddress/"
        initSocket(webSocketUrl)

        val display = windowManager.defaultDisplay
        val rotation = display.rotation
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        socket.emit("start_camera", Gson().toJson(mapOf("camera_name" to "front_left")))

        setContent {
            DroneControlScreen()
        }
    }

    private fun initSocket(url: String) {
        try {
            socket = IO.socket(url)
            socket.on(Socket.EVENT_CONNECT) {
                Log.d("Socket.IO", "Connected to Socket.IO server")
                socket.emit("hello", "Hello from Android client!")
            }
            socket.on(Socket.EVENT_DISCONNECT) {
                Log.d("Socket.IO", "Disconnected from Socket.IO server")
            }
            socket.on(Manager.EVENT_ERROR) { args ->
                Log.e("Socket.IO", "Socket.IO error: ${args[0]}")
                navigateToInitialActivity("Socket.IO connection error")
            }
            socket.connect()
        } catch (e: Exception) {
            Log.e("Socket.IO", "Error initializing Socket.IO", e)
            navigateToInitialActivity("Failed to connect to Socket.IO server")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::socket.isInitialized) {
            socket.disconnect()
        }
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
