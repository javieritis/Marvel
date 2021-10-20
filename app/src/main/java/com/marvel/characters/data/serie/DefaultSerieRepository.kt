package com.marvel.characters.data.serie

import com.marvel.characters.domain.serie.SerieRepository
import javax.inject.Inject

class DefaultSerieRepository @Inject constructor(
    private val seriesService: SeriesService
) : SerieRepository {
    override suspend fun fetchSeries(characterId: Long): List<Serie>? {
        return seriesService.getSeriesByCharacterId(characterId).data.results
    }
}