package com.marvel.characters.domain.serie

import com.marvel.characters.data.serie.Serie

interface SerieRepository {
    suspend fun fetchSeries(
        characterId: Long
    ): List<Serie>?
}