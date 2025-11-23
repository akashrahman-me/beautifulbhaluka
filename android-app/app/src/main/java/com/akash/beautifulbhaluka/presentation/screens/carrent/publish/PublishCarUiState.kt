package com.akash.beautifulbhaluka.presentation.screens.carrent.publish

data class PublishCarUiState(
    val carName: String = "",
    val driverName: String = "",
    val mobile: String = "",
    val address: String = "",
    val location: String = "",
    val carType: String = "",
    val pricePerDay: String = "",
    val imageUrl: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPublished: Boolean = false
)

sealed interface PublishCarAction {
    data class OnCarNameChange(val name: String) : PublishCarAction
    data class OnDriverNameChange(val name: String) : PublishCarAction
    data class OnMobileChange(val mobile: String) : PublishCarAction
    data class OnAddressChange(val address: String) : PublishCarAction
    data class OnLocationChange(val location: String) : PublishCarAction
    data class OnCarTypeChange(val type: String) : PublishCarAction
    data class OnPriceChange(val price: String) : PublishCarAction
    data class OnImageSelect(val imageUrl: String) : PublishCarAction
    data object OnPublish : PublishCarAction
    data object ClearError : PublishCarAction
}

