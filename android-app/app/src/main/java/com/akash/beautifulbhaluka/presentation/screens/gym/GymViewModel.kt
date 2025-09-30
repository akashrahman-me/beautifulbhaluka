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
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the provided gym data
            val gyms = listOf(
                Gym(
                    name = "এফএনএফ গেট ফিট জিম",
                    address = "ভালুকা ওয়াহেদ টাওয়ার ২য় তালা",
                    phone = "01970-409797",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/FB_IMG_17352372784802.jpg"
                ),
                Gym(
                    name = "নাছিব জিম",
                    address = "ভালুকা বাসস্ট্যান্ড রাস্তার পূর্ব পাশে",
                    phone = "01713-639057",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/MVIMG_20220915_154807.jpg"
                ),
                Gym(
                    name = "শাহীন জিম এন্ড ফিটনেস",
                    address = "সিডস্টোর ভালুকা",
                    phone = "01613-907112",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2023-01-31.jpg"
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
}
