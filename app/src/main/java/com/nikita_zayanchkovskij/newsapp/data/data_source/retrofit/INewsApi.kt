package com.nikita_zayanchkovskij.newsapp.data.data_source.retrofit


import com.nikita_zayanchkovskij.newsapp.data.repositories.dto.TopHeadlinesMainDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface INewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlinesNews(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") newsApiKey: String): Response<TopHeadlinesMainDto>

    @GET("top-headlines")
    suspend fun getNewsByCategory(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") newsApiKey: String): Response<TopHeadlinesMainDto>

}