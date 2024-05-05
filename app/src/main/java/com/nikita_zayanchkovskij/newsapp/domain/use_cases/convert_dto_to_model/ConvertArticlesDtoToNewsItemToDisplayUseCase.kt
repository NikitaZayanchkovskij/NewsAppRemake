package com.nikita_zayanchkovskij.newsapp.domain.use_cases.convert_dto_to_model


import com.nikita_zayanchkovskij.newsapp.data.repositories.dto.ArticlesDto
import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay


object ConvertArticlesDtoToNewsItemToDisplayUseCase {

    fun convertItem(item: ArticlesDto): NewsItemToDisplay {

        /* В параметрах null не будет т.к. я вызываю эту функцию в классе NewsRepositoryImplementation
         * только если responseFromNewsApi.isSuccessful, поэтому ставлю !!.
         */
        return NewsItemToDisplay(
            null,
            item.source.id!!, item.source.name!!, item.author!!, item.title!!,
            item.description!!, item.url!!, item.urlToImage!!, item.publishedAt!!, item.content!!
        )
    }
}