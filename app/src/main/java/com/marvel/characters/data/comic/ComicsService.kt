package com.marvel.characters.data.comic

import com.marvel.characters.data.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsService {
    @GET("characters/{characterId}/comics")
    suspend fun getComicsByCharacterId(
        @Path("characterId") characterId: Long,
    ): BaseResponse<List<Comic>>
}