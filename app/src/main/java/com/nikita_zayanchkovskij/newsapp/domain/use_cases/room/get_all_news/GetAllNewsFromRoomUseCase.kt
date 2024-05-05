package com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.get_all_news


import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsRoomRepository
import javax.inject.Inject


class GetAllNewsFromRoomUseCase @Inject constructor(private val repository: INewsRoomRepository) {

    suspend fun getAllNewsFromRoom(): List<NewsItemToDisplay> {
        return repository.getAllNewsItemsFromRoom()
    }
}