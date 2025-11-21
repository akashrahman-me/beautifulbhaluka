package com.akash.beautifulbhaluka.presentation.screens.schoolcollege.publish

import com.akash.beautifulbhaluka.presentation.screens.schoolcollege.InstitutionCategory

data class PublishInstitutionUiState(
    val imageUri: String = "",
    val name: String = "",
    val category: InstitutionCategory = InstitutionCategory.SCHOOL,
    val scope: String = "",
    val establishedYear: String = "",
    val eiin: String = "",
    val location: String = "",
    val mobile: String = "",
    val description: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class PublishInstitutionAction {
    data class UpdateImage(val uri: String) : PublishInstitutionAction()
    data class UpdateName(val name: String) : PublishInstitutionAction()
    data class UpdateCategory(val category: InstitutionCategory) : PublishInstitutionAction()
    data class UpdateScope(val scope: String) : PublishInstitutionAction()
    data class UpdateEstablishedYear(val year: String) : PublishInstitutionAction()
    data class UpdateEiin(val eiin: String) : PublishInstitutionAction()
    data class UpdateLocation(val location: String) : PublishInstitutionAction()
    data class UpdateMobile(val mobile: String) : PublishInstitutionAction()
    data class UpdateDescription(val description: String) : PublishInstitutionAction()
    object Submit : PublishInstitutionAction()
}

