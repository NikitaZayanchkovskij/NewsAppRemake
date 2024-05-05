package com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_lists_for_main_screen


import androidx.lifecycle.ViewModel
import com.nikita_zayanchkovskij.newsapp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.api.get_top_headlines_news.GetTopHeadlinesNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class TopHeadlinesNewsViewModel @Inject constructor(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase
): ViewModel() {

    private val _topHeadlinesNewsList = MutableStateFlow<NewsListState>(NewsListState())
    val topHeadlinesNewsList = _topHeadlinesNewsList.asStateFlow()


    suspend fun receiveTopHeadlinesNews() {
        val result = getTopHeadlinesNewsUseCase.getTopHeadlinesNews()

        when (result) {

            is NetworkResponseManager.Success -> {
                _topHeadlinesNewsList.value = NewsListState(
                    newsList = result.data ?: arrayListOf(), isLoading = false
                )
            }

            is NetworkResponseManager.Error -> {
                _topHeadlinesNewsList.value = NewsListState(
                    error = result.message ?: "Something went wrong, an unexpected error occurred...",
                    isLoading = false
                )
            }
        }
    }
}