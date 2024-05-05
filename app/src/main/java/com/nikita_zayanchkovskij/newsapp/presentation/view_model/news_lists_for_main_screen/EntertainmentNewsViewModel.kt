package com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_lists_for_main_screen


import androidx.lifecycle.ViewModel
import com.nikita_zayanchkovskij.newsapp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.newsapp.common.constants.NewsAppConstants
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.api.get_news_by_category.GetNewsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class EntertainmentNewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
): ViewModel() {

    private val _entertainmentNewsList = MutableStateFlow<NewsListState>(NewsListState())
    val entertainmentNewsList = _entertainmentNewsList.asStateFlow()


    suspend fun receiveEntertainmentNews() {
        val result = getNewsByCategoryUseCase.getNewsByCategory(NewsAppConstants.CATEGORY_ENTERTAINMENT)

        when (result) {

            is NetworkResponseManager.Success -> {
                _entertainmentNewsList.value = NewsListState(
                    newsList = result.data ?: arrayListOf(), isLoading = false
                )
            }

            is NetworkResponseManager.Error -> {
                _entertainmentNewsList.value = NewsListState(
                    error = result.message ?: "Something went wrong, an unexpected error occurred...",
                    isLoading = false
                )
            }
        }
    }
}