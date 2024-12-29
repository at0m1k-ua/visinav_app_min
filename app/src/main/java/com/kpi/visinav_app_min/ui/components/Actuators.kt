package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import io.socket.client.Socket

@Composable
fun Actuators(modifier: Modifier, socket: Socket) {
    val gson = Gson()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(2) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { column ->
                    val index = row * 3 + column + 1
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, shape = CircleShape)
                            .clickable {
                                val command = mapOf("actuator" to index)
                                val jsonCommand = gson.toJson(command)
                                socket.emit("actuator_command", jsonCommand)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "$index",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
