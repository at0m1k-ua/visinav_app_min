package com.kpi.visinav_app_min

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.kpi.visinav_app_min.ui.components.Actuators
import com.kpi.visinav_app_min.ui.components.CamerasToggle
import com.kpi.visinav_app_min.ui.components.Coordinates
import com.kpi.visinav_app_min.ui.components.HeightControl
import com.kpi.visinav_app_min.ui.components.LandTakeoffButton
import com.kpi.visinav_app_min.ui.components.MapWidget
import com.kpi.visinav_app_min.ui.components.Sensors
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import kotlinx.coroutines.*
import org.json.JSONObject

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

@Composable
fun ControlPanelScreen(ipAddress: String, socket: Socket) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Control Panel")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Connected to IP:")
        Text(text = ipAddress)
        Spacer(modifier = Modifier.height(16.dp))
        CameraStream(socket)
    }
}

@Composable
fun CameraStream(socket: Socket) {
    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val bitmap = imageBytes?.let {
        BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Camera Stream",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Control Panel", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        }
    }

    DisposableEffect(Unit) {
        socket.on("camera_frame") { args ->
            if (args.isNotEmpty()) {
                val frame = (args[0] as JSONObject).get("frame") as? ByteArray
                Log.d("CameraStream", "Received frame size: ${frame?.size ?: 0}")
                imageBytes = frame
            }
        }

        onDispose {
            socket.off("camera_frame")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewControlPanelScreen() {
    val mockSocket = IO.socket("http://localhost:8080")
    ControlPanelScreen(ipAddress = "192.168.1.1:8080", socket = mockSocket)
}

@Composable
fun DroneControlScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3B2E))
    ) {
        Coordinates(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        Sensors(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd),
        )

        LandTakeoffButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp),
        )

        Actuators(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        )

        CamerasToggle(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        MapWidget(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        HeightControl(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDroneControlScreen() {
    DroneControlScreen()
}
