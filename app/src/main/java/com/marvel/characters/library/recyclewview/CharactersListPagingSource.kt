package com.marvel.characters.library.recyclewview

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marvel.characters.data.character.Character
import com.marvel.characters.data.character.CharacterService
import com.marvel.characters.screens.home.OrderType

const val NETWORK_PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 0

class CharactersListPagingSource(
    private val characterService: CharacterService,
    private val orderTypeList: OrderType
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) (position * NETWORK_PAGE_SIZE) else INITIAL_LOAD_SIZE
        return try {
            val response = characterService.getCharacters(
                limit = params.loadSize,
                offset = offset,
                orderBy = orderTypeList.value
            ).data
            val list = response.results!!
            val nextKey = if (list.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = list,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e("", "", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return null
    }
}