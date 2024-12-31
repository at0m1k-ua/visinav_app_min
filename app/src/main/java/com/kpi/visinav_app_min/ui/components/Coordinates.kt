package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Coordinates(coordinatesData: Map<String, Double>, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Column (
            modifier = modifier
                .background(color = Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            Text(text = "Lat: " + coordinatesData["lat"], color = Color.White, fontSize = 14.sp)
            Text(text = "Lon: " + coordinatesData["lon"], color = Color.White, fontSize = 14.sp)
        }
    }
}
