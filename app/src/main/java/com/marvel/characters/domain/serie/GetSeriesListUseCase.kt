package com.marvel.characters.domain.serie

import com.marvel.characters.data.serie.Serie
import javax.inject.Inject

class GetSeriesListUseCase @Inject constructor(
    private val serieRepository: SerieRepository
) {
    suspend operator fun invoke(
        characterId: Long
    ): List<Serie>? =
        serieRepository.fetchSeries(characterId)
}