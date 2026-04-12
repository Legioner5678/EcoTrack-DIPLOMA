package com.example.ecotrack.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eco_points")
data class EcoPointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,        // Название привычки
    val points: Int = 1,      // Всегда +1 за привычку
    val date: Long = System.currentTimeMillis() // Когда сделана привычка
)
