package com.akash.beautifulbhaluka.presentation.screens.restaurants

data class RestaurantsUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val error: String? = null
)

data class Restaurant(
    val name: String,
    val thumbnail: String,
    val address: String
)

sealed interface RestaurantsAction {
    object LoadData : RestaurantsAction
    data class OnRestaurantClick(val restaurant: Restaurant) : RestaurantsAction
}
