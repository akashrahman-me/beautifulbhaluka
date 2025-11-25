package com.akash.beautifulbhaluka.presentation.screens.gentsparlour

data class GentsParlourUiState(
    val isLoading: Boolean = false,
    val parlours: List<GentsParlour> = emptyList(),
    val error: String? = null
)

data class GentsParlour(
    val id: String,
    val title: String,
    val image: String,
    val description: String = "",
    val address: String = "",
    val phones: List<String> = emptyList(),
    val averageRating: Float = 0f,
    val ratingCount: Int = 0,
    val userRating: Int = 0
)

sealed interface GentsParlourAction {
    object LoadData : GentsParlourAction
    data class CallPhone(val phoneNumber: String) : GentsParlourAction
    data class RateParlour(val parlourId: String, val rating: Int) : GentsParlourAction
}
