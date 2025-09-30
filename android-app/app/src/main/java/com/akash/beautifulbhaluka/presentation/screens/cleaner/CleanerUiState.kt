package com.akash.beautifulbhaluka.presentation.screens.cleaner

data class CleanerUiState(
    val isLoading: Boolean = false,
    val cleaners: List<Cleaner> = emptyList(),
    val error: String? = null
)

data class Cleaner(
    val name: String,
    val role: String,
    val phone: String,
    val image: String
)

sealed interface CleanerAction {
    object LoadData : CleanerAction
    data class CallCleaner(val phone: String) : CleanerAction
}
