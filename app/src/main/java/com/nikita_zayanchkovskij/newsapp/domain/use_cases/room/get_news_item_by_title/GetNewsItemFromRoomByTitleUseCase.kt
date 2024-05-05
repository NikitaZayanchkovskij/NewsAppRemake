package com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.get_news_item_by_title


import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsRoomRepository
import javax.inject.Inject


class GetNewsItemFromRoomByTitleUseCase @Inject constructor(private val repository: INewsRoomRepository) {

    suspend fun getNewsItemFromRoomByTitle(title: String): NewsItemToDisplay? {
        return repository.getNewsItemsFromRoomByTitle(title)
    }
}