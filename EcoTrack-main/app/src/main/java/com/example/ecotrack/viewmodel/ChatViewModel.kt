package com.example.ecotrack.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.google.ai.client.generativeai.type.RequestOptions
import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    // Твой список сообщений
    val messages = mutableStateListOf<ChatMessage>()

    // Состояние загрузки (чтобы показывать индикатор)
    var isLoading by mutableStateOf(false)
        private set

    private val config = generationConfig {
        temperature = 0.7f
    }

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyCG_Qdf-Y0fn0mRPuglqD95cTmzSch1x18",
        generationConfig = generationConfig {
            temperature = 0.7f
        }
    )

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return

        // 1. Добавляем сообщение пользователя в список
        messages.add(ChatMessage(userMessage, true))
        isLoading = true

        viewModelScope.launch {
            try {
                // 2. Делаем запрос к ИИ
                val response = generativeModel.generateContent(userMessage)
                val botResponse = response.text ?: "I'm sorry, I couldn't process that."

                // 3. Добавляем ответ бота
                messages.add(ChatMessage(botResponse, false))
            } catch (e: Exception) {
                // Логируем ошибку для отладки
                Log.e("ECO_TRACK_DEBUG", "!!! AI ERROR !!!: ${e.localizedMessage}")
                e.printStackTrace()

                // Показываем ошибку пользователю в чате
                messages.add(ChatMessage("Error: ${e.localizedMessage}", false))
            } finally {
                isLoading = false
            }
        }
    }
}