package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeightControl(height: Double, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .background(color = Color.Black.copy(alpha = 0.5f))
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.arrow_up_float),
                contentDescription = "Height Up",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = height.toString() + "m", color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(id = android.R.drawable.arrow_down_float),
                contentDescription = "Height Down",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}