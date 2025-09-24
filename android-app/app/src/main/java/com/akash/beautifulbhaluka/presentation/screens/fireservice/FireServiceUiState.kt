package com.akash.beautifulbhaluka.presentation.screens.fireservice

data class FireServiceUiState(
    val isLoading: Boolean = false,
    val fireServiceInfo: com.akash.beautifulbhaluka.domain.model.FireServiceInfo? = null,
    val error: String? = null
)

sealed class FireServiceAction {
    object LoadData : FireServiceAction()
    data class CallNumber(val phoneNumber: String) : FireServiceAction()
}
