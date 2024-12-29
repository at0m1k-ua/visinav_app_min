package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun Sensors(sensorsData: Map<String, Any>, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = "humidity: 70,1", color = Color.White, fontSize = 14.sp)
        Text(text = "brightness: 59,8", color = Color.White, fontSize = 14.sp)
        Text(text = "unitsCount: 1", color = Color.White, fontSize = 14.sp)
    }
}
