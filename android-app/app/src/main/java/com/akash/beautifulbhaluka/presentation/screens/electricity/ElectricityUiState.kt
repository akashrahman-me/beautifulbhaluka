package com.akash.beautifulbhaluka.presentation.screens.electricity

data class ElectricityUiState(
    val isLoading: Boolean = false,
    val electricityOffices: List<ElectricityOffice> = emptyList(),
    val error: String? = null
)

data class ElectricityOffice(
    val name: String,
    val office: String,
    val phone: String,
    val image: String
)

sealed interface ElectricityAction {
    object LoadData : ElectricityAction
    data class CallOffice(val phone: String) : ElectricityAction
}
