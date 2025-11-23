package com.akash.beautifulbhaluka.presentation.screens.hotels

data class HotelsUiState(
    val isLoading: Boolean = false,
    val hotels: List<Hotel> = emptyList(),
    val error: String? = null
)

data class Hotel(
    val name: String,
    val thumbnail: String,
    val address: String,
    val phone: List<String> = emptyList(),
    val averageRating: Float = 0f,
    val ratingCount: Int = 0,
    val userRating: Int = 0
)

sealed interface HotelsAction {
    object LoadData : HotelsAction
    data class OnHotelClick(val hotel: Hotel) : HotelsAction
    data class OnPhoneClick(val phoneNumber: String) : HotelsAction
    data class OnRatingChange(val hotel: Hotel, val rating: Int) : HotelsAction
    object OnPublishClick : HotelsAction
}
