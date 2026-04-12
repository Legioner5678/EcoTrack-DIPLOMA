package com.example.ecotrack.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.core.content.edit

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs =
        application.getSharedPreferences("eco_prefs", Application.MODE_PRIVATE)

    fun resetProgress() {
        prefs.edit {
            putInt("habits_completed", 0)
        }
    }
}
