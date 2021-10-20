package com.marvel.characters.domain.comic

import com.marvel.characters.data.comic.Comic

interface ComicRepository {
    suspend fun fetchComics(
        characterId: Long
    ): List<Comic>?
}