package com.marvel.characters.data.base

import com.marvel.characters.data.image.Image

abstract class MediaItem {
    abstract val id: Long
    abstract val title: String?
    abstract val description: String?
    abstract val thumbnail: Image?
}