package com.akash.beautifulbhaluka.presentation.screens.hotels.publish

data class PublishHotelUiState(
    val name: String = "",
    val address: String = "",
    val location: String = "",
    val phone: String = "",
    val image: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val nameError: String? = null,
    val addressError: String? = null,
    val locationError: String? = null,
    val phoneError: String? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() &&
                address.isNotBlank() &&
                location.isNotBlank() &&
                phone.isNotBlank() &&
                nameError == null &&
                addressError == null &&
                locationError == null &&
                phoneError == null
}

sealed interface PublishHotelAction {
    data class UpdateName(val name: String) : PublishHotelAction
    data class UpdateAddress(val address: String) : PublishHotelAction
    data class UpdateLocation(val location: String) : PublishHotelAction
    data class UpdatePhone(val phone: String) : PublishHotelAction
    data class UpdateImage(val image: String) : PublishHotelAction
    object Submit : PublishHotelAction
    object ClearSuccess : PublishHotelAction
}

