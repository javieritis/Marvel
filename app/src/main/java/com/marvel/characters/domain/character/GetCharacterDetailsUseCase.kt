package com.marvel.characters.domain.character

import com.marvel.characters.data.character.Character
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(
        characterId: Long
    ): Character? =
        characterRepository.getCharacterDetails(characterId).data.results?.first()
}