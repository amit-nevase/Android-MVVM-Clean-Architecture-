package com.amit.assignment.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amit.assignment.domain.model.Character
import com.amit.assignment.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    val isFavorite = MutableLiveData<Boolean>()

    // update character bookmark status
    fun updateFavoriteStatus(character: Character) {
        if (isFavorite.value == true) {
            getCharacterUseCase.deleteAsFavorite(character)
        } else {
            getCharacterUseCase.addAsFavorite(character)
        }
        isFavorite.value?.let {
            isFavorite.value = !it
        }
    }

    // Checking current selected character bookmarked or not
    fun checkFavoriteStatus(characterId: Int) {
        isFavorite.value = getCharacterUseCase.isFavorite(characterId.toLong())
    }
}
