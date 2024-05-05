package com.nikita_zayanchkovskij.newsapp.domain.use_cases.room


import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.delete_news_item.DeleteNewsItemFromRoomUseCase
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.get_news_item_by_title.GetNewsItemFromRoomByTitleUseCase
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.get_all_news.GetAllNewsFromRoomUseCase
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.save_news_item.SaveNewsItemToRoomUseCase


/** Примечание, зачем сделал этот класс.
 * Вживляю его в конструктор класса NewsInBookmarksViewModel, чтобы обращаясь к одному классу у
 * меня был доступ к каждому use case-у.
 */
data class RoomNewsUseCases (
    val getAllNews: GetAllNewsFromRoomUseCase,
    val getNewsItemByTitle: GetNewsItemFromRoomByTitleUseCase,
    val saveNewsItem: SaveNewsItemToRoomUseCase,
    val deleteNewsItem: DeleteNewsItemFromRoomUseCase,
)