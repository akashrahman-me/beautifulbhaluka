package com.akash.beautifulbhaluka.presentation.screens.bloodbank.publish

data class PublishDonorUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val fullName: String = "",
    val mobileNumber: String = "",
    val bloodGroup: String = "",
    val address: String = "",
    val facebookLink: String = "",
    val whatsappNumber: String = "",
    val isBloodGroupDropdownExpanded: Boolean = false,
    val validationErrors: ValidationErrors = ValidationErrors()
)

data class ValidationErrors(
    val fullName: String? = null,
    val mobileNumber: String? = null,
    val bloodGroup: String? = null,
    val address: String? = null
)

sealed class PublishDonorAction {
    data class UpdateFullName(val name: String) : PublishDonorAction()
    data class UpdateMobileNumber(val number: String) : PublishDonorAction()
    data class UpdateBloodGroup(val group: String) : PublishDonorAction()
    data class UpdateAddress(val address: String) : PublishDonorAction()
    data class UpdateFacebookLink(val link: String) : PublishDonorAction()
    data class UpdateWhatsAppNumber(val number: String) : PublishDonorAction()
    data class SetBloodGroupDropdownExpanded(val expanded: Boolean) : PublishDonorAction()
    object Submit : PublishDonorAction()
    object ClearError : PublishDonorAction()
}

fun getBloodGroups(): List<String> = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

