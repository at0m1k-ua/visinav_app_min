package com.kpi.visinav_app_min

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import okhttp3.*

class ControlPanelActivity : ComponentActivity() {
    private lateinit var webSocket: WebSocket
    private var isWebSocketConnected = false
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ipAddress = intent.getStringExtra("IP_ADDRESS") ?: ""
        if (!isValidIpPort(ipAddress)) {
            navigateToInitialActivity("Invalid IP address or port. Please try again.")
            return
        }

        val webSocketUrl = "ws://$ipAddress/"
        initWebSocket(webSocketUrl)

        setContent {
            ControlPanelScreen(ipAddress)
        }
    }

    private fun initWebSocket(url: String) {
        val request = Request.Builder().url(url).build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "Connected to WebSocket at $url")
                isWebSocketConnected = true
                webSocket.send("Hello from client!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocket", "Message received: $text")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocket", "Closing WebSocket: $code $reason")
                webSocket.close(1000, null)
                isWebSocketConnected = false
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                val msg = "WebSocket error: ${t.message}"
                Log.e("WebSocket", msg)
                isWebSocketConnected = false
                navigateToInitialActivity(msg)
            }
        })
    }

    private fun navigateToInitialActivity(errorMessage: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val intent = Intent(this@ControlPanelActivity, InitialActivity::class.java).apply {
                putExtra("ERROR_MESSAGE", errorMessage)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::webSocket.isInitialized) {
            webSocket.close(1000, "Activity destroyed")
        }
    }

    private fun isValidIpPort(ipPort: String): Boolean {
        val regex = Regex("^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}):(\\d{1,5})$")
        return ipPort.matches(regex)
    }
}

@Composable
fun ControlPanelScreen(ipAddress: String) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewControlPanelScreen() {
    ControlPanelScreen(ipAddress = "192.168.1.1:8080")
}
