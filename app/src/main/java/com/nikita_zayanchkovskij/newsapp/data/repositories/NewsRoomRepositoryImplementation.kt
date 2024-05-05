package com.nikita_zayanchkovskij.newsapp.data.repositories


import com.nikita_zayanchkovskij.newsapp.data.data_source.room.IDaoForRoom
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsRoomRepository
import javax.inject.Inject


class NewsRoomRepositoryImplementation @Inject constructor(private val dao: IDaoForRoom): INewsRoomRepository {
    override suspend fun saveNewsItemToRoom(item: NewsItemToDisplay) {
        dao.saveNewsItemToRoom(item)
    }

    override suspend fun deleteNewsItemFromRoom(item: NewsItemToDisplay) {
        dao.deleteNewsItemFromRoom(item)
    }

    override suspend fun getAllNewsItemsFromRoom(): List<NewsItemToDisplay> {
        return dao.getAllNewsItemsFromRoom()
    }

    override suspend fun getNewsItemsFromRoomByTitle(title: String): NewsItemToDisplay? {
        return dao.getNewsItemsFromRoomByTitle(title)
    }


}