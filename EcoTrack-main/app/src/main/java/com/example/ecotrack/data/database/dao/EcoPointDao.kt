package com.example.ecotrack.data.database.dao

import androidx.room.*
import com.example.ecotrack.domain.model.EcoPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EcoPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: EcoPointEntity)

    @Query("SELECT * FROM eco_points ORDER BY date DESC")
    fun getAllHabits(): Flow<List<EcoPointEntity>>

    @Query("DELETE FROM eco_points")
    suspend fun clearAllHabits()

    @Delete
    suspend fun deleteHabit(habit: EcoPointEntity)

    @Query("SELECT SUM(points) FROM eco_points")
    fun getTotalPoints(): Flow<Int?>
}
