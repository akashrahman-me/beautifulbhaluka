package com.akash.beautifulbhaluka.presentation.screens.social.comments

import com.akash.beautifulbhaluka.domain.model.Comment

/**
 * UI State for Comments Screen
 * Following MVVM pattern with clean state management
 */
data class CommentsUiState(
    val isLoading: Boolean = false,
    val comments: List<Comment> = emptyList(),
    val error: String? = null,
    val isSubmitting: Boolean = false,
    val commentText: String = "",
    val replyingTo: Comment? = null,
    val postId: String = ""
)

/**
 * Actions for Comments Screen
 * Single source of truth for all user interactions
 */
sealed class CommentsAction {
    object LoadComments : CommentsAction()
    data class UpdateCommentText(val text: String) : CommentsAction()
    object SubmitComment : CommentsAction()
    data class LikeComment(val commentId: String) : CommentsAction()
    data class UnlikeComment(val commentId: String) : CommentsAction()
    data class StartReply(val comment: Comment) : CommentsAction()
    object CancelReply : CommentsAction()
    data class DeleteComment(val commentId: String) : CommentsAction()
    data class LoadReplies(val commentId: String) : CommentsAction()
}

