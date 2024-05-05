package com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.save_news_item


import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsRoomRepository
import javax.inject.Inject


class SaveNewsItemToRoomUseCase @Inject constructor(private val repository: INewsRoomRepository) {

    suspend fun saveNewsItemToRoom(item: NewsItemToDisplay) {
        repository.saveNewsItemToRoom(item)
    }
}