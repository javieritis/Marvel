package com.marvel.characters.data.comic

import com.marvel.characters.domain.comic.ComicRepository
import javax.inject.Inject

class DefaultComicRepository @Inject constructor(
    private val comicsService: ComicsService
) : ComicRepository {
    override suspend fun fetchComics(characterId: Long): List<Comic>? {
        return comicsService.getComicsByCharacterId(characterId).data.results
    }
}