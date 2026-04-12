package com.example.ecotrack.di

import android.content.Context
import androidx.room.Room
import com.example.ecotrack.data.database.AppDatabase
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.data.network.api.EcoBackendApiService // Добавь этот импорт
import com.example.ecotrack.data.repository.EcoTrackRepositoryImpl // Добавь этот импорт
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "eco_track_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEcoPointDao(appDatabase: AppDatabase): EcoPointDao {
        return appDatabase.ecoPointDao()
    }

    // --- ДОБАВЬ ЭТОТ БЛОК ---
    @Provides
    @Singleton
    fun provideEcoTrackRepository(
        api: EcoBackendApiService, // Hilt возьмет это из NetworkModule
        dao: EcoPointDao           // Hilt возьмет это из функции выше
    ): EcoTrackRepositoryImpl {
        return EcoTrackRepositoryImpl(api, dao)
    }
}