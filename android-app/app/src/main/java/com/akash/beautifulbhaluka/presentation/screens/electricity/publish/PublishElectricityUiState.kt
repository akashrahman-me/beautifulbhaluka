package com.akash.beautifulbhaluka.presentation.screens.electricity.publish

data class PublishElectricityUiState(
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

sealed interface PublishElectricityAction {
    data class UpdateName(val name: String) : PublishElectricityAction
    data class UpdateAddress(val address: String) : PublishElectricityAction
    data class UpdateLocation(val location: String) : PublishElectricityAction
    data class UpdatePhone(val phone: String) : PublishElectricityAction
    data class UpdateImage(val image: String) : PublishElectricityAction
    object Submit : PublishElectricityAction
    object ClearSuccess : PublishElectricityAction
}

