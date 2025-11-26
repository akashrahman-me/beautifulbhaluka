package com.akash.beautifulbhaluka.presentation.screens.bookbuddy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.WritingCategory
import com.akash.beautifulbhaluka.domain.usecase.GetWritingsUseCase
import com.akash.beautifulbhaluka.domain.usecase.ReactToWritingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookBuddyViewModel @Inject constructor(
    private val getWritingsUseCase: GetWritingsUseCase,
    private val reactToWritingUseCase: ReactToWritingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookBuddyUiState())
    val uiState: StateFlow<BookBuddyUiState> = _uiState.asStateFlow()

    init {
        loadWritings()
    }

    fun onAction(action: BookBuddyAction) {
        when (action) {
            is BookBuddyAction.LoadWritings -> loadWritings()
            is BookBuddyAction.Refresh -> refresh()
            is BookBuddyAction.SelectCategory -> selectCategory(action.category)
            is BookBuddyAction.SearchQueryChanged -> updateSearchQuery(action.query)
            is BookBuddyAction.ShowCreateDialog -> showCreateDialog()
            is BookBuddyAction.HideCreateDialog -> hideCreateDialog()
            is BookBuddyAction.NavigateToDetail -> {}
            is BookBuddyAction.NavigateToAuthor -> {}
            is BookBuddyAction.ReactToWriting -> reactToWriting(action.writingId, action.reaction)
        }
    }

    private fun loadWritings() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getWritingsUseCase(_uiState.value.selectedCategory)
                .onSuccess { writings ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            writings = writings,
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
        loadWritings()
    }

    private fun selectCategory(category: WritingCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        loadWritings()
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    private fun showCreateDialog() {
        _uiState.update { it.copy(showCreateDialog = true) }
    }

    private fun hideCreateDialog() {
        _uiState.update { it.copy(showCreateDialog = false) }
    }

    private fun reactToWriting(writingId: String, reaction: Reaction) {
        viewModelScope.launch {
            reactToWritingUseCase(writingId, reaction)
                .onSuccess {
                    loadWritings()
                }
        }
    }
}

