package com.example.ecotrack.ui.habits

import androidx.lifecycle.ViewModel
import com.example.ecotrack.data.local.HabitProgressRepository
import com.example.ecotrack.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: HabitProgressRepository
) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    init {
        loadHabits()
    }

    private fun loadHabits() {
        val list = listOf(
            Habit(1, "Своя кружка", "Кофе в свой стакан", isCompletedToday = repository.getHabitStatus(1)),
            Habit(2, "Эко-сумка", "Поход в магазин без пакета", isCompletedToday = repository.getHabitStatus(2)),
            Habit(3, "Сортировка", "Сдал пластик на переработку", isCompletedToday = repository.getHabitStatus(3))
        )
        _habits.value = list
    }

    fun toggleHabit(id: Int) {
        val currentList = _habits.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            val habit = currentList[index]
            val newStatus = !habit.isCompletedToday
            repository.saveHabitStatus(id, newStatus)
            currentList[index] = habit.copy(isCompletedToday = newStatus)
            _habits.value = currentList
        }
    }
}