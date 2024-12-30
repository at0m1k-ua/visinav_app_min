package com.kpi.visinav_app_min

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kpi.visinav_app_min.ui.components.CameraStream
import com.kpi.visinav_app_min.ui.components.DroneControlScreen
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

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

        setContent {
            var telemetryData by remember { mutableStateOf<Map<String, Any>?>(null) }

            var selectedCoordinates by remember { mutableStateOf<Pair<Double, Double>?>(null) }
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val latitude = result.data?.getDoubleExtra("lat", 0.0) ?: 0.0
                    val longitude = result.data?.getDoubleExtra("lon", 0.0) ?: 0.0
                    selectedCoordinates = latitude to longitude
                }
            }

            DisposableEffect(Unit) {
                val socket = socketManager.socket

                socket.on("telemetry_data") { args ->
                    if (args.isNotEmpty()) {
                        val data = args[0] as JSONObject
                        Log.d("TelemetryData", "Received: $data")
                        telemetryData = data.toMap()
                    } else {
                        Log.d("TelemetryData", "No data received")
                    }
                }

                onDispose {
                    socket.off("telemetry_data")
                }
            }

            DroneControlScreen(
                telemetryData = telemetryData,
                background = {
                    CameraStream(socketManager.socket)
                },
                socket = socketManager.socket,
                onMapClick = {
                    launcher.launch(Intent(this, MapSelectionActivity::class.java))
                },
            )
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

private fun JSONObject.toMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    val keys = keys()
    while (keys.hasNext()) {
        val key = keys.next() as String
        val value = this[key]
        map[key] = when (value) {
            is JSONObject -> value.toMap()
            is JSONArray -> value.toList()
            else -> value ?: "null"
        }
    }
    return map
}

private fun JSONArray.toList(): List<Any> {
    val list = mutableListOf<Any>()
    for (i in 0 until length()) {
        val value = get(i)
        list.add(
            when (value) {
                is JSONObject -> value.toMap()
                is JSONArray -> value.toList()
                else -> value ?: "null"
            }
        )
    }
    return list
}

