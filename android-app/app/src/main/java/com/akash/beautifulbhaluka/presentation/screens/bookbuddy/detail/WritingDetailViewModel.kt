package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.domain.usecase.AddWritingCommentUseCase
import com.akash.beautifulbhaluka.domain.usecase.GetWritingByIdUseCase
import com.akash.beautifulbhaluka.domain.usecase.GetWritingCommentsUseCase
import com.akash.beautifulbhaluka.domain.usecase.ReactToWritingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritingDetailViewModel @Inject constructor(
    private val getWritingByIdUseCase: GetWritingByIdUseCase,
    private val getWritingCommentsUseCase: GetWritingCommentsUseCase,
    private val addWritingCommentUseCase: AddWritingCommentUseCase,
    private val reactToWritingUseCase: ReactToWritingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(WritingDetailUiState())
    val uiState: StateFlow<WritingDetailUiState> = _uiState.asStateFlow()

    private val writingId: String = savedStateHandle.get<String>("writingId") ?: ""

    init {
        if (writingId.isNotEmpty()) {
            loadWriting(writingId)
        }
    }

    fun onAction(action: WritingDetailAction) {
        when (action) {
            is WritingDetailAction.LoadWriting -> loadWriting(action.id)
            is WritingDetailAction.Refresh -> refresh()
            is WritingDetailAction.ReactToWriting -> reactToWriting(action.reaction)
            is WritingDetailAction.ToggleCommentInput -> toggleCommentInput()
            is WritingDetailAction.UpdateCommentText -> updateCommentText(action.text)
            is WritingDetailAction.SubmitComment -> submitComment()
            is WritingDetailAction.DeleteComment -> {}
            is WritingDetailAction.ReactToComment -> {}
            is WritingDetailAction.NavigateToAuthor -> {}
        }
    }

    private fun loadWriting(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getWritingByIdUseCase(id)
                .onSuccess { writing ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            writing = writing,
                            error = null
                        )
                    }
                    loadComments(id)
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load writing"
                        )
                    }
                }
        }
    }

    private fun loadComments(writingId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingComments = true) }
            getWritingCommentsUseCase(writingId)
                .onSuccess { comments ->
                    _uiState.update {
                        it.copy(
                            isLoadingComments = false,
                            comments = comments
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoadingComments = false) }
                }
        }
    }

    private fun refresh() {
        loadWriting(writingId)
    }

    private fun reactToWriting(reaction: Reaction) {
        viewModelScope.launch {
            reactToWritingUseCase(writingId, reaction)
                .onSuccess {
                    loadWriting(writingId)
                }
        }
    }

    private fun toggleCommentInput() {
        _uiState.update {
            it.copy(showCommentInput = !it.showCommentInput)
        }
    }

    private fun updateCommentText(text: String) {
        _uiState.update { it.copy(commentText = text) }
    }

    private fun submitComment() {
        val text = _uiState.value.commentText
        if (text.isBlank()) return

        viewModelScope.launch {
            val comment = Comment(
                id = "",
                postId = writingId,
                userId = "current_user",
                userName = "Current User",
                userProfileImage = "",
                content = text,
                createdAt = System.currentTimeMillis()
            )

            addWritingCommentUseCase(writingId, comment)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            commentText = "",
                            showCommentInput = false
                        )
                    }
                    loadComments(writingId)
                    loadWriting(writingId)
                }
        }
    }
}

