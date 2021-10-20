package com.marvel.characters.domain.comic

import com.marvel.characters.data.comic.Comic
import javax.inject.Inject

class GetComicListUseCase @Inject constructor(
    private val comicRepository: ComicRepository
) {
    suspend operator fun invoke(
        characterId: Long
    ): List<Comic>? =
        comicRepository.fetchComics(characterId)
}