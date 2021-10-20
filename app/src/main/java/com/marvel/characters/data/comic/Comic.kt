package com.marvel.characters.data.comic

import android.os.Parcelable
import com.marvel.characters.data.base.MediaItem
import com.marvel.characters.data.image.Image
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Comic(
    @SerialName("id") override val id: Long,
    @SerialName("title") override val title: String?,
    @SerialName("description") override val description: String?,
    @SerialName("thumbnail") override val thumbnail: Image?,
    @SerialName("images") val images: List<Image>?,
    @SerialName("isbn") val isbn: String?,
    @SerialName("pageCount") val pageCount: Int?,
) : Parcelable, MediaItem()