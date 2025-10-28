package com.akash.beautifulbhaluka.presentation.screens.social.profile.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for All Photos Screen
 * Handles business logic and state management
 */
class AllPhotosViewModel : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(AllPhotosUiState())
    val uiState: StateFlow<AllPhotosUiState> = _uiState.asStateFlow()

    fun onAction(action: AllPhotosAction) {
        when (action) {
            is AllPhotosAction.LoadPhotos -> loadPhotos(action.userId)
            is AllPhotosAction.SelectFilter -> selectFilter(action.filter)
            is AllPhotosAction.PhotoClick -> handlePhotoClick(action.photo)
            is AllPhotosAction.UploadPhoto -> handleUploadPhoto()
            is AllPhotosAction.Refresh -> refreshPhotos()
            is AllPhotosAction.NavigateBack -> {} // Handled by navigation
        }
    }

    private fun loadPhotos(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getPhotos(userId)
                .onSuccess { photos ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            photos = photos,
                            filteredPhotos = filterPhotos(photos, it.selectedFilter),
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load photos"
                        )
                    }
                }
        }
    }

    private fun selectFilter(filter: PhotoFilter) {
        _uiState.update {
            it.copy(
                selectedFilter = filter,
                filteredPhotos = filterPhotos(it.photos, filter)
            )
        }
    }

    private fun filterPhotos(photos: List<Photo>, filter: PhotoFilter): List<Photo> {
        return when (filter) {
            PhotoFilter.ALL -> photos
            PhotoFilter.RECENT -> photos.sortedByDescending { it.createdAt }
            PhotoFilter.POPULAR -> photos.sortedByDescending { it.likes }
            PhotoFilter.ALBUMS -> photos // TODO: Implement album filtering
        }
    }

    private fun refreshPhotos() {
        val currentState = _uiState.value
        if (!currentState.isRefreshing && currentState.photos.isNotEmpty()) {
            _uiState.update { it.copy(isRefreshing = true) }
            // Re-load photos
            viewModelScope.launch {
                // Simulate refresh
                kotlinx.coroutines.delay(1000)
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    private fun handlePhotoClick(photo: Photo) {
        // TODO: Navigate to photo detail screen or open photo viewer
    }

    private fun handleUploadPhoto() {
        // TODO: Handle photo upload
    }
}

