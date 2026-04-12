package com.example.ecotrack.ui.analytics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AnalyticsViewModel : ViewModel() {

    var state = mutableStateOf(
        AnalyticsState(
            totalCompleted = 42,
            averagePerDay = 3.5f,
            bestDay = "Monday"
        )
    )
        private set
}
