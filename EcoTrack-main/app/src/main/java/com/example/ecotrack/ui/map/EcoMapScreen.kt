package com.example.ecotrack.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun EcoMapScreen(
    // Hilt сам создаст и подставит сюда вьюмодель
    viewModel: MapViewModel = hiltViewModel()
) {
    // Подписываемся на список точек из ViewModel
    val ecoPoints by viewModel.points.collectAsState()

    // Центр карты (КБТУ)
    val kbtu = LatLng(43.2551, 76.9429)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(kbtu, 13f)
    }

    // Включаем отображение геолокации и кнопку "где я"
    val uiSettings = remember { MapUiSettings(myLocationButtonEnabled = true) }
    val properties = remember { MapProperties(isMyLocationEnabled = true) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        // Отрисовка точек, которые ViewModel получила от Django
        ecoPoints.forEach { point ->
            Marker(
                state = MarkerState(position = LatLng(point.latitude, point.longitude)),
                title = point.name,
                snippet = point.address
            )
        }
    }
}