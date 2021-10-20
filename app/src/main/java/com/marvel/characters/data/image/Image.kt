package com.marvel.characters.data.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Image(
    @SerialName("path") val path: String,
    @SerialName("extension") val extension: String
) : Parcelable {
    val imageUrl get() = "${path}.${extension}"
}