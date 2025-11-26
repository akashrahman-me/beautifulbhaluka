package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.author

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.usecase.GetWritingsByAuthorUseCase
import com.akash.beautifulbhaluka.domain.usecase.ReactToWritingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorWritingsViewModel @Inject constructor(
    private val getWritingsByAuthorUseCase: GetWritingsByAuthorUseCase,
    private val reactToWritingUseCase: ReactToWritingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthorWritingsUiState())
    val uiState: StateFlow<AuthorWritingsUiState> = _uiState.asStateFlow()

    private val authorId: String = savedStateHandle.get<String>("authorId") ?: ""

    init {
        if (authorId.isNotEmpty()) {
            loadWritings(authorId)
        }
    }

    fun onAction(action: AuthorWritingsAction) {
        when (action) {
            is AuthorWritingsAction.LoadWritings -> loadWritings(action.authorId)
            is AuthorWritingsAction.Refresh -> refresh()
            is AuthorWritingsAction.NavigateToDetail -> {}
            is AuthorWritingsAction.ReactToWriting -> reactToWriting(
                action.writingId,
                action.reaction
            )
        }
    }

    private fun loadWritings(authorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getWritingsByAuthorUseCase(authorId)
                .onSuccess { writings ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            writings = writings,
                            authorName = writings.firstOrNull()?.authorName ?: "",
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load writings"
                        )
                    }
                }
        }
    }

    private fun refresh() {
        loadWritings(authorId)
    }

    private fun reactToWriting(writingId: String, reaction: Reaction) {
        viewModelScope.launch {
            reactToWritingUseCase(writingId, reaction)
                .onSuccess {
                    loadWritings(authorId)
                }
        }
    }
}

