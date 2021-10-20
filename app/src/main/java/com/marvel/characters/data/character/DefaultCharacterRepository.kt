package com.marvel.characters.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marvel.characters.data.base.BaseResponse
import com.marvel.characters.domain.character.CharacterRepository
import com.marvel.characters.library.recyclewview.CharactersListPagingSource
import com.marvel.characters.library.recyclewview.NETWORK_PAGE_SIZE
import com.marvel.characters.screens.home.OrderType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultCharacterRepository @Inject constructor(
    private val characterService: CharacterService
) : CharacterRepository {
    override suspend fun fetchCharacterList(
        orderTypeList: OrderType
    ): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = NETWORK_PAGE_SIZE,
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersListPagingSource(characterService, orderTypeList)
            }
        ).flow
    }

    override suspend fun getCharacterDetails(id: Long): BaseResponse<List<Character>> {
        return characterService.getCharacterDetails(
            characterId = id
        )
    }
}