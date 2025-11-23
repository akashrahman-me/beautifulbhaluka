package com.akash.beautifulbhaluka.presentation.screens.restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestaurantsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantsUiState())
    val uiState: StateFlow<RestaurantsUiState> = _uiState.asStateFlow()

    init {
        onAction(RestaurantsAction.LoadData)
    }

    fun onAction(action: RestaurantsAction) {
        when (action) {
            is RestaurantsAction.LoadData -> loadData()
            is RestaurantsAction.OnRestaurantClick -> {
                // Handle restaurant click (could navigate to detail screen or show more info)
            }

            is RestaurantsAction.OnRatingChange -> {
                _uiState.update { currentState ->
                    val updatedRestaurants = currentState.restaurants.map { restaurant ->
                        if (restaurant == action.restaurant) {
                            restaurant.copy(rating = action.rating)
                        } else {
                            restaurant
                        }
                    }
                    currentState.copy(restaurants = updatedRestaurants)
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val restaurants = listOf(
                    Restaurant(
                        name = "হোটেল সেভেন স্টার",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/FB_IMG_1734854329560.jpg",
                        address = "ভালুকা",
                        rating = 4,
                        ratingCount = 25
                    ),
                    Restaurant(
                        name = "হোটেল স্বাদ",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2021-02-26.jpg",
                        address = "ভালুকা",
                        rating = 5,
                        ratingCount = 42
                    ),
                    Restaurant(
                        name = "ডক্টরস ক্যাফে",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20210215_165147.jpg",
                        address = "ভালুকা",
                        rating = 3,
                        ratingCount = 18
                    ),
                    Restaurant(
                        name = "সিটি গার্ডেন ২",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-05-16.jpg",
                        address = "ভালুকা",
                        rating = 4,
                        ratingCount = 31
                    ),
                    Restaurant(
                        name = "সারাবেলা ফুড গার্ডেন",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-01-09-2.jpg",
                        address = "ভালুকা",
                        rating = 5,
                        ratingCount = 56
                    ),
                    Restaurant(
                        name = "মাটির হোটেল",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-11-22.jpg",
                        address = "ভালুকা কলেজের পশ্চিম পাশে",
                        rating = 4,
                        ratingCount = 12
                    ),
                    Restaurant(
                        name = "স্বপ্ন বিলাস রেস্টুরেন্ট",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2023-12-16.jpg",
                        address = "ভালুকা হাজির বাজার\n01722-582593",
                        rating = 3,
                        ratingCount = 8
                    ),
                    Restaurant(
                        name = "হোটেল রাজধানী",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-02-28-1.jpg",
                        address = "ভালুকা",
                        rating = 4,
                        ratingCount = 37
                    ),
                    Restaurant(
                        name = "ফুড প্যারাডাইস রেস্টুরেন্ট এন্ড বিরিয়ানি হাউস",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2025/01/IMG-20250109-WA00872.jpg",
                        address = "ভালুকা গফরগাঁও রোড",
                        rating = 5,
                        ratingCount = 63
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        restaurants = restaurants,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "তথ্য লোড করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }
}
