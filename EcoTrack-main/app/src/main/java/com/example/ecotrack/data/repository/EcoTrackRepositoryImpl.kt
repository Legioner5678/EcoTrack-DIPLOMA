package com.example.ecotrack.data.repository

import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.network.api.EcoBackendApiService // Новый импорт
import com.example.ecotrack.domain.model.EcoPointEntity
import com.example.ecotrack.domain.model.EcoMapPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EcoTrackRepositoryImpl @Inject constructor(
    private val api: EcoBackendApiService, // ЗАМЕНИЛИ ТУТ
    private val dao: EcoPointDao
) {
    // Эта функция будет тянуть точки с твоего сервера
    suspend fun getRemoteEcoPoints(): List<EcoMapPoint> {
        return api.getEcoPoints()
    }

    // Твои старые функции для локальной БД (пусть будут)
    val allEcoPoints: Flow<List<EcoPointEntity>> = dao.getAllHabits()

    suspend fun addEcoPoint(point: EcoPointEntity) {
        dao.insert(point)
    }

    suspend fun deleteEcoPoint(point: EcoPointEntity) {
        dao.deleteHabit(point)
    }
}