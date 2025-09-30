package com.akash.beautifulbhaluka.presentation.screens.gym

data class GymUiState(
    val isLoading: Boolean = false,
    val gyms: List<Gym> = emptyList(),
    val error: String? = null
)

data class Gym(
    val name: String,
    val address: String,
    val phone: String,
    val image: String
)

sealed interface GymAction {
    object LoadData : GymAction
    data class CallGym(val phone: String) : GymAction
}
