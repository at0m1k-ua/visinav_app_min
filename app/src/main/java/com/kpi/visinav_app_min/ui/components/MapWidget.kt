package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapWidget(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF375D81)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "MAP", color = Color.White, fontSize = 14.sp)
        }
    }
}
