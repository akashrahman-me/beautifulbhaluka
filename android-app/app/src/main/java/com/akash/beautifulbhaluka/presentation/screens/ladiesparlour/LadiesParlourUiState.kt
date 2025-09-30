package com.akash.beautifulbhaluka.presentation.screens.ladiesparlour

data class LadiesParlourUiState(
    val isLoading: Boolean = false,
    val parlours: List<BeautyParlour> = emptyList(),
    val error: String? = null
)

data class BeautyParlour(
    val name: String,
    val address: String?,
    val phone: String?,
    val image: String
)

sealed interface LadiesParlourAction {
    object LoadData : LadiesParlourAction
    data class CallParlour(val phone: String) : LadiesParlourAction
}
