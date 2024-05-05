package com.nikita_zayanchkovskij.newsapp.data.repositories.dto


data class TopHeadlinesMainDto (
    val status: String,
    val totalResults: Int,
    val articles: List<ArticlesDto>
)
