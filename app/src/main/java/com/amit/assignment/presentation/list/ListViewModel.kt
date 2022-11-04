package com.amit.assignment.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amit.assignment.domain.model.Character
import com.amit.assignment.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    val characterResponseLiveData = MutableLiveData<List<Character>?>()
    val errorResponseLiveData = MutableLiveData<String>()
    val isLoad = MutableLiveData<Boolean?>()
    var position = 0

    init {
        isLoad.value = null
    }

    fun setNextPosition(position: Int) {
        getCharacterUseCase.saveCharacterListPosition(position)
    }

    // Remote api request to fetch data from API
    fun loadCharacters() {
        getCharacterUseCase.execute(
            onSuccess = {
                isLoad.value = true
                characterResponseLiveData.value = it.data?.results
                position = it.data?.offset!! + 1
                setNextPosition(position)
            },
            onError = {
                errorResponseLiveData.value = "error"
            }
        )
    }
}
