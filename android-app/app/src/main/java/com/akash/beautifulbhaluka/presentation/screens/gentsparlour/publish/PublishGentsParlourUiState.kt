package com.akash.beautifulbhaluka.presentation.screens.gentsparlour.publish

data class PublishGentsParlourUiState(
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

sealed interface PublishGentsParlourAction {
    data class UpdateName(val name: String) : PublishGentsParlourAction
    data class UpdateAddress(val address: String) : PublishGentsParlourAction
    data class UpdateLocation(val location: String) : PublishGentsParlourAction
    data class UpdatePhone(val phone: String) : PublishGentsParlourAction
    data class UpdateImage(val image: String) : PublishGentsParlourAction
    object Submit : PublishGentsParlourAction
    object ClearSuccess : PublishGentsParlourAction
}

