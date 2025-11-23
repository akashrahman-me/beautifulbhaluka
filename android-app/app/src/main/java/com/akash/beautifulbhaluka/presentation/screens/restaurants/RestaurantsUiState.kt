package com.akash.beautifulbhaluka.presentation.screens.restaurants

data class RestaurantsUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val error: String? = null
)

data class Restaurant(
    val name: String,
    val thumbnail: String,
    val address: String,
    val rating: Int = 0,
    val ratingCount: Int = 0
)

sealed interface RestaurantsAction {
    object LoadData : RestaurantsAction
    data class OnRestaurantClick(val restaurant: Restaurant) : RestaurantsAction
    data class OnRatingChange(val restaurant: Restaurant, val rating: Int) : RestaurantsAction
}
