package com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.delete_news_item


import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsRoomRepository
import javax.inject.Inject


class DeleteNewsItemFromRoomUseCase @Inject constructor(private val repository: INewsRoomRepository) {

    suspend fun deleteNewsItemFromRoom(item: NewsItemToDisplay) {
        repository.deleteNewsItemFromRoom(item)
    }
}