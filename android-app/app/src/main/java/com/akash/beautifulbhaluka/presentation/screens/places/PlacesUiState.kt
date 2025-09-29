package com.akash.beautifulbhaluka.presentation.screens.places

data class PlacesUiState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val error: String? = null
)

data class Place(
    val title: String,
    val thumbnail: String,
    val author: String,
    val date: String,
    val content: String,
    val category: String
)

sealed interface PlacesAction {
    object LoadData : PlacesAction
    data class OnPlaceClick(val place: Place) : PlacesAction
}
