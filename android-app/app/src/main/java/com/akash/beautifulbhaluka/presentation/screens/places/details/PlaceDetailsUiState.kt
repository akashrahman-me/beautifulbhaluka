package com.akash.beautifulbhaluka.presentation.screens.places.details

data class PlaceDetailsUiState(
    val isLoading: Boolean = false,
    val placeContent: String = "",
    val error: String? = null
)

sealed interface PlaceDetailsAction {
    object NavigateBack : PlaceDetailsAction
    object NavigateHome : PlaceDetailsAction
}

