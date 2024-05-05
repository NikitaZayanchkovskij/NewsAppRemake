package com.nikita_zayanchkovskij.newsapp.dependency_injection


import android.app.Application
import androidx.room.Room
import com.nikita_zayanchkovskij.newsapp.common.constants.NewsAppConstants
import com.nikita_zayanchkovskij.newsapp.data.data_source.retrofit.INewsApi
import com.nikita_zayanchkovskij.newsapp.data.repositories.NewsApiRepositoryImplementation
import com.nikita_zayanchkovskij.newsapp.data.data_source.retrofit.NewsAppRetrofit
import com.nikita_zayanchkovskij.newsapp.data.data_source.room.NewsAppRoomDataBase
import com.nikita_zayanchkovskij.newsapp.data.repositories.NewsRoomRepositoryImplementation
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsApiRepository
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsRoomRepository
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.RoomNewsUseCases
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.delete_news_item.DeleteNewsItemFromRoomUseCase
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.get_news_item_by_title.GetNewsItemFromRoomByTitleUseCase
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.get_all_news.GetAllNewsFromRoomUseCase
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.room.save_news_item.SaveNewsItemToRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DIAppModule {

    @Provides
    @Singleton
    fun provideNewsRepository(api: INewsApi): INewsApiRepository {
        return NewsApiRepositoryImplementation(api)
    }

    @Provides
    @Singleton
    fun provideNewsApi(): INewsApi {
        val newsApi = NewsAppRetrofit.initRetrofit(NewsAppConstants.NEWS_API_BASE_URL)
        return newsApi
    }

    @Provides
    @Singleton
    fun provideNewsAppRoomDataBase(app: Application): NewsAppRoomDataBase {
        return Room.databaseBuilder(
            app, NewsAppRoomDataBase::class.java,
            NewsAppConstants.ROOM_DATABASE_TABLE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideNewsRoomRepository(dataBase: NewsAppRoomDataBase): INewsRoomRepository {
        return NewsRoomRepositoryImplementation(dataBase.dao)
    }

    @Provides
    @Singleton
    fun provideNewsRoomUseCases(repository: INewsRoomRepository): RoomNewsUseCases {
        return RoomNewsUseCases(
            getAllNews = GetAllNewsFromRoomUseCase(repository),
            getNewsItemByTitle = GetNewsItemFromRoomByTitleUseCase(repository),
            saveNewsItem = SaveNewsItemToRoomUseCase(repository),
            deleteNewsItem = DeleteNewsItemFromRoomUseCase(repository)
        )
    }
}