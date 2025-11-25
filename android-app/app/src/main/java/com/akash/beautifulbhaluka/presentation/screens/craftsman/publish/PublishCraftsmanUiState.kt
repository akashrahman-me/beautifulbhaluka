package com.akash.beautifulbhaluka.presentation.screens.craftsman.publish

import com.akash.beautifulbhaluka.presentation.screens.craftsman.CraftsmanType

data class PublishCraftsmanUiState(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val image: String = "",
    val craftsmanType: CraftsmanType = CraftsmanType.ELECTRICIAN,
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val nameError: String? = null,
    val addressError: String? = null,
    val phoneError: String? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() &&
                address.isNotBlank() &&
                phone.isNotBlank() &&
                nameError == null &&
                addressError == null &&
                phoneError == null
}

sealed interface PublishCraftsmanAction {
    data class UpdateName(val name: String) : PublishCraftsmanAction
    data class UpdateAddress(val address: String) : PublishCraftsmanAction
    data class UpdatePhone(val phone: String) : PublishCraftsmanAction
    data class UpdateImage(val image: String) : PublishCraftsmanAction
    data class UpdateCraftsmanType(val type: CraftsmanType) : PublishCraftsmanAction
    object Submit : PublishCraftsmanAction
    object ClearSuccess : PublishCraftsmanAction
}

