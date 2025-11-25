package com.akash.beautifulbhaluka.presentation.screens.entertainment.publish

import com.akash.beautifulbhaluka.presentation.screens.entertainment.EntertainmentType

data class PublishEntertainmentUiState(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val image: String = "",
    val entertainmentType: EntertainmentType = EntertainmentType.SOUND_SYSTEM,
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

sealed interface PublishEntertainmentAction {
    data class UpdateName(val name: String) : PublishEntertainmentAction
    data class UpdateAddress(val address: String) : PublishEntertainmentAction
    data class UpdatePhone(val phone: String) : PublishEntertainmentAction
    data class UpdateImage(val image: String) : PublishEntertainmentAction
    data class UpdateEntertainmentType(val type: EntertainmentType) : PublishEntertainmentAction
    object Submit : PublishEntertainmentAction
    object ClearSuccess : PublishEntertainmentAction
}

