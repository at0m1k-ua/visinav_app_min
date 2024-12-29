package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        BatteryIndicator(
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
