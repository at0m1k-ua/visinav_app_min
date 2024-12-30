package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import io.socket.client.Socket

@Composable
fun DroneControlScreen(telemetryData: Map<String, Any>?,
                       background: @Composable () -> Unit = {},
                       socket: Socket
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3B2E))
    ) {
        background()

        telemetryData?.let { data ->
            val coordinates = data["coordinates"] as? Map<String, Double> ?: emptyMap()
            val sensors = data["sensorsState"] as? Map<String, Any> ?: emptyMap()
            val battery = data["battery"] as? Int ?: 0
            val height = data["height"] as? Double ?: 0.0

            Coordinates(
                coordinatesData = coordinates,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )

            Sensors(
                sensorsData = sensors,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )

            BatteryIndicator(
                batteryLevel = battery,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
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
                socket
            )

            CamerasToggle(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                onCameraSelected = { camera ->
                    socket.emit("start_camera", Gson().toJson(
                        mapOf("camera_name" to camera)
                    ))
                }
            )

            MapWidget(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )

            HeightControl(
                height = height,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )
        }
    }
}
