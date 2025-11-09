package com.akash.beautifulbhaluka.presentation.screens.social.comments

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.repository.VoiceRecorder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * ViewModel for Comments Screen
 * Manages comment interactions, replies, likes following MVVM architecture
 */
@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val voiceRecorder: VoiceRecorder,
    private val application: Application
) : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(CommentsUiState())
    val uiState: StateFlow<CommentsUiState> = _uiState.asStateFlow()

    private val postId: String = savedStateHandle.get<String>("postId") ?: ""

    private var currentRecordingFile: File? = null

    init {
        _uiState.update { it.copy(postId = postId) }
        if (postId.isNotEmpty()) {
            loadComments()
        }

        // Observe recording duration from VoiceRecorder
        voiceRecorder.recordingDuration
            .onEach { duration ->
                _uiState.update { it.copy(recordingDuration = duration) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: CommentsAction) {
        when (action) {
            is CommentsAction.LoadComments -> loadComments()
            is CommentsAction.UpdateCommentText -> updateCommentText(action.text)
            is CommentsAction.SubmitComment -> submitComment()
            is CommentsAction.LikeComment -> likeComment(action.commentId)
            is CommentsAction.UnlikeComment -> unlikeComment(action.commentId)
            is CommentsAction.ReactToComment -> reactToComment(action.commentId, action.reaction)
            is CommentsAction.CustomReactToComment -> customReactToComment(
                action.commentId,
                action.emoji,
                action.label
            )

            is CommentsAction.StartReply -> startReply(action.comment)
            is CommentsAction.CancelReply -> cancelReply()
            is CommentsAction.DeleteComment -> deleteComment(action.commentId)
            is CommentsAction.LoadReplies -> loadReplies(action.commentId)
            // Image upload actions
            is CommentsAction.AddImage -> addImage(action.imageUrl)
            is CommentsAction.RemoveImage -> removeImage(action.imageUrl)
            is CommentsAction.PickImage -> pickImage()
            // Voice recording actions
            is CommentsAction.StartVoiceRecording -> startVoiceRecording()
            is CommentsAction.StopVoiceRecording -> stopVoiceRecording()
            is CommentsAction.CancelVoiceRecording -> cancelVoiceRecording()
            is CommentsAction.UpdateRecordingDuration -> updateRecordingDuration(action.duration)
            is CommentsAction.OnMicrophonePermissionResult -> onMicrophonePermissionResult(action.granted)
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
        if (currentState.commentText.isBlank() &&
            currentState.selectedImages.isEmpty() &&
            currentState.voiceRecordingUrl == null
        ) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true) }

            val result = if (currentState.replyingTo != null) {
                repository.addReply(
                    postId = currentState.postId,
                    parentCommentId = currentState.replyingTo.id,
                    content = currentState.commentText,
                    images = currentState.selectedImages,
                    voiceUrl = currentState.voiceRecordingUrl,
                    voiceDuration = currentState.recordingDuration
                )
            } else {
                repository.addComment(
                    postId = currentState.postId,
                    content = currentState.commentText,
                    images = currentState.selectedImages,
                    voiceUrl = currentState.voiceRecordingUrl,
                    voiceDuration = currentState.recordingDuration
                )
            }

            result.onSuccess { newComment ->
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        commentText = "",
                        replyingTo = null,
                        selectedImages = emptyList(),
                        voiceRecordingUrl = null,
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
                isLiked = isLiked,
                userReaction = if (!isLiked) null else comment.userReaction,
                customReactionEmoji = if (!isLiked) null else comment.customReactionEmoji,
                customReactionLabel = if (!isLiked) null else comment.customReactionLabel
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

    private fun reactToComment(
        commentId: String,
        reaction: com.akash.beautifulbhaluka.domain.model.Reaction
    ) {
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

    // Image upload methods
    private fun addImage(imageUrl: String) {
        _uiState.update { state ->
            val currentImages = state.selectedImages.toMutableList()
            if (currentImages.size < 5) { // Limit to 5 images
                currentImages.add(imageUrl)
            }
            state.copy(selectedImages = currentImages)
        }
    }

    private fun removeImage(imageUrl: String) {
        _uiState.update { state ->
            state.copy(selectedImages = state.selectedImages.filter { it != imageUrl })
        }
    }

    private fun pickImage() {
        // TODO: Implement image picker integration
        // This will be handled by the UI layer through ActivityResultLauncher
        // For now, this is a placeholder that can trigger UI-side image picker
    }

    // Voice recording methods
    private fun startVoiceRecording() {
        // Trigger permission request - actual recording starts after permission granted
        _uiState.update { it.copy(needsMicrophonePermission = true) }
    }

    private fun stopVoiceRecording() {
        viewModelScope.launch {
            val recordedDuration = _uiState.value.recordingDuration

            voiceRecorder.stopRecording()
                .onSuccess { filePath ->
                    _uiState.update {
                        it.copy(
                            isRecordingVoice = false,
                            voiceRecordingUrl = filePath,
                            recordingDuration = recordedDuration // Keep the actual recorded duration
                        )
                    }

                    // Auto-submit voice comment
                    submitComment()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isRecordingVoice = false,
                            recordingDuration = 0L,
                            error = "Failed to save recording: ${error.message}"
                        )
                    }
                }
        }
    }

    private fun cancelVoiceRecording() {
        viewModelScope.launch {
            voiceRecorder.cancelRecording()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isRecordingVoice = false,
                            recordingDuration = 0L,
                            voiceRecordingUrl = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isRecordingVoice = false,
                            recordingDuration = 0L,
                            voiceRecordingUrl = null,
                            error = "Failed to cancel recording: ${error.message}"
                        )
                    }
                }
        }
    }

    private fun updateRecordingDuration(duration: Long) {
        // Duration is now updated from VoiceRecorder flow
        // This method kept for backward compatibility but not needed
    }


    private fun onMicrophonePermissionResult(granted: Boolean) {
        if (granted) {
            // Permission granted, start recording
            viewModelScope.launch {
                // Create temp file for recording
                val recordingsDir = File(application.cacheDir, "recordings")
                if (!recordingsDir.exists()) {
                    recordingsDir.mkdirs()
                }

                val timestamp = System.currentTimeMillis()
                currentRecordingFile = File(recordingsDir, "voice_$timestamp.m4a")

                currentRecordingFile?.let { file ->
                    voiceRecorder.startRecording(file)
                        .onSuccess {
                            _uiState.update {
                                it.copy(
                                    isRecordingVoice = true,
                                    recordingDuration = 0L,
                                    needsMicrophonePermission = false
                                )
                            }
                        }
                        .onFailure { error ->
                            _uiState.update {
                                it.copy(
                                    needsMicrophonePermission = false,
                                    error = "Failed to start recording: ${error.message}"
                                )
                            }
                        }
                }
            }
        } else {
            // Permission denied, reset state
            _uiState.update {
                it.copy(
                    needsMicrophonePermission = false,
                    error = "Microphone permission is required for voice comments"
                )
            }
        }
    }

    fun requestMicrophonePermission() {
        _uiState.update { it.copy(needsMicrophonePermission = true) }
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up voice recorder resources
        voiceRecorder.release()
    }
}
