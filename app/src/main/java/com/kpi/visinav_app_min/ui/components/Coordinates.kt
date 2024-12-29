package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun Coordinates(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Lat: 50.450079", color = Color.White, fontSize = 14.sp)
        Text(text = "Lon: 30.4533602", color = Color.White, fontSize = 14.sp)
    }
}
