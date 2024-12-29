package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Sensors(sensorsData: Map<String, Any>, modifier: Modifier) {
    Column(
        modifier = modifier
            .background(color = Color.Black.copy(alpha = 0.5f))
            .padding(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        for ((key, value) in sensorsData) {
            Text(text = "$key: $value", color = Color.White, fontSize = 14.sp)
        }
    }
}
