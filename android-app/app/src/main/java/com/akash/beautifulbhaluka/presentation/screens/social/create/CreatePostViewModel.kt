package com.akash.beautifulbhaluka.presentation.screens.social.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.PostPrivacy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreatePostViewModel : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState: StateFlow<CreatePostUiState> = _uiState.asStateFlow()

    fun onAction(action: CreatePostAction) {
        when (action) {
            is CreatePostAction.UpdateContent -> updateContent(action.content)
            is CreatePostAction.AddImages -> addImages(action.uris)
            is CreatePostAction.RemoveImage -> removeImage(action.uri)
            is CreatePostAction.UpdatePrivacy -> updatePrivacy(action.privacy)
            is CreatePostAction.UpdateLocation -> updateLocation(action.location)
            is CreatePostAction.ShowPrivacyDialog -> showPrivacyDialog()
            is CreatePostAction.HidePrivacyDialog -> hidePrivacyDialog()
            is CreatePostAction.ShowLocationDialog -> showLocationDialog()
            is CreatePostAction.HideLocationDialog -> hideLocationDialog()
            is CreatePostAction.PublishPost -> publishPost()
            is CreatePostAction.ClearSuccess -> clearSuccess()
        }
    }

    private fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content, error = null)
    }

    private fun addImages(uris: List<Uri>) {
        val currentImages = _uiState.value.selectedImages
        val newImages = (currentImages + uris).take(10) // Max 10 images
        _uiState.value = _uiState.value.copy(selectedImages = newImages)
    }

    private fun removeImage(uri: Uri) {
        _uiState.value = _uiState.value.copy(
            selectedImages = _uiState.value.selectedImages.filter { it != uri }
        )
    }

    private fun updatePrivacy(privacy: PostPrivacy) {
        _uiState.value = _uiState.value.copy(
            selectedPrivacy = privacy,
            showPrivacyDialog = false
        )
    }

    private fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(
            location = location,
            showLocationDialog = false
        )
    }

    private fun showPrivacyDialog() {
        _uiState.value = _uiState.value.copy(showPrivacyDialog = true)
    }

    private fun hidePrivacyDialog() {
        _uiState.value = _uiState.value.copy(showPrivacyDialog = false)
    }

    private fun showLocationDialog() {
        _uiState.value = _uiState.value.copy(showLocationDialog = true)
    }

    private fun hideLocationDialog() {
        _uiState.value = _uiState.value.copy(showLocationDialog = false)
    }

    private fun publishPost() {
        val state = _uiState.value

        if (state.content.isBlank() && state.selectedImages.isEmpty()) {
            _uiState.value = state.copy(error = "Post cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isPosting = true, error = null)

            // Convert Uri to String (in real app, upload images first)
            val imageUrls = state.selectedImages.map { it.toString() }

            repository.createPost(
                content = state.content,
                images = imageUrls,
                videoUrl = null,
                privacy = state.selectedPrivacy,
                location = state.location.ifBlank { null }
            )
                .onSuccess {
                    _uiState.value = CreatePostUiState(postSuccess = true)
                }
                .onFailure { error ->
                    _uiState.value = state.copy(
                        isPosting = false,
                        error = error.message
                    )
                }
        }
    }

    private fun clearSuccess() {
        _uiState.value = _uiState.value.copy(postSuccess = false)
    }
}
