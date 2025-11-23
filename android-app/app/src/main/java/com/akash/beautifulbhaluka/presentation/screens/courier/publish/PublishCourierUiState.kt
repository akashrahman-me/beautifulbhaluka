package com.akash.beautifulbhaluka.presentation.screens.courier.publish

data class PublishCourierUiState(
    val name: String = "",
    val address: String = "",
    val location: String = "",
    val phone: String = "",
    val image: String = "",

    val nameError: String? = null,
    val addressError: String? = null,
    val locationError: String? = null,
    val phoneError: String? = null,

    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
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

sealed interface PublishCourierAction {
    data class UpdateName(val name: String) : PublishCourierAction
    data class UpdateAddress(val address: String) : PublishCourierAction
    data class UpdateLocation(val location: String) : PublishCourierAction
    data class UpdatePhone(val phone: String) : PublishCourierAction
    data class UpdateImage(val image: String) : PublishCourierAction
    object Submit : PublishCourierAction
}

