package com.akash.beautifulbhaluka.presentation.screens.directory.publish

data class PublishDirectoryUiState(
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

sealed interface PublishDirectoryAction {
    data class UpdateName(val name: String) : PublishDirectoryAction
    data class UpdateAddress(val address: String) : PublishDirectoryAction
    data class UpdateLocation(val location: String) : PublishDirectoryAction
    data class UpdatePhone(val phone: String) : PublishDirectoryAction
    data class UpdateImage(val image: String) : PublishDirectoryAction
    object Submit : PublishDirectoryAction
    object ClearSuccess : PublishDirectoryAction
}

