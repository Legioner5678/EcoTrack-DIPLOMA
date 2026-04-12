package com.example.ecotrack.data.network.api

import com.example.ecotrack.domain.model.EcoMapPoint
import retrofit2.http.GET

interface EcoBackendApiService {
    // Наш эндпоинт из Django
    @GET("api/points/")
    suspend fun getEcoPoints(): List<EcoMapPoint>

    // Сюда в будущем добавим:
    // @GET("api/habits/") suspend fun getHabits(): List<Habit>
}