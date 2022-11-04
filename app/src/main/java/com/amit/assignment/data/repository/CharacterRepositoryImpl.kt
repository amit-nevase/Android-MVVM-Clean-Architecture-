package com.amit.assignment.data.repository

import com.amit.assignment.data.mapper.toEntity
import com.amit.assignment.data.source.local.AppDatabase
import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.data.source.remote.RetrofitService
import com.amit.assignment.domain.model.Character
import com.amit.assignment.domain.model.MarvelResponse
import com.amit.assignment.domain.repository.CharacterRepository
import io.reactivex.Single

// Character repository implementations
class CharacterRepositoryImpl(
    private val database: AppDatabase,
    private val retrofitService: RetrofitService
) : CharacterRepository {

    // Executing API request to get data from remote
    override fun getCharacterList(position: Int): Single<MarvelResponse> {
        return retrofitService.getCharacterList(position)
    }

    override fun deleteCharacter(character: Character) {
        database.characterDao.delete(character.toEntity())
    }

    override fun addCharacter(character: Character) {
        database.characterDao.insert(character.toEntity())
    }

    override fun isFavoriteCharacter(id: Long): Boolean {
        return database.characterDao.loadOneById(id) != null
    }

    // Fetching bookmarked characters from local DB
    override fun getCharacterListFromDB(): List<CharacterEntity> {
        return database.characterDao.loadAll()
    }
}
