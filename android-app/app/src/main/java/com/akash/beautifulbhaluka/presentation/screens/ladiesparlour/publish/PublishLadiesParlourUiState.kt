package com.akash.beautifulbhaluka.presentation.screens.ladiesparlour.publish

data class PublishLadiesParlourUiState(
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

sealed interface PublishLadiesParlourAction {
    data class UpdateName(val name: String) : PublishLadiesParlourAction
    data class UpdateAddress(val address: String) : PublishLadiesParlourAction
    data class UpdateLocation(val location: String) : PublishLadiesParlourAction
    data class UpdatePhone(val phone: String) : PublishLadiesParlourAction
    data class UpdateImage(val image: String) : PublishLadiesParlourAction
    object Submit : PublishLadiesParlourAction
    object ClearSuccess : PublishLadiesParlourAction
}

