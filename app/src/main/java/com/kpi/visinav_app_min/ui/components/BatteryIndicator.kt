package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import com.kpi.visinav_app_min.R

@Composable
fun BatteryIndicator(modifier: Modifier) {
    Box(
        modifier = modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.battery),
            contentDescription = "Battery Icon"
        )
        Text(text = "42", color = Color.Black, fontSize = 16.sp)
    }
}
