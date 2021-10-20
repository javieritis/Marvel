package com.marvel.characters.screens.characterdetails

import androidx.lifecycle.MutableLiveData
import com.marvel.characters.data.character.Character
import com.marvel.characters.data.comic.Comic
import com.marvel.characters.data.serie.Serie
import com.marvel.characters.domain.character.GetCharacterDetailsUseCase
import com.marvel.characters.domain.comic.GetComicListUseCase
import com.marvel.characters.domain.serie.GetSeriesListUseCase
import com.marvel.characters.library.ActivityNavigator
import com.marvel.characters.library.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getComicListUseCase: GetComicListUseCase,
    private val getSeriesListUseCase: GetSeriesListUseCase,
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val activityNavigator: ActivityNavigator
) : BaseViewModel() {

    val character = MutableLiveData<Character>()
    val comics = MutableLiveData<List<Comic>?>()
    val series = MutableLiveData<List<Serie>?>()

    suspend fun fetchCharacter() {
        character.value?.let {
            runCatching {
                getCharacterDetailsUseCase(characterId = it.id).also { character ->
                    this@CharacterDetailsViewModel.character.value = character
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun fetchComics() {
        uiScope.launch {
            runCatching {
                getComicListUseCase(characterId = character.value?.id!!)
            }.onSuccess {
                comics.value = it
            }.onFailure {
                comics.value = null
                it.printStackTrace()
            }
        }
    }

    fun fetchSeries() {
        uiScope.launch {
            runCatching {
                getSeriesListUseCase(characterId = character.value?.id!!)
            }.onSuccess {
                series.value = it
            }.onFailure {
                series.value = null
                it.printStackTrace()
            }
        }
    }

    fun openImageDetails() {
        character.value?.let {
            activityNavigator.openImageDetails(it.thumbnail.imageUrl)
        }
    }
}