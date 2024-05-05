package com.nikita_zayanchkovskij.newsapp.presentation.view_model.news_in_bookmarks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.RoomNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsInBookmarksViewModel @Inject constructor(private val roomNewsUseCases: RoomNewsUseCases): ViewModel() {
    private val _newsInBookmarksFromRoom = MutableStateFlow<List<NewsItemToDisplay>>(emptyList())
    val newsInBookmarksFromRoom = _newsInBookmarksFromRoom.asStateFlow()


    fun getAllNewsFromRoom() = viewModelScope.launch {
        _newsInBookmarksFromRoom.value = roomNewsUseCases.getAllNews.getAllNewsFromRoom()
    }


    suspend fun getNewsItemFromRoomByTitle(title: String): NewsItemToDisplay? {
        return roomNewsUseCases.getNewsItemByTitle.getNewsItemFromRoomByTitle(title)
    }


    fun saveNewsItemToRoom(item: NewsItemToDisplay) = viewModelScope.launch {
        roomNewsUseCases.saveNewsItem.saveNewsItemToRoom(item)
    }


    fun deleteNewsItemFromRoom(item: NewsItemToDisplay) = viewModelScope.launch {
        roomNewsUseCases.deleteNewsItem.deleteNewsItemFromRoom(item)
    }
}