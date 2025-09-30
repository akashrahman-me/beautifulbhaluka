package com.akash.beautifulbhaluka.presentation.screens.broadband

data class BroadbandUiState(
    val isLoading: Boolean = false,
    val broadbandServices: List<BroadbandService> = emptyList(),
    val error: String? = null
)

data class BroadbandService(
    val name: String,
    val verified: Boolean,
    val image: String?,
    val details: String,
    val phone: String,
    val address: String,
    val rating: String
)

sealed interface BroadbandAction {
    object LoadData : BroadbandAction
    data class CallService(val phone: String) : BroadbandAction
}
