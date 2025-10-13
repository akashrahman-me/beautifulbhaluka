package com.akash.beautifulbhaluka.presentation.screens.social.create

import android.net.Uri
import com.akash.beautifulbhaluka.domain.model.PostPrivacy

data class CreatePostUiState(
    val content: String = "",
    val selectedImages: List<Uri> = emptyList(),
    val selectedPrivacy: PostPrivacy = PostPrivacy.PUBLIC,
    val location: String = "",
    val isPosting: Boolean = false,
    val postSuccess: Boolean = false,
    val error: String? = null,
    val showPrivacyDialog: Boolean = false,
    val showLocationDialog: Boolean = false
)

sealed class CreatePostAction {
    data class UpdateContent(val content: String) : CreatePostAction()
    data class AddImages(val uris: List<Uri>) : CreatePostAction()
    data class RemoveImage(val uri: Uri) : CreatePostAction()
    data class UpdatePrivacy(val privacy: PostPrivacy) : CreatePostAction()
    data class UpdateLocation(val location: String) : CreatePostAction()
    object ShowPrivacyDialog : CreatePostAction()
    object HidePrivacyDialog : CreatePostAction()
    object ShowLocationDialog : CreatePostAction()
    object HideLocationDialog : CreatePostAction()
    object PublishPost : CreatePostAction()
    object ClearSuccess : CreatePostAction()
}

