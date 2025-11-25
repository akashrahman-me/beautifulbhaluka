package com.akash.beautifulbhaluka.presentation.screens.butchercook.publish

data class PublishButcherCookUiState(
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

sealed interface PublishButcherCookAction {
    data class UpdateName(val name: String) : PublishButcherCookAction
    data class UpdateAddress(val address: String) : PublishButcherCookAction
    data class UpdateLocation(val location: String) : PublishButcherCookAction
    data class UpdatePhone(val phone: String) : PublishButcherCookAction
    data class UpdateImage(val image: String) : PublishButcherCookAction
    object Submit : PublishButcherCookAction
    object ClearSuccess : PublishButcherCookAction
}

