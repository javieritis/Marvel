package com.marvel.characters.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marvel.characters.data.character.Character
import com.marvel.characters.domain.character.GetCharacterListUseCase
import com.marvel.characters.library.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase
) : BaseViewModel() {

    private var characters: Flow<PagingData<Character>>? = null

    var orderTypeList: MutableLiveData<OrderType> =
        MutableLiveData<OrderType>(OrderType.NAME_ASCENDING)

    suspend fun fetchCharacters(update: Boolean = false): Flow<PagingData<Character>> {
        val response = getCharacterListUseCase.invoke(orderTypeList.value!!)
            .cachedIn(uiScope)
        if (update) {
            response.distinctUntilChanged()
        }
        characters = response
        return response
    }
}