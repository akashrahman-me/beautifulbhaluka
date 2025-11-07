package com.akash.beautifulbhaluka.presentation.screens.houserent.details

import com.akash.beautifulbhaluka.domain.model.HouseRent

data class HouseRentDetailsUiState(
    val isLoading: Boolean = false,
    val property: HouseRent? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)

sealed class HouseRentDetailsAction {
    object ToggleFavorite : HouseRentDetailsAction()
    object Share : HouseRentDetailsAction()
    object Call : HouseRentDetailsAction()
    object WhatsApp : HouseRentDetailsAction()
}

