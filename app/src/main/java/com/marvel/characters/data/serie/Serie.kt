package com.marvel.characters.data.serie

import android.os.Parcelable
import com.marvel.characters.data.base.MediaItem
import com.marvel.characters.data.image.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Serie(
    @SerialName("id") override val id: Long,
    @SerialName("title") override val title: String?,
    @SerialName("description") override val description: String?,
    @SerialName("thumbnail") override val thumbnail: Image?,
    @SerialName("rating") val rating: String?
) : Parcelable, MediaItem()