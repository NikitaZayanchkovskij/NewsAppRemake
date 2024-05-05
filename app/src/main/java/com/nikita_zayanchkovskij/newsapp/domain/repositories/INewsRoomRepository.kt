package com.nikita_zayanchkovskij.newsapp.domain.repositories

import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay

interface INewsRoomRepository {
    suspend fun saveNewsItemToRoom(item: NewsItemToDisplay)
    suspend fun deleteNewsItemFromRoom(item: NewsItemToDisplay)
    suspend fun getAllNewsItemsFromRoom(): List<NewsItemToDisplay>
    suspend fun getNewsItemsFromRoomByTitle(title: String): NewsItemToDisplay?
}