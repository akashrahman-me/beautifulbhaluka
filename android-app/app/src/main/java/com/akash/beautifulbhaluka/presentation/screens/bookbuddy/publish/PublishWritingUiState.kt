package com.akash.beautifulbhaluka.presentation.screens.bookbuddy.publish

import android.net.Uri
import com.akash.beautifulbhaluka.domain.model.WritingCategory

data class PublishWritingUiState(
    val title: String = "",
    val content: String = "",
    val excerpt: String = "",
    val category: WritingCategory = WritingCategory.STORY,
    val coverImageUri: Uri? = null,
    val isPublishing: Boolean = false,
    val isSavingDraft: Boolean = false,
    val error: String? = null,
    val publishedWritingId: String? = null,
    val titleError: String? = null,
    val contentError: String? = null
)

sealed class PublishWritingAction {
    data class UpdateTitle(val title: String) : PublishWritingAction()
    data class UpdateContent(val content: String) : PublishWritingAction()
    data class UpdateExcerpt(val excerpt: String) : PublishWritingAction()
    data class UpdateCategory(val category: WritingCategory) : PublishWritingAction()
    data class UpdateCoverImage(val uri: Uri?) : PublishWritingAction()
    object Publish : PublishWritingAction()
    object SaveDraft : PublishWritingAction()
    object ClearError : PublishWritingAction()
}

