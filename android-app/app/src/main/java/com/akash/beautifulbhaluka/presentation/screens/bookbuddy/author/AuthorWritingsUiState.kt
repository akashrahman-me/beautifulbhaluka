package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.author

import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.Writing

data class AuthorWritingsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val writings: List<Writing> = emptyList(),
    val authorName: String = ""
)

sealed class AuthorWritingsAction {
    data class LoadWritings(val authorId: String) : AuthorWritingsAction()
    object Refresh : AuthorWritingsAction()
    data class NavigateToDetail(val writingId: String) : AuthorWritingsAction()
    data class ReactToWriting(val writingId: String, val reaction: Reaction) :
        AuthorWritingsAction()
}

