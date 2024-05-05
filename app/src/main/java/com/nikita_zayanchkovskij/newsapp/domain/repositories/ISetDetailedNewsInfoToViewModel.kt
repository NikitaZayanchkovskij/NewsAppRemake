package com.nikita_zayanchkovskij.newsapp.domain.repositories

import com.nikita_zayanchkovskij.newsapp.domain.models.NewsItemToDisplay

/** Запускается при нажатии на item новости в AdapterNewsListForMainScreen, а что конкретно
 * происходит пишу уже в конкретном фрагменте, где использую адаптер.
 */
interface ISetDetailedNewsInfoToViewModel {
    fun setDetailedNewsInfoToViewModel(item: NewsItemToDisplay)
}