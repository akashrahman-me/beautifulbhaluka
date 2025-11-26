package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.publish

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.Writing
import com.akash.beautifulbhaluka.domain.model.WritingCategory
import com.akash.beautifulbhaluka.domain.usecase.CreateWritingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PublishWritingViewModel @Inject constructor(
    private val createWritingUseCase: CreateWritingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PublishWritingUiState())
    val uiState: StateFlow<PublishWritingUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishWritingAction) {
        when (action) {
            is PublishWritingAction.UpdateTitle -> updateTitle(action.title)
            is PublishWritingAction.UpdateContent -> updateContent(action.content)
            is PublishWritingAction.UpdateExcerpt -> updateExcerpt(action.excerpt)
            is PublishWritingAction.UpdateCategory -> updateCategory(action.category)
            is PublishWritingAction.UpdateCoverImage -> updateCoverImage(action.uri)
            is PublishWritingAction.Publish -> publish()
            is PublishWritingAction.SaveDraft -> saveDraft()
            is PublishWritingAction.ClearError -> clearError()
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update {
            it.copy(
                title = title,
                titleError = null
            )
        }
    }

    private fun updateContent(content: String) {
        _uiState.update {
            it.copy(
                content = content,
                contentError = null
            )
        }
    }

    private fun updateExcerpt(excerpt: String) {
        _uiState.update { it.copy(excerpt = excerpt) }
    }

    private fun updateCategory(category: WritingCategory) {
        _uiState.update { it.copy(category = category) }
    }

    private fun updateCoverImage(uri: Uri?) {
        _uiState.update { it.copy(coverImageUri = uri) }
    }

    private fun publish() {
        if (!validateInput()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isPublishing = true, error = null) }

            val state = _uiState.value
            val excerpt = if (state.excerpt.isNotBlank()) {
                state.excerpt
            } else {
                state.content.take(150) + if (state.content.length > 150) "..." else ""
            }

            val writing = Writing(
                id = UUID.randomUUID().toString(),
                title = state.title,
                excerpt = excerpt,
                content = state.content,
                coverImageUrl = state.coverImageUri?.toString(),
                category = state.category,
                authorId = "current_user",
                authorName = "Current User",
                authorProfileImage = null,
                likes = 0,
                comments = 0,
                isLiked = false,
                userReaction = null,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            createWritingUseCase(writing)
                .onSuccess { createdWriting ->
                    _uiState.update {
                        it.copy(
                            isPublishing = false,
                            publishedWritingId = createdWriting.id,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isPublishing = false,
                            error = error.message ?: "Failed to publish writing"
                        )
                    }
                }
        }
    }

    private fun saveDraft() {
        if (!validateInput()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSavingDraft = true, error = null) }

            _uiState.update {
                it.copy(
                    isSavingDraft = false,
                    error = "Draft saved successfully! (This is a demo feature)"
                )
            }
        }
    }

    private fun validateInput(): Boolean {
        val state = _uiState.value
        var isValid = true
        var titleError: String? = null
        var contentError: String? = null

        if (state.title.isBlank()) {
            titleError = "শিরোনাম আবশ্যক"
            isValid = false
        }

        if (state.content.isBlank()) {
            contentError = "লেখা আবশ্যক"
            isValid = false
        }

        _uiState.update {
            it.copy(
                titleError = titleError,
                contentError = contentError
            )
        }

        return isValid
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

