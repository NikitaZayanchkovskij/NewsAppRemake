package com.nikita_zayanchkovskij.newsapp.data.data_source.room


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nikita_zayanchkovskij.newsapp.common.constants.NewsAppConstants
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay


@Dao
interface IDaoForRoom {

    @Insert
    suspend fun saveNewsItemToRoom(item: NewsItemToDisplay)

    @Delete
    suspend fun deleteNewsItemFromRoom(item: NewsItemToDisplay)

    @Query("SELECT * FROM ${NewsAppConstants.ENTITY_ROOM_TABLE_NAME}")
    suspend fun getAllNewsItemsFromRoom(): List<NewsItemToDisplay>

    @Query("SELECT * FROM ${NewsAppConstants.ENTITY_ROOM_TABLE_NAME} WHERE title LIKE :title")
    suspend fun getNewsItemsFromRoomByTitle(title: String): NewsItemToDisplay?

}