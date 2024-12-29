package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kpi.visinav_app_min.R

@Composable
fun CameraToggleButton(imageId: Int) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = imageId),
            contentDescription = "Camera Control",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun CamerasToggle(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CameraToggleButton(imageId = R.drawable.left_camera)
        CameraToggleButton(imageId = R.drawable.bottom_camera)
        CameraToggleButton(imageId = R.drawable.right_camera)
    }
}
