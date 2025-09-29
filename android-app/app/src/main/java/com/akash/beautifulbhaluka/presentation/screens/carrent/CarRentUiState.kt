package com.akash.beautifulbhaluka.presentation.screens.carrent

data class CarRentUiState(
    val isLoading: Boolean = false,
    val carRentServices: List<CarRentService> = emptyList(),
    val drivers: List<Driver> = emptyList(),
    val error: String? = null
)

data class CarRentService(
    val name: String,
    val thumbnail: String,
    val description: String,
    val phone: List<String>
)

data class Driver(
    val serialNumber: String,
    val name: String,
    val phoneNumber: String
)

sealed interface CarRentAction {
    object LoadData : CarRentAction
    data class OnCarRentServiceClick(val service: CarRentService) : CarRentAction
    data class OnPhoneClick(val phoneNumber: String) : CarRentAction
}
