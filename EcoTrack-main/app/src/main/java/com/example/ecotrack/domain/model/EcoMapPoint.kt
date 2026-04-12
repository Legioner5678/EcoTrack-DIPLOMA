package com.example.ecotrack.domain.model

import com.google.android.gms.maps.model.LatLng

data class EcoMapPoint(
    val id: Int,             // В Django ID — это число (Int)
    val name: String,
    val address: String,
    val latitude: Double,    // В Django это Float/Double
    val longitude: Double,
    val description: String
) {
    // Автоматически создаем позицию для карты из координат
    val position: LatLng get() = LatLng(latitude, longitude)
}