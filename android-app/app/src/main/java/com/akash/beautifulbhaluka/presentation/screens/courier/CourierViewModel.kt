package com.akash.beautifulbhaluka.presentation.screens.courier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourierViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CourierUiState())
    val uiState: StateFlow<CourierUiState> = _uiState.asStateFlow()

    init {
        onAction(CourierAction.LoadData)
    }

    fun onAction(action: CourierAction) {
        when (action) {
            is CourierAction.LoadData -> loadData()
            is CourierAction.OnCourierClick -> {
                // Handle courier service click (could navigate to detail screen or show more info)
            }

            is CourierAction.OnPhoneClick -> {
                // Handle phone click (could open dialer)
            }

            is CourierAction.OnRatingChange -> {
                handleRatingChange(action.courierService, action.rating)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val courierServices = listOf(
                    CourierService(
                        name = "সুন্দরবন কুরিয়ার সার্ভিস",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-03-07.jpg",
                        address = "ভালুকা ডিজিটাল হাসপাতাল সংলগ্ন",
                        phone = "01952-255785",
                        location = "ভালুকা",
                        averageRating = 4.2f,
                        ratingCount = 15,
                        userRating = 0
                    ),
                    CourierService(
                        name = "সদাগর কুরিয়ার সার্ভিস এন্ড পার্সেল",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-03-25.jpg",
                        address = "ভালুকা ডিজিটাল হাসপাতাল সংলগ্ন",
                        phone = "01324-719196",
                        location = "ভালুকা",
                        averageRating = 4.5f,
                        ratingCount = 22,
                        userRating = 0
                    ),
                    CourierService(
                        name = "করতোয়া কুরিয়ার সার্ভিস",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/FB_IMG_1678364968266.jpg",
                        address = "ভালুকা কলেজের পশ্চিম পাশে",
                        phone = "01713-228474",
                        location = "ভালুকা",
                        averageRating = 4.0f,
                        ratingCount = 18,
                        userRating = 0
                    ),
                    CourierService(
                        name = "স্টেডফাস্ট কুরিয়ার",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-12-07.jpg",
                        address = "ভালুকা জ্ঞানের মোড় স্কুল রোড",
                        phone = "01321-230745",
                        location = "ভালুকা",
                        averageRating = 4.8f,
                        ratingCount = 35,
                        userRating = 0
                    ),
                    CourierService(
                        name = "মেট্রো এক্সপ্রেস ভালুকা",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2021-11-11.jpg",
                        address = "ভালুকা ডিজিটাল হাসপাতাল সংলগ্ন",
                        phone = "01810-152890",
                        location = "ভালুকা",
                        averageRating = 4.3f,
                        ratingCount = 28,
                        userRating = 0
                    ),
                    CourierService(
                        name = "REDX রিডিএক্স লজিস্টিক্স এলটিডি",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-06-18.jpg",
                        address = "ভালুকা মেজরভিটা কৃষি ব্যাংক সংলগ্ন",
                        phone = "01777-667372",
                        location = "ভালুকা",
                        averageRating = 4.6f,
                        ratingCount = 40,
                        userRating = 0
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        courierServices = courierServices,
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

    private fun handleRatingChange(courierService: CourierService, rating: Int) {
        viewModelScope.launch {
            // Update local state optimistically
            _uiState.update { state ->
                val updatedServices = state.courierServices.map { service ->
                    if (service.name == courierService.name) {
                        // Recalculate average rating
                        val totalRating = service.averageRating * service.ratingCount
                        val newTotalRating = if (service.userRating > 0) {
                            totalRating - service.userRating + rating
                        } else {
                            totalRating + rating
                        }
                        val newRatingCount = if (service.userRating > 0) {
                            service.ratingCount
                        } else {
                            service.ratingCount + 1
                        }
                        val newAverageRating = if (newRatingCount > 0) {
                            newTotalRating / newRatingCount
                        } else {
                            0f
                        }

                        service.copy(
                            userRating = rating,
                            averageRating = newAverageRating,
                            ratingCount = newRatingCount
                        )
                    } else {
                        service
                    }
                }
                state.copy(courierServices = updatedServices)
            }

            // TODO: Send rating to backend API
        }
    }
}
