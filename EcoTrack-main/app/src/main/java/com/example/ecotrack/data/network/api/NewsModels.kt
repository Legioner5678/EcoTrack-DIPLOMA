package com.example.ecotrack.data.network.api

data class NewsResponse(
    val status: String,
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?, // Добавляем поле для картинки
    val source: Source,
    val publishedAt: String // Понадобится для отображения времени
)

data class Source(
    val name: String
)