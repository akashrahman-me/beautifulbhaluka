package com.akash.beautifulbhaluka.presentation.screens.gym

data class GymUiState(
    val isLoading: Boolean = false,
    val gyms: List<Gym> = emptyList(),
    val error: String? = null
)

data class Gym(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val image: String,
    val averageRating: Float = 0f,
    val ratingCount: Int = 0,
    val userRating: Int = 0
)

sealed interface GymAction {
    object LoadData : GymAction
    data class CallGym(val phone: String) : GymAction
    data class RateGym(val gymId: String, val rating: Int) : GymAction
}
