package com.akash.beautifulbhaluka.presentation.screens.cleaner.publish

data class PublishCleanerUiState(
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

sealed interface PublishCleanerAction {
    data class UpdateName(val name: String) : PublishCleanerAction
    data class UpdateAddress(val address: String) : PublishCleanerAction
    data class UpdateLocation(val location: String) : PublishCleanerAction
    data class UpdatePhone(val phone: String) : PublishCleanerAction
    data class UpdateImage(val image: String) : PublishCleanerAction
    object Submit : PublishCleanerAction
    object ClearSuccess : PublishCleanerAction
}

