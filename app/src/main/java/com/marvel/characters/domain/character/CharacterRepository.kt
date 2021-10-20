package com.marvel.characters.domain.character

import androidx.paging.PagingData
import com.marvel.characters.data.character.Character
import com.marvel.characters.screens.home.OrderType
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun fetchCharacterList(
        orderTypeList: OrderType
    ): Flow<PagingData<Character>>

    suspend fun getCharacterDetails(id: Long): Character?
}