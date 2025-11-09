package com.akash.beautifulbhaluka.presentation.screens.social.comments

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Reaction

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
    val postId: String = "",
    // Image upload support
    val selectedImages: List<String> = emptyList(),
    // Voice recording support
    val isRecordingVoice: Boolean = false,
    val recordingDuration: Long = 0L,
    val voiceRecordingUrl: String? = null,
    val needsMicrophonePermission: Boolean = false
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
    data class ReactToComment(val commentId: String, val reaction: Reaction) : CommentsAction()
    data class CustomReactToComment(val commentId: String, val emoji: String, val label: String) :
        CommentsAction()

    data class StartReply(val comment: Comment) : CommentsAction()
    object CancelReply : CommentsAction()
    data class DeleteComment(val commentId: String) : CommentsAction()
    data class LoadReplies(val commentId: String) : CommentsAction()

    // Image upload actions
    data class AddImage(val imageUrl: String) : CommentsAction()
    data class RemoveImage(val imageUrl: String) : CommentsAction()
    object PickImage : CommentsAction()

    // Voice recording actions
    object StartVoiceRecording : CommentsAction()
    object StopVoiceRecording : CommentsAction()
    object CancelVoiceRecording : CommentsAction()
    data class UpdateRecordingDuration(val duration: Long) : CommentsAction()
    data class OnMicrophonePermissionResult(val granted: Boolean) : CommentsAction()
}
