package com.marvel.characters.screens.home

import androidx.annotation.StringRes
import com.marvel.characters.R

enum class OrderType(val value: String, val viewId: Int, @StringRes val resourceName: Int) {
    NAME_ASCENDING("name", 1, R.string.name_ascending),
    NAME_DESCENDING("-name", 2, R.string.name_descending),
    MODIFICATION_DATE_ASCENDING("modified", 3, R.string.date_modified_ascending),
    MODIFICATION_DATE_DESCENDING("-modified", 4, R.string.date_modified_descending)
}