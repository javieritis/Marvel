package com.marvel.characters.data.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResult<V>(
    @SerialName("results") val results: V?
)

@Serializable
data class BaseResponse<V>(
    @SerialName("data") val data: BaseResult<V>,
    @SerialName("code") val code: Int,
    @SerialName("status") val status: String
)