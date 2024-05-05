package com.nikita_zayanchkovskij.newsapp.presentation.view_model.detailed_news_info


import androidx.lifecycle.ViewModel
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class DetailedNewsInfoViewModel: ViewModel() {
    private val _detailedNewsItem = MutableStateFlow<NewsItemToDisplay?>(null)
    val detailedNewsItem = _detailedNewsItem.asStateFlow()


    fun setValueToDetailedNewsItem(item: NewsItemToDisplay) {
        _detailedNewsItem.value = item
    }
}