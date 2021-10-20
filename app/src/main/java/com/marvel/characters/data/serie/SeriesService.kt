package com.marvel.characters.data.serie

import com.marvel.characters.data.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {
    @GET("characters/{characterId}/series")
    suspend fun getSeriesByCharacterId(
        @Path("characterId") characterId: Long,
    ): BaseResponse<List<Serie>>
}