package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BatteryIndicator(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(Color.Gray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "42", color = Color.White, fontSize = 16.sp)
    }
}
