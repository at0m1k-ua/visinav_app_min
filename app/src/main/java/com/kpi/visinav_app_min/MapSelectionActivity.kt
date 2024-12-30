package com.kpi.visinav_app_min

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

class MapSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapSelectionScreen(
                onPointSelected = { lat, lon ->
                    val resultIntent = Intent().apply {
                        putExtra("lat", lat)
                        putExtra("lon", lon)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun MapSelectionScreen(onPointSelected: (Double, Double) -> Unit) {
    val selectedPoint = remember { mutableStateOf<GeoPoint?>(null) }
    val mapViewState = remember { mutableStateOf<MapView?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                createMapView(ctx, selectedPoint).also {
                    mapViewState.value = it
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FloatingActionButton(onClick = {
                mapViewState.value?.controller?.zoomIn()
            }) {
                Text("+")
            }
            FloatingActionButton(
                onClick = { mapViewState.value?.controller?.zoomOut() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("-")
            }
        }

        Button(
            onClick = {
                val point = selectedPoint.value
                if (point != null) {
                    onPointSelected(point.latitude, point.longitude)
                } else {
                    println("Точка не выбрана!")
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            enabled = selectedPoint.value != null
        ) {
            Text("Обрати")
        }
    }
}

fun createMapView(context: Context, selectedPoint: MutableState<GeoPoint?>): MapView {
    val mapView = MapView(context)
    mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
    mapView.controller.setZoom(15.0)
    mapView.controller.setCenter(GeoPoint(50.440496, 30.549744))
    mapView.setBuiltInZoomControls(false) // Отключаем встроенные кнопки масштаба
    mapView.setMultiTouchControls(true)

    val mapEventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
        override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
            if (p != null) {
                selectedPoint.value = p
                mapView.overlays.removeAll { it is Marker }
                val marker = Marker(mapView).apply {
                    position = p
                    title = "Обрана точка"
                }
                mapView.overlays.add(marker)
            }
            return true
        }

        override fun longPressHelper(p: GeoPoint?): Boolean {
            return false
        }
    })
    mapView.overlays.add(mapEventsOverlay)

    return mapView
}
