package com.akash.beautifulbhaluka.presentation.screens.ladiesparlour

data class LadiesParlourUiState(
    val isLoading: Boolean = false,
    val parlours: List<BeautyParlour> = emptyList(),
    val error: String? = null
)

data class BeautyParlour(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val image: String,
    val averageRating: Float = 0f,
    val ratingCount: Int = 0,
    val userRating: Int = 0
)

sealed interface LadiesParlourAction {
    object LoadData : LadiesParlourAction
    data class CallParlour(val phone: String) : LadiesParlourAction
    data class RateParlour(val parlourId: String, val rating: Int) : LadiesParlourAction
}
