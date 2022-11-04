package com.amit.assignment.presentation.boomarks

import androidx.lifecycle.ViewModel
import com.amit.assignment.data.source.local.entity.CharacterEntity
import com.amit.assignment.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookMarksViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    // Fetching characters list from DB
    fun loadBookMarkedCharacters(): List<CharacterEntity> {
        return getCharacterUseCase.getCharacterListFromDB()
    }
}
