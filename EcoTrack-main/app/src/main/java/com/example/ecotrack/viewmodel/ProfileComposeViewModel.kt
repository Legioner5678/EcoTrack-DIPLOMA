package com.example.ecotrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecotrack.data.database.dao.EcoPointDao
import com.example.ecotrack.domain.model.EcoPointEntity
import com.example.ecotrack.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileComposeViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val ecoPointDao: EcoPointDao
) : ViewModel() {

    val userName: StateFlow<String> = userPreferences.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Guest")

    val city: StateFlow<String> = userPreferences.city
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val habits: StateFlow<List<EcoPointEntity>> = ecoPointDao.getAllHabits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ecoPoints: StateFlow<Int> = ecoPointDao.getTotalPoints()
        .map { it ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun saveName(name: String) {
        viewModelScope.launch { userPreferences.saveUserName(name) }
    }

    fun saveCity(city: String) {
        viewModelScope.launch { userPreferences.saveCity(city) }
    }

    fun deleteHabit(habit: EcoPointEntity) {
        viewModelScope.launch { ecoPointDao.deleteHabit(habit) }
    }

    fun resetHabits() {
        viewModelScope.launch { ecoPointDao.clearAllHabits() }
    }
}