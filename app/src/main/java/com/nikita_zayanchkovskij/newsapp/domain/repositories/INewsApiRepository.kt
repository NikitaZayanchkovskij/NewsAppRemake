package com.nikita_zayanchkovskij.newsapp.domain.repositories


import com.nikita_zayanchkovskij.newsapp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay


interface INewsApiRepository {

    suspend fun getTopHeadlinesNews(
        country: String, pageSize: Int, newsApiKey: String): NetworkResponseManager<ArrayList<NewsItemToDisplay>>

    suspend fun getNewsByCategory(country: String,
        category: String, pageSize: Int, newsApiKey: String): NetworkResponseManager<ArrayList<NewsItemToDisplay>>
}