package com.marvel.characters.library.injector.modules

import com.marvel.characters.data.character.DefaultCharacterRepository
import com.marvel.characters.data.comic.DefaultComicRepository
import com.marvel.characters.data.serie.DefaultSerieRepository
import com.marvel.characters.domain.character.CharacterRepository
import com.marvel.characters.domain.comic.ComicRepository
import com.marvel.characters.domain.serie.SerieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindCharacterDataSource(defaultCharacterRepository: DefaultCharacterRepository): CharacterRepository

    @Singleton
    @Binds
    fun bindComicDataSource(defaultComicRepository: DefaultComicRepository): ComicRepository

    @Singleton
    @Binds
    fun bindSerieDataSource(defaultSerieRepository: DefaultSerieRepository): SerieRepository

}