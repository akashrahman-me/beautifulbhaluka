package com.akash.beautifulbhaluka.presentation.screens.kazioffice

data class KaziOfficeUiState(
    val isLoading: Boolean = false,
    val kazis: List<Kazi> = emptyList(),
    val error: String? = null
)

data class Kazi(
    val name: String,
    val address: String,
    val phone: String,
    val image: String
)

sealed interface KaziOfficeAction {
    object LoadData : KaziOfficeAction
    data class CallPhone(val phoneNumber: String) : KaziOfficeAction
}
