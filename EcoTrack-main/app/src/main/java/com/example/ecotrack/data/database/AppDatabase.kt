package com.example.ecotrack.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.domain.model.EcoPointEntity

@Database(entities = [EcoPointEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ecoPointDao(): EcoPointDao
}
