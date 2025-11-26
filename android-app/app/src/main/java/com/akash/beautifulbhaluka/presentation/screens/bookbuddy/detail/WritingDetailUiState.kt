package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.detail

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.model.Writing

data class WritingDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val writing: Writing? = null,
    val comments: List<Comment> = emptyList(),
    val isLoadingComments: Boolean = false,
    val showCommentInput: Boolean = false,
    val commentText: String = ""
)

sealed class WritingDetailAction {
    data class LoadWriting(val id: String) : WritingDetailAction()
    object Refresh : WritingDetailAction()
    data class ReactToWriting(val reaction: Reaction) : WritingDetailAction()
    object ToggleCommentInput : WritingDetailAction()
    data class UpdateCommentText(val text: String) : WritingDetailAction()
    object SubmitComment : WritingDetailAction()
    data class DeleteComment(val commentId: String) : WritingDetailAction()
    data class ReactToComment(val commentId: String) : WritingDetailAction()
    data class NavigateToAuthor(val authorId: String) : WritingDetailAction()
}

