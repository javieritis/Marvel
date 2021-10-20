package com.marvel.characters.domain.character

import androidx.paging.PagingData
import com.marvel.characters.data.character.Character
import com.marvel.characters.screens.home.OrderType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(orderTypeList: OrderType): Flow<PagingData<Character>> =
        characterRepository.fetchCharacterList(orderTypeList)
}