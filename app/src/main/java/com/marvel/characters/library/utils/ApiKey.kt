package com.marvel.characters.library.utils

import com.marvel.characters.BuildConfig
import com.marvel.characters.library.extensions.toMD5

fun generateApiKey(timeStamp: Long): String {
   return "$timeStamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".toMD5()
}