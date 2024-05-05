package com.nikita_zayanchkovskij.newsapp.domain.use_cases.api.get_news_by_category


import com.nikita_zayanchkovskij.newsapp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.newsapp.common.constants.NewsAppConstants
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsApiRepository
import javax.inject.Inject


class GetNewsByCategoryUseCase @Inject constructor(private val repository: INewsApiRepository) {

    suspend fun getNewsByCategory(category: String): NetworkResponseManager<ArrayList<NewsItemToDisplay>> {
        val newsList = repository.getNewsByCategory(
            NewsAppConstants.COUNTRY_TO_GET_NEWS, category,
            NewsAppConstants.AMOUNT_OF_NEWS_PER_PAGE, NewsAppConstants.NEWS_API_KEY
        )
        return newsList
    }
}