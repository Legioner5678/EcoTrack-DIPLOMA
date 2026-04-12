package com.example.ecotrack.domain.model

data class Habit(
    val id: Int,
    val title: String,
    val description: String,
    val lastCompleted: Long? = null,
    val isCompletedToday: Boolean = false
)