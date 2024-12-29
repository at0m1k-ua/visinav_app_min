package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun Sensors(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = "humidity: 70,1", color = Color.White, fontSize = 14.sp)
        Text(text = "brightness: 59,8", color = Color.White, fontSize = 14.sp)
        Text(text = "unitsCount: 1", color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "42", color = Color.White, fontSize = 16.sp)
        }
    }
}
