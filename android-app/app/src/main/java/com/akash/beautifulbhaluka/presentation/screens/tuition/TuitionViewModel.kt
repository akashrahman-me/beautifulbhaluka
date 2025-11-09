package com.akash.beautifulbhaluka.presentation.screens.tuition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.TuitionRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.TuitionCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TuitionViewModel : ViewModel() {

    private val repository = TuitionRepositoryImpl()

    private val _uiState = MutableStateFlow(TuitionUiState())
    val uiState: StateFlow<TuitionUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onAction(action: TuitionAction) {
        when (action) {
            is TuitionAction.LoadData -> loadData()
            is TuitionAction.Refresh -> refresh()
            is TuitionAction.SelectCategory -> selectCategory(action.category)
            is TuitionAction.UpdateSearch -> updateSearch(action.query)
            is TuitionAction.ToggleFavorite -> toggleFavorite(action.tutorId)
            else -> {}
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val tutorsResult = repository.getTutors()
            val requestsResult = repository.getRequests()

            tutorsResult.onSuccess { tutors ->
                requestsResult.onSuccess { requests ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tutors = tutors,
                            requests = requests,
                            error = null
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            val tutorsResult = repository.getTutors()
            val requestsResult = repository.getRequests()

            tutorsResult.onSuccess { tutors ->
                requestsResult.onSuccess { requests ->
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            tutors = tutors,
                            requests = requests
                        )
                    }
                }
            }
        }
    }

    private fun selectCategory(category: TuitionCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    private fun updateSearch(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    private fun toggleFavorite(tutorId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(tutorId)
                .onSuccess {
                    val updatedTutors = _uiState.value.tutors.map { tutor ->
                        if (tutor.id == tutorId) {
                            tutor.copy(isFavorite = !tutor.isFavorite)
                        } else {
                            tutor
                        }
                    }
                    _uiState.update { it.copy(tutors = updatedTutors) }
                }
        }
    }
}

