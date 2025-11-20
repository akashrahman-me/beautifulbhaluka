package com.akash.beautifulbhaluka.presentation.screens.lawyer.publish

data class PublishLawyerUiState(
    val name: String = "",
    val designation: String = "",
    val phone: String = "",
    val image: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val nameError: String? = null,
    val designationError: String? = null,
    val phoneError: String? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() &&
                designation.isNotBlank() &&
                phone.isNotBlank() &&
                nameError == null &&
                designationError == null &&
                phoneError == null
}

