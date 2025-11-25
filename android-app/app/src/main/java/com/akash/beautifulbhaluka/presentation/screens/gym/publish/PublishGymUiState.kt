package com.akash.beautifulbhaluka.presentation.screens.gym.publish

data class PublishGymUiState(
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

sealed interface PublishGymAction {
    data class UpdateName(val name: String) : PublishGymAction
    data class UpdateAddress(val address: String) : PublishGymAction
    data class UpdateLocation(val location: String) : PublishGymAction
    data class UpdatePhone(val phone: String) : PublishGymAction
    data class UpdateImage(val image: String) : PublishGymAction
    object Submit : PublishGymAction
    object ClearSuccess : PublishGymAction
}

