package com.example.ecotrack.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val CITY_KEY = stringPreferencesKey("city") // Новое поле

    val userName: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[USERNAME_KEY] ?: "Guest" }

    val city: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[CITY_KEY] ?: "" }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = name
        }
    }

    suspend fun saveCity(city: String) {
        context.dataStore.edit { prefs ->
            prefs[CITY_KEY] = city
        }
    }
}