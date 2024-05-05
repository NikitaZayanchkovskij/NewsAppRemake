package com.nikita_zayanchkovskij.newsapp.data.data_source.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay


@Database(entities = [NewsItemToDisplay::class], version = 1)
abstract class NewsAppRoomDataBase: RoomDatabase() {
    abstract val dao: IDaoForRoom
}