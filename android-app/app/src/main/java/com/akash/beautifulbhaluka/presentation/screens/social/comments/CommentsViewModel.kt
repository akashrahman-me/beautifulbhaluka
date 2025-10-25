package com.akash.beautifulbhaluka.presentation.screens.social.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Comments Screen
 * Manages comment interactions, replies, likes following MVVM architecture
 */
class CommentsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(CommentsUiState())
    val uiState: StateFlow<CommentsUiState> = _uiState.asStateFlow()

    private val postId: String = savedStateHandle.get<String>("postId") ?: ""

    init {
        _uiState.update { it.copy(postId = postId) }
        if (postId.isNotEmpty()) {
            loadComments()
        }
    }

    fun onAction(action: CommentsAction) {
        when (action) {
            is CommentsAction.LoadComments -> loadComments()
            is CommentsAction.UpdateCommentText -> updateCommentText(action.text)
            is CommentsAction.SubmitComment -> submitComment()
            is CommentsAction.LikeComment -> likeComment(action.commentId)
            is CommentsAction.UnlikeComment -> unlikeComment(action.commentId)
            is CommentsAction.ReactToComment -> reactToComment(action.commentId, action.reaction)
            is CommentsAction.CustomReactToComment -> customReactToComment(action.commentId, action.emoji, action.label)
            is CommentsAction.StartReply -> startReply(action.comment)
            is CommentsAction.CancelReply -> cancelReply()
            is CommentsAction.DeleteComment -> deleteComment(action.commentId)
            is CommentsAction.LoadReplies -> loadReplies(action.commentId)
        }
    }

    private fun loadComments() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getComments(_uiState.value.postId)
                .onSuccess { comments ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            comments = comments,
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

    private fun updateCommentText(text: String) {
        _uiState.update { it.copy(commentText = text) }
    }

    private fun submitComment() {
        val currentState = _uiState.value
        if (currentState.commentText.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }

            val result = if (currentState.replyingTo != null) {
                repository.addReply(
                    postId = currentState.postId,
                    parentCommentId = currentState.replyingTo.id,
                    content = currentState.commentText
                )
            } else {
                repository.addComment(
                    postId = currentState.postId,
                    content = currentState.commentText
                )
            }

            result.onSuccess { newComment ->
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        commentText = "",
                        replyingTo = null,
                        comments = if (currentState.replyingTo != null) {
                            // Add reply to parent comment
                            it.comments.map { comment ->
                                if (comment.id == currentState.replyingTo.id) {
                                    comment.copy(replies = comment.replies + newComment)
                                } else {
                                    comment
                                }
                            }
                        } else {
                            // Add new top-level comment
                            listOf(newComment) + it.comments
                        }
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        error = error.message
                    )
                }
            }
        }
    }

    private fun likeComment(commentId: String) {
        viewModelScope.launch {
            repository.likeComment(commentId)
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            comments = state.comments.map { comment ->
                                updateCommentLike(comment, commentId, true)
                            }
                        )
                    }
                }
        }
    }

    private fun unlikeComment(commentId: String) {
        viewModelScope.launch {
            repository.unlikeComment(commentId)
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            comments = state.comments.map { comment ->
                                updateCommentLike(comment, commentId, false)
                            }
                        )
                    }
                }
        }
    }

    private fun updateCommentLike(comment: Comment, commentId: String, isLiked: Boolean): Comment {
        return if (comment.id == commentId) {
            comment.copy(
                likes = if (isLiked) comment.likes + 1 else maxOf(0, comment.likes - 1),
                isLiked = isLiked
            )
        } else {
            comment.copy(
                replies = comment.replies.map { reply ->
                    updateCommentLike(reply, commentId, isLiked)
                }
            )
        }
    }

    private fun startReply(comment: Comment) {
        _uiState.update { it.copy(replyingTo = comment) }
    }

    private fun cancelReply() {
        _uiState.update { it.copy(replyingTo = null, commentText = "") }
    }

    private fun deleteComment(commentId: String) {
        viewModelScope.launch {
            repository.deleteComment(commentId)
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            comments = state.comments.filter { it.id != commentId }
                                .map { comment ->
                                    comment.copy(
                                        replies = comment.replies.filter { it.id != commentId }
                                    )
                                }
                        )
                    }
                }
        }
    }

    private fun loadReplies(commentId: String) {
        viewModelScope.launch {
            repository.getReplies(commentId)
                .onSuccess { replies ->
                    _uiState.update { state ->
                        state.copy(
                            comments = state.comments.map { comment ->
                                if (comment.id == commentId) {
                                    comment.copy(replies = replies)
                                } else {
                                    comment
                                }
                            }
                        )
                    }
                }
        }
    }

    private fun reactToComment(commentId: String, reaction: com.akash.beautifulbhaluka.domain.model.Reaction) {
        _uiState.update { state ->
            state.copy(
                comments = state.comments.map { comment ->
                    updateCommentReaction(comment, commentId, reaction)
                }
            )
        }
    }

    private fun customReactToComment(commentId: String, emoji: String, label: String) {
        _uiState.update { state ->
            state.copy(
                comments = state.comments.map { comment ->
                    updateCommentCustomReaction(comment, commentId, emoji, label)
                }
            )
        }
    }

    private fun updateCommentReaction(
        comment: Comment,
        commentId: String,
        reaction: com.akash.beautifulbhaluka.domain.model.Reaction
    ): Comment {
        return if (comment.id == commentId) {
            comment.copy(
                userReaction = reaction,
                customReactionEmoji = null,
                customReactionLabel = null,
                isLiked = true,
                likes = if (!comment.isLiked && comment.userReaction == null && comment.customReactionEmoji == null) {
                    comment.likes + 1
                } else {
                    comment.likes
                }
            )
        } else {
            comment.copy(
                replies = comment.replies.map { reply ->
                    updateCommentReaction(reply, commentId, reaction)
                }
            )
        }
    }

    private fun updateCommentCustomReaction(
        comment: Comment,
        commentId: String,
        emoji: String,
        label: String
    ): Comment {
        return if (comment.id == commentId) {
            comment.copy(
                customReactionEmoji = emoji,
                customReactionLabel = label,
                userReaction = null,
                isLiked = true,
                likes = if (!comment.isLiked && comment.userReaction == null && comment.customReactionEmoji == null) {
                    comment.likes + 1
                } else {
                    comment.likes
                }
            )
        } else {
            comment.copy(
                replies = comment.replies.map { reply ->
                    updateCommentCustomReaction(reply, commentId, emoji, label)
                }
            )
        }
    }
}
