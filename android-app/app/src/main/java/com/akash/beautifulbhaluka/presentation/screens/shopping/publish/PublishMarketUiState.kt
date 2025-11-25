package com.akash.beautifulbhaluka.presentation.screens.shopping.publish

data class PublishMarketUiState(
    val marketName: String = "",
    val address: String = "",
    val marketDays: String = "",
    val image: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val marketNameError: String? = null,
    val addressError: String? = null,
    val marketDaysError: String? = null
) {
    val isValid: Boolean
        get() = marketName.isNotBlank() &&
                address.isNotBlank() &&
                marketDays.isNotBlank() &&
                marketNameError == null &&
                addressError == null &&
                marketDaysError == null
}

sealed interface PublishMarketAction {
    data class UpdateMarketName(val name: String) : PublishMarketAction
    data class UpdateAddress(val address: String) : PublishMarketAction
    data class UpdateMarketDays(val days: String) : PublishMarketAction
    data class UpdateImage(val image: String) : PublishMarketAction
    object Submit : PublishMarketAction
    object ClearSuccess : PublishMarketAction
}

