package com.kpi.visinav_app_min.ui.components

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import io.socket.client.Socket
import org.json.JSONObject

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
