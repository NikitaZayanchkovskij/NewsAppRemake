package com.nikita_zayanchkovskij.newsapp.domain.use_cases.api.get_top_headlines_news


import com.nikita_zayanchkovskij.newsapp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.newsapp.common.constants.NewsAppConstants
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsApiRepository
import javax.inject.Inject


class GetTopHeadlinesNewsUseCase @Inject constructor(private val repository: INewsApiRepository) {

    suspend fun getTopHeadlinesNews(): NetworkResponseManager<ArrayList<NewsItemToDisplay>> {
        val newsList = repository.getTopHeadlinesNews(
            NewsAppConstants.COUNTRY_TO_GET_NEWS,
            NewsAppConstants.AMOUNT_OF_NEWS_PER_PAGE, NewsAppConstants.NEWS_API_KEY
        )
        return newsList
    }
}