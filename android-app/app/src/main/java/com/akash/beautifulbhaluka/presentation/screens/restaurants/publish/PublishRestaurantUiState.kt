package com.akash.beautifulbhaluka.presentation.screens.restaurants.publish

data class PublishRestaurantUiState(
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

sealed interface PublishRestaurantAction {
    data class UpdateName(val name: String) : PublishRestaurantAction
    data class UpdateAddress(val address: String) : PublishRestaurantAction
    data class UpdateLocation(val location: String) : PublishRestaurantAction
    data class UpdatePhone(val phone: String) : PublishRestaurantAction
    data class UpdateImage(val image: String) : PublishRestaurantAction
    object Submit : PublishRestaurantAction
    object ClearSuccess : PublishRestaurantAction
}

