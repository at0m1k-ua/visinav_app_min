package com.kpi.visinav_app_min

import android.util.Log
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket

class SocketManager(private val url: String) {
    private lateinit var socket: Socket

    fun initializeSocket(
        onConnect: () -> Unit = {},
        onDisconnect: () -> Unit = {},
        onError: (error: String) -> Unit = {}
    ) {
        try {
            socket = IO.socket(url)
            socket.on(Socket.EVENT_CONNECT) {
                Log.d("Socket.IO", "Connected to Socket.IO server")
                onConnect()
            }
            socket.on(Socket.EVENT_DISCONNECT) {
                Log.d("Socket.IO", "Disconnected from Socket.IO server")
                onDisconnect()
            }
            socket.on(Manager.EVENT_ERROR) { args ->
                val errorMessage = args.firstOrNull()?.toString() ?: "Unknown error"
                Log.e("Socket.IO", "Socket.IO error: $errorMessage")
                onError(errorMessage)
            }
            socket.connect()
        } catch (e: Exception) {
            Log.e("Socket.IO", "Error initializing Socket.IO", e)
            onError("Failed to connect to Socket.IO server")
        }
    }

    fun emit(event: String, data: String) {
        if (::socket.isInitialized && socket.connected()) {
            socket.emit(event, data)
        }
    }

    fun disconnect() {
        if (::socket.isInitialized) {
            socket.disconnect()
        }
    }
}

