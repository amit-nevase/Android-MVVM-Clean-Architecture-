package com.amit.assignment.domain.repository

import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.domain.model.Character
import com.amit.assignment.domain.model.MarvelResponse
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacterList(position: Int): Single<MarvelResponse>
    fun deleteCharacter(character: Character)
    fun addCharacter(character: Character)
    fun isFavoriteCharacter(id: Long): Boolean
    fun getCharacterListFromDB(): List<CharacterEntity>
}
