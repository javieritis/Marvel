package com.marvel.characters.data.character

import android.os.Parcelable
import com.marvel.characters.data.image.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Character(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("thumbnail") val thumbnail: Image,
    @SerialName("comics") var comics: AvailableModel,
    @SerialName("series") var series: AvailableModel,
) : Parcelable {
    val hasComics: Boolean
        get() {
            return comics.available > 0
        }

    val hasSeries: Boolean
        get() {
            return series.available > 0
        }
}

@Parcelize
@Serializable
data class AvailableModel(
    @SerialName("available") val available: Long
) : Parcelable