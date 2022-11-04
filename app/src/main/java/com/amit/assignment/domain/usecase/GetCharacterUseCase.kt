package com.amit.assignment.domain.usecase

import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.domain.model.Character
import com.amit.assignment.domain.model.MarvelResponse
import com.amit.assignment.domain.repository.CharacterRepository
import com.amit.assignment.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of (which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */

data class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) : SingleUseCase<MarvelResponse>() {
    private var position: Int = 0

    fun saveCharacterListPosition(pos: Int) {
        position = pos
    }

    override fun buildUseCaseSingle(): Single<MarvelResponse> {
        return repository.getCharacterList(position)
    }

    fun deleteAsFavorite(character: Character) {
        repository.deleteCharacter(character)
    }

    fun addAsFavorite(character: Character) {
        repository.addCharacter(character)
    }

    fun isFavorite(characterId: Long): Boolean {
        return repository.isFavoriteCharacter(characterId)
    }

    fun getCharacterListFromDB(): List<CharacterEntity> {
        return repository.getCharacterListFromDB()
    }
}
