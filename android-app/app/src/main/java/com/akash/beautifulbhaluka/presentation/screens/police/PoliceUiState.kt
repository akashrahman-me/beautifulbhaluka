package com.akash.beautifulbhaluka.presentation.screens.police

data class PoliceUiState(
    val isLoading: Boolean = false,
    val policeInfo: com.akash.beautifulbhaluka.domain.model.PoliceInfo? = null,
    val error: String? = null
)

sealed class PoliceAction {
    object LoadData : PoliceAction()
    data class CallNumber(val phoneNumber: String) : PoliceAction()
}
