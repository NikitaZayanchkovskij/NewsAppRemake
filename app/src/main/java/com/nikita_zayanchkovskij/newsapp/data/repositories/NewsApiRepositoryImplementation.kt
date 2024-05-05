package com.nikita_zayanchkovskij.newsapp.data.repositories


import com.nikita_zayanchkovskij.newsapp.common.NetworkResponseManager
import com.nikita_zayanchkovskij.newsapp.data.data_source.retrofit.INewsApi
import com.nikita_zayanchkovskij.newsapp.data.repositories.dto.ArticlesDto
import com.nikita_zayanchkovskij.newsapp.data.repositories.dto.SourceDto
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay
import com.nikita_zayanchkovskij.newsapp.domain.repositories.INewsApiRepository
import com.nikita_zayanchkovskij.newsapp.domain.use_cases.convert_dto_to_model.ConvertArticlesDtoToNewsItemToDisplayUseCase
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class NewsApiRepositoryImplementation @Inject constructor(private val api: INewsApi): INewsApiRepository {


    override suspend fun getTopHeadlinesNews(country: String,
        pageSize: Int, newsApiKey: String): NetworkResponseManager<ArrayList<NewsItemToDisplay>> {

        val newsListForTheMainScreen = arrayListOf<NewsItemToDisplay>()

        try {
            val responseFromNewsApi = api.getTopHeadlinesNews(country, pageSize, newsApiKey)

            if (responseFromNewsApi.isSuccessful) {
                val newsList = responseFromNewsApi.body()!!.articles

                /* С NewsApi почему-то некоторые новости приходят у которых title [Removed],
                 * name [Removed] и description [Removed]. Тогда на экране в списке новостей
                 * показывается новость с тремя надписями [Removed] - мне так не нужно.
                 * Проверяю если пришло что-то из этого, то такую новость не буду добавлять в список.
                 * И так же во всех запросах по категориям.
                 */
                for (item in newsList) {
                    if (item.title == "[Removed]" || item.source.name == "[Removed]") {
                        continue
                    }
                    val temporaryNewsItem = checkAllNewsItemParametersForNull(item)
                    val newsItemForMainScreen = ConvertArticlesDtoToNewsItemToDisplayUseCase
                        .convertItem(temporaryNewsItem)

                    newsListForTheMainScreen.add(newsItemForMainScreen)
                }
            }
            else {
                throw HttpException(responseFromNewsApi)
            }

            return NetworkResponseManager.Success<ArrayList<NewsItemToDisplay>>(newsListForTheMainScreen)

        } catch (error: HttpException) {
            val errorMessage = error.response()?.errorBody()?.string()?.let {
                JSONObject(it).getString("message")
            }
            return NetworkResponseManager.Error<ArrayList<NewsItemToDisplay>>(
                "${error.message}:\n$errorMessage"
            )

        } catch (error: IOException) {
            return NetworkResponseManager.Error<ArrayList<NewsItemToDisplay>>(
                "${error.message}.\n Please, check your internet connection."
            )
        }
    }


    override suspend fun getNewsByCategory(country: String, category: String,
        pageSize: Int, newsApiKey: String): NetworkResponseManager<ArrayList<NewsItemToDisplay>> {

        val newsListForTheMainScreen = arrayListOf<NewsItemToDisplay>()

        try {
            val responseFromNewsApi = api.getNewsByCategory(country, category, pageSize, newsApiKey)

            if (responseFromNewsApi.isSuccessful) {
                val newsList = responseFromNewsApi.body()!!.articles

                for (item in newsList) {
                    if (item.title == "[Removed]" || item.source.name == "[Removed]") {
                        continue
                    }
                    val temporaryNewsItem = checkAllNewsItemParametersForNull(item)
                    val newsItemForMainScreen = ConvertArticlesDtoToNewsItemToDisplayUseCase
                        .convertItem(temporaryNewsItem)

                    newsListForTheMainScreen.add(newsItemForMainScreen)
                }
            } else {
                throw HttpException(responseFromNewsApi)
            }

            return NetworkResponseManager.Success<ArrayList<NewsItemToDisplay>>(newsListForTheMainScreen)

        } catch (error: HttpException) {
            val errorMessage = error.response()?.errorBody()?.string()?.let {
                JSONObject(it).getString("message")
            }
            return NetworkResponseManager.Error<ArrayList<NewsItemToDisplay>>(
                "${error.message}:\n$errorMessage"
            )

        } catch (error: IOException) {
            return NetworkResponseManager.Error<ArrayList<NewsItemToDisplay>>(
                "${error.message}.\n Please, check your internet connection."
            )
        }
    }


    /** Пояснение, зачем нужна эта функция.
     *
     * Когда приходит новость с newsapi.org там почему-то у некоторых параметров значения могут быть
     * null, например articles.source.id может быть null и/или articles.author может быть null,
     * и/или articles.description может быть null и т.д.
     *
     * Причём у каждого item-a по разному, у одного только author может быть null, а у другого
     * параметров 5 может быть null - поэтому проверяю всё таким образом и сохраняю.
     * Иначе если у какой-то новости один из параметров будет null - то эту новость не даёт сохранить
     * в закладки Room и приложение закрывается с ошибкой.
     */
    private fun checkAllNewsItemParametersForNull(detailedNewsItem: ArticlesDto): ArticlesDto {
        val temporarySourceItem = SourceDto(
            detailedNewsItem.source.id ?: detailedNewsItem.source.name,
            detailedNewsItem.source.name ?: ""
        )
        val temporaryNewsItem = ArticlesDto(
            temporarySourceItem,
            detailedNewsItem.author ?: "",
            detailedNewsItem.title ?: "",
            detailedNewsItem.description ?: "",
            detailedNewsItem.url ?: "",
            detailedNewsItem.urlToImage ?: "",
            detailedNewsItem.publishedAt ?: "",
            detailedNewsItem.content ?: ""
        )
        return temporaryNewsItem
    }
}