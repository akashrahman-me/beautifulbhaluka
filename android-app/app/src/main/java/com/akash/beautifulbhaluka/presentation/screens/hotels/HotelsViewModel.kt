package com.akash.beautifulbhaluka.presentation.screens.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HotelsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HotelsUiState())
    val uiState: StateFlow<HotelsUiState> = _uiState.asStateFlow()

    init {
        onAction(HotelsAction.LoadData)
    }

    fun onAction(action: HotelsAction) {
        when (action) {
            is HotelsAction.LoadData -> loadData()
            is HotelsAction.OnHotelClick -> {
                // Handle hotel click (could navigate to detail screen or show more info)
            }

            is HotelsAction.OnPhoneClick -> {
                // Handle phone click (could open dialer)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val hotels = listOf(
                    Hotel(
                        name = "হোটেল নদী বাংলা",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/235889.png",
                        address = "ভালুকা পাচ রাস্তার মোড় ব্রীজ সংলগ্ন",
                        phone = listOf("01716-772146")
                    ),
                    Hotel(
                        name = "হোটেল উত্তরা",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/235889.png",
                        address = "ভালুকা বাসস্ট্যান্ডেের পশ্চিম পাশে",
                        phone = listOf("01711-189909", "+8801552-437912")
                    ),
                    Hotel(
                        name = "হোটেল নিরব",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/235889.png",
                        address = "সারাবেলা হক মার্কেট",
                        phone = emptyList()
                    ),
                    Hotel(
                        name = "সারাবেলা আবাসিক হোটেল",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/235889.png",
                        address = "নতুন বাসস্ট্যান্ড, ভালুকা",
                        phone = listOf("018550240095")
                    ),
                    Hotel(
                        name = "তানভীর গেস্ট হাউস",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/235889.png",
                        address = "সেভেন স্টারের সাথে",
                        phone = emptyList()
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hotels = hotels,
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
