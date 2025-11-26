package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.author

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.WritingCategory
import com.akash.beautifulbhaluka.domain.usecase.DeleteWritingUseCase
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
    private val deleteWritingUseCase: DeleteWritingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthorWritingsUiState())
    val uiState: StateFlow<AuthorWritingsUiState> = _uiState.asStateFlow()

    private val authorId: String = savedStateHandle.get<String>("authorId") ?: ""
    private val currentUserId: String = "current_user_123" // TODO: Get from auth service

    init {
        if (authorId.isNotEmpty()) {
            checkOwnership()
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

            is AuthorWritingsAction.FilterByCategory -> filterByCategory(action.category)
            is AuthorWritingsAction.SortBy -> sortBy(action.sortType)
            is AuthorWritingsAction.FilterByStatus -> filterByStatus(action.status)
            is AuthorWritingsAction.DeleteWriting -> deleteWriting(action.writingId)
            is AuthorWritingsAction.ShowDeleteDialog -> showDeleteDialog(action.writingId)
            is AuthorWritingsAction.DismissDeleteDialog -> dismissDeleteDialog()
            is AuthorWritingsAction.NavigateToPublish -> {}
            is AuthorWritingsAction.NavigateToDrafts -> {}
            is AuthorWritingsAction.NavigateToEditProfile -> {}
            is AuthorWritingsAction.EditWriting -> {}
        }
    }

    private fun checkOwnership() {
        _uiState.update { it.copy(isOwner = currentUserId == authorId) }
    }

    private fun loadWritings(authorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getWritingsByAuthorUseCase(authorId)
                .onSuccess { writings ->
                    val authorProfile = AuthorProfile(
                        authorId = authorId,
                        authorName = writings.firstOrNull()?.authorName ?: "",
                        profileImageUrl = writings.firstOrNull()?.authorProfileImage,
                        bio = "সৃজনশীল লেখক",
                        totalPosts = writings.size,
                        totalLikes = writings.sumOf { it.likes },
                        totalComments = writings.sumOf { it.comments }
                    )

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            writings = writings,
                            authorProfile = authorProfile,
                            error = null
                        )
                    }
                    applyFiltersAndSort()
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

    private fun filterByCategory(category: WritingCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFiltersAndSort()
    }

    private fun sortBy(sortType: PostSortType) {
        _uiState.update { it.copy(selectedSortType = sortType) }
        applyFiltersAndSort()
    }

    private fun filterByStatus(status: PostStatus) {
        _uiState.update { it.copy(selectedStatus = status) }
        applyFiltersAndSort()
    }

    private fun applyFiltersAndSort() {
        val currentState = _uiState.value
        var filtered = currentState.writings

        // Filter by category
        if (currentState.selectedCategory != WritingCategory.ALL) {
            filtered = filtered.filter { it.category == currentState.selectedCategory }
        }

        // Sort
        filtered = when (currentState.selectedSortType) {
            PostSortType.NEWEST -> filtered.sortedByDescending { it.createdAt }
            PostSortType.OLDEST -> filtered.sortedBy { it.createdAt }
            PostSortType.MOST_LIKED -> filtered.sortedByDescending { it.likes }
            PostSortType.MOST_COMMENTED -> filtered.sortedByDescending { it.comments }
        }

        _uiState.update { it.copy(filteredWritings = filtered) }
    }

    private fun deleteWriting(writingId: String) {
        viewModelScope.launch {
            deleteWritingUseCase(writingId)
                .onSuccess {
                    dismissDeleteDialog()
                    loadWritings(authorId)
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(error = error.message ?: "Failed to delete writing")
                    }
                }
        }
    }

    private fun showDeleteDialog(writingId: String) {
        _uiState.update {
            it.copy(
                showDeleteDialog = true,
                writingToDelete = writingId
            )
        }
    }

    private fun dismissDeleteDialog() {
        _uiState.update {
            it.copy(
                showDeleteDialog = false,
                writingToDelete = null
            )
        }
    }
}

