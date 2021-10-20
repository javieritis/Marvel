package com.marvel.characters.screens.characterdetails

import androidx.lifecycle.MutableLiveData
import com.marvel.characters.data.character.Character
import com.marvel.characters.data.comic.Comic
import com.marvel.characters.data.serie.Serie
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
    private val activityNavigator: ActivityNavigator
) : BaseViewModel() {

    val character = MutableLiveData<Character>()
    val comics = MutableLiveData<List<Comic>?>()
    val series = MutableLiveData<List<Serie>?>()

    fun setCharacter(character: Character) {
        this.character.value = character
    }

    fun fetchComics() {
        uiScope.launch {
            val list = getComicListUseCase(characterId = character.value?.id!!)
            comics.value = list
        }
    }

    fun fetchSeries() {
        uiScope.launch {
            val list = getSeriesListUseCase(characterId = character.value?.id!!)
            series.value = list
        }
    }

    fun openImageDetails() {
        character.value?.let {
            activityNavigator.openImageDetails(it.thumbnail.imageUrl)
        }
    }
}