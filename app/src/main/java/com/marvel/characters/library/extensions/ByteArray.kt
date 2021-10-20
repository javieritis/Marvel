package com.marvel.characters.library.extensions

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}