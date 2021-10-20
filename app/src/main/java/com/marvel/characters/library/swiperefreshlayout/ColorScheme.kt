package com.marvel.characters.library.swiperefreshlayout

import androidx.annotation.ColorInt
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.setColorSchemeColors(@ColorInt int: Int) {
    setColorSchemeColors(int, int, int)
}