package com.akash.beautifulbhaluka.presentation.screens.tuition.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.TuitionRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TuitionDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository = TuitionRepositoryImpl()
    private val tutorId: String = savedStateHandle.get<String>("tutorId") ?: ""

    private val _uiState = MutableStateFlow(TuitionDetailsUiState())
    val uiState: StateFlow<TuitionDetailsUiState> = _uiState.asStateFlow()

    init {
        loadDetails()
    }

    fun onAction(action: TuitionDetailsAction) {
        when (action) {
            is TuitionDetailsAction.LoadDetails -> loadDetails()
            is TuitionDetailsAction.ToggleFavorite -> toggleFavorite()
            else -> {}
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getTutorById(tutorId)
                .onSuccess { tutor ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tutor = tutor,
                            isFavorite = tutor.isFavorite,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            repository.toggleFavorite(tutorId)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isFavorite = !it.isFavorite,
                            tutor = it.tutor?.copy(isFavorite = !it.isFavorite)
                        )
                    }
                }
        }
    }
}

