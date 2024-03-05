package com.example.mvicomposeproject.ui

import androidx.lifecycle.viewModelScope
import com.example.mvicomposeproject.domain.use_case.GetGenderUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val getGenderUserUseCase: GetGenderUserUseCase
): BaseViewModel<GenderUiState, GenderEvent, GenderIntent>() {

    private fun observeGender(name: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getGenderUserUseCase(name).collect {
                it.onSuccess { genderUser ->
                    setState { copy(genderUser = genderUser, isLoading = false) }
                }.onFailure {
                    setState { copy(isLoading = false) }
                    setEvent { GenderEvent.ShowToast("Unexpected result") }
                }
            }
        }
    }

    override fun createInitialState(): GenderUiState {
        return GenderUiState()
    }

    override fun handleIntent(intent: GenderIntent) {
        when (intent) {
            is GenderIntent.SearchGenderUser -> observeGender(intent.name)
        }
    }
}