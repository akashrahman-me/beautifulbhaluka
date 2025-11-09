package com.akash.beautifulbhaluka.presentation.screens.tuition.publish

data class PublishTutorUiState(
    val name: String = "",
    val qualification: String = "",
    val experience: String = "",
    val subjects: List<String> = emptyList(),
    val classes: List<String> = emptyList(),
    val preferredLocations: List<String> = emptyList(),
    val preferredMedium: List<String> = emptyList(),
    val fee: String = "",
    val availability: String = "",
    val contactNumber: String = "",
    val description: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

sealed class PublishTutorAction {
    data class UpdateName(val name: String) : PublishTutorAction()
    data class UpdateQualification(val qualification: String) : PublishTutorAction()
    data class UpdateExperience(val experience: String) : PublishTutorAction()
    data class UpdateSubject(val subject: String, val selected: Boolean) : PublishTutorAction()
    data class UpdateClass(val className: String, val selected: Boolean) : PublishTutorAction()
    data class UpdateLocation(val location: String, val selected: Boolean) : PublishTutorAction()
    data class UpdateMedium(val medium: String, val selected: Boolean) : PublishTutorAction()
    data class UpdateFee(val fee: String) : PublishTutorAction()
    data class UpdateAvailability(val availability: String) : PublishTutorAction()
    data class UpdateContact(val contact: String) : PublishTutorAction()
    data class UpdateDescription(val description: String) : PublishTutorAction()
    object Submit : PublishTutorAction()
}

