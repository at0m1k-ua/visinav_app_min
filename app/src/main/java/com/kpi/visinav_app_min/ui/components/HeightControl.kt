package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.socket.client.Socket

@Composable
fun HeightControl(socket: Socket, height: Double, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .background(color = Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    socket.emit("button_press", "increase_height")
                }
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.arrow_up_float),
                    contentDescription = "Increase Height",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(text = height.toString() + "m", color = Color.White, fontSize = 16.sp)

            IconButton(
                onClick = {
                    socket.emit("button_press", "decrease_height")
                }
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.arrow_down_float),
                    contentDescription = "Decrease Height",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
