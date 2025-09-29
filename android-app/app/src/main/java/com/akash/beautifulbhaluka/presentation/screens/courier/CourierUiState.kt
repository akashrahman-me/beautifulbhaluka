package com.akash.beautifulbhaluka.presentation.screens.courier

data class CourierUiState(
    val isLoading: Boolean = false,
    val courierServices: List<CourierService> = emptyList(),
    val error: String? = null
)

data class CourierService(
    val name: String,
    val thumbnail: String,
    val address: String,
    val phone: String
)

sealed interface CourierAction {
    object LoadData : CourierAction
    data class OnCourierClick(val courierService: CourierService) : CourierAction
    data class OnPhoneClick(val phoneNumber: String) : CourierAction
}
