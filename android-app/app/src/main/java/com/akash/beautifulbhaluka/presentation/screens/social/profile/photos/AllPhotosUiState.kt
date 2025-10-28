package com.akash.beautifulbhaluka.presentation.screens.social.profile.photos

import com.akash.beautifulbhaluka.domain.model.Photo

/**
 * UI State for All Photos Screen
 */
data class AllPhotosUiState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val filteredPhotos: List<Photo> = emptyList(),
    val selectedFilter: PhotoFilter = PhotoFilter.ALL,
    val error: String? = null,
    val isRefreshing: Boolean = false
)

/**
 * User Actions for All Photos Screen
 */
sealed class AllPhotosAction {
    data class LoadPhotos(val userId: String) : AllPhotosAction()
    data class SelectFilter(val filter: PhotoFilter) : AllPhotosAction()
    data class PhotoClick(val photo: Photo) : AllPhotosAction()
    object UploadPhoto : AllPhotosAction()
    object Refresh : AllPhotosAction()
    object NavigateBack : AllPhotosAction()
}

/**
 * Photo Filter Types
 */
enum class PhotoFilter(val displayName: String) {
    ALL("All Photos"),
    RECENT("Most Recent"),
    POPULAR("Most Popular"),
    ALBUMS("Albums")
}

