package com.kpi.visinav_app_min.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kpi.visinav_app_min.R

@Composable
fun LandTakeoffButton(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.land_takeoff),
                contentDescription = "Land/Takeoff Button",
                tint = Color.White,
                modifier = Modifier.size(72.dp)
            )
        }
    }
}
