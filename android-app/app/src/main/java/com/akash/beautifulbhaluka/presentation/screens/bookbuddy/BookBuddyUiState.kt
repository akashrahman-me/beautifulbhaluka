package com.akash.beautifulbhaluka.presentation.screens.bookbuddy

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.model.WritingCategory

data class BookBuddyUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val writings: List<Writing> = emptyList(),
    val selectedCategory: WritingCategory = WritingCategory.ALL,
    val showCreateDialog: Boolean = false,
    val searchQuery: String = ""
)

sealed class BookBuddyAction {
    object LoadWritings : BookBuddyAction()
    object Refresh : BookBuddyAction()
    data class SelectCategory(val category: WritingCategory) : BookBuddyAction()
    data class SearchQueryChanged(val query: String) : BookBuddyAction()
    object ShowCreateDialog : BookBuddyAction()
    object HideCreateDialog : BookBuddyAction()
    data class NavigateToDetail(val writingId: String) : BookBuddyAction()
    data class NavigateToAuthor(val authorId: String) : BookBuddyAction()
    data class ReactToWriting(val writingId: String, val reaction: Reaction) : BookBuddyAction()
}

