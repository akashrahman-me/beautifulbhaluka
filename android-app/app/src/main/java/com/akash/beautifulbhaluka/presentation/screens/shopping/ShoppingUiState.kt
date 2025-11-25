package com.akash.beautifulbhaluka.presentation.screens.shopping

data class ShoppingUiState(
    val isLoading: Boolean = false,
    val markets: List<Market> = emptyList(),
    val error: String? = null
)

data class Market(
    val title: String,
    val description: String,
    val image: String
)

sealed interface ShoppingAction {
    object LoadData : ShoppingAction
    object NavigateToPublish : ShoppingAction
}
