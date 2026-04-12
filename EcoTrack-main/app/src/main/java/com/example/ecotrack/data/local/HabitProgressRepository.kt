package com.example.ecotrack.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Репозиторий для работы с локальным хранилищем (SharedPreferences).
 * Использует applicationContext для предотвращения утечек памяти.
 */
class HabitProgressRepository @Inject constructor(
    @ApplicationContext private val context: Context // Добавь эти аннотации здесь
) {

    // Используем Application Context, чтобы SharedPreferences был доступен везде
    private val appContext = context.applicationContext

    companion object {
        // Константы для SharedPreferences
        private const val PREFS_NAME = "EcoTrackPrefs"
        private const val KEY_USER_NAME = "user_name"
        private const val DEFAULT_USER_NAME = "Эко-Герой"
        private const val PREFIX_HABIT_STATUS = "habit_status_"
    }

    private val prefs: SharedPreferences = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // --- Функции для сохранения/загрузки имени пользователя ---

    /** Сохраняет имя пользователя. */
    fun saveUserName(name: String) {
        prefs.edit { putString(KEY_USER_NAME, name) }
    }

    /** Получает имя пользователя или значение по умолчанию. */
    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, DEFAULT_USER_NAME) ?: DEFAULT_USER_NAME
    }

    // --- Функции для сохранения/загрузки статуса привычек ---

    /** * Сохраняет статус выполнения привычки (true/false).
     * Ключ формируется как "habit_status_" + ID.
     */
    fun saveHabitStatus(habitId: Int, isDone: Boolean) {
        val key = PREFIX_HABIT_STATUS + habitId
        prefs.edit { putBoolean(key, isDone) }
    }

    /** * Получает сохраненный статус выполнения привычки.
     * @return true, если привычка была отмечена, иначе false.
     */
    fun getHabitStatus(habitId: Int): Boolean {
        val key = PREFIX_HABIT_STATUS + habitId
        // Если статус не найден (первый запуск), возвращаем false
        return prefs.getBoolean(key, false)
    }

    // --- Дополнительная функция для экрана Профиля ---

    /** * Сбрасывает весь прогресс выполнения привычек.
     * Используется на экране Профиля.
     */
    fun clearAllHabitProgress() {
        prefs.edit {

            // Удаляем все ключи, которые начинаются с префикса статуса привычки
            prefs.all.keys.forEach { key ->
                if (key.startsWith(PREFIX_HABIT_STATUS)) {
                    remove(key)
                }
            }
        }
    }
}