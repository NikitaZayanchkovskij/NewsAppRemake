package com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_lists_for_main_screen

import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay

data class NewsListState(
    val isLoading: Boolean = true,
    val newsList: ArrayList<NewsItemToDisplay> = arrayListOf(),
    val error: String? = null
)
