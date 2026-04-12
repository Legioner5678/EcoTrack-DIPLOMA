package com.example.ecotrack.data.network.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getEcoNews(
        // Более жесткий фильтр: ищем только важные темы
        @Query("q") query: String = "(ecology OR climate OR sustainability OR \"global warming\" OR \"green energy\")",
        // Искать только в заголовках и описании (убирает лишнее)
        @Query("searchIn") searchIn: String = "title,description",
        @Query("sortBy") sortBy: String = "relevancy", // Самые важные вперед
        @Query("pageSize") pageSize: Int = 40,
        @Query("apiKey") apiKey: String,
        @Header("User-Agent") userAgent: String = "EcoTrackApp"
    ): NewsResponse
}