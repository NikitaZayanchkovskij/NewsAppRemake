package com.nikita_zayanchkovskij.newsapp.data.repositories.dto


data class ArticlesDto (
    val source: SourceDto,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)
