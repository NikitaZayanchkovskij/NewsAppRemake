package com.nikita_zayanchkovskij.newsapp.domain.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikita_zayanchkovskij.newsapp.common.constants.NewsAppConstants


@Entity(tableName = NewsAppConstants.ENTITY_ROOM_TABLE_NAME)
data class NewsItemToDisplay(

    @PrimaryKey(autoGenerate = true)
    var roomId: Int? = null,

    @ColumnInfo(name = "sourceId")
    val sourceId: String,

    @ColumnInfo(name = "sourceName")
    val sourceName: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "content")
    val content: String
)
