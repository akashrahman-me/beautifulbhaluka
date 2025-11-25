package com.akash.beautifulbhaluka.presentation.screens.gym

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GymViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GymUiState())
    val uiState: StateFlow<GymUiState> = _uiState.asStateFlow()

    init {
        onAction(GymAction.LoadData)
    }

    fun onAction(action: GymAction) {
        when (action) {
            is GymAction.LoadData -> loadData()
            is GymAction.CallGym -> callGym(action.phone)
            is GymAction.RateGym -> rateGym(action.gymId, action.rating)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the provided gym data
            val gyms = listOf(
                Gym(
                    id = "gym_1",
                    name = "এফএনএফ গেট ফিট জিম",
                    address = "ভালুকা ওয়াহেদ টাওয়ার ২য় তালা",
                    phone = "01970-409797",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/FB_IMG_17352372784802.jpg",
                    averageRating = 4.5f,
                    ratingCount = 23,
                    userRating = 0
                ),
                Gym(
                    id = "gym_2",
                    name = "নাছিব জিম",
                    address = "ভালুকা বাসস্ট্যান্ড রাস্তার পূর্ব পাশে",
                    phone = "01713-639057",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/MVIMG_20220915_154807.jpg",
                    averageRating = 4.2f,
                    ratingCount = 18,
                    userRating = 0
                ),
                Gym(
                    id = "gym_3",
                    name = "শাহীন জিম এন্ড ফিটনেস",
                    address = "সিডস্টোর ভালুকা",
                    phone = "01613-907112",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2023-01-31.jpg",
                    averageRating = 4.7f,
                    ratingCount = 31,
                    userRating = 0
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    gyms = gyms,
                    error = null
                )
            }
        }
    }

    private fun callGym(phone: String) {
        // TODO: Implement phone call functionality
        // For now, this is just a placeholder
    }

    private fun rateGym(gymId: String, rating: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedGyms = currentState.gyms.map { gym ->
                    if (gym.id == gymId) {
                        val newRatingCount =
                            if (gym.userRating == 0) gym.ratingCount + 1 else gym.ratingCount
                        val totalRating =
                            (gym.averageRating * gym.ratingCount) - gym.userRating + rating
                        val newAverageRating = totalRating / newRatingCount

                        gym.copy(
                            userRating = rating,
                            averageRating = newAverageRating,
                            ratingCount = newRatingCount
                        )
                    } else {
                        gym
                    }
                }
                currentState.copy(gyms = updatedGyms)
            }
        }
    }
}
