package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.publish

import android.net.Uri

data class PublishMatchmakerUiState(
    val isPublishing: Boolean = false,
    val publishSuccess: Boolean = false,
    val error: String? = null,

    // Profile Photo
    val selectedImageUri: Uri? = null,
    val savedImagePath: String? = null,
    val isUploadingImage: Boolean = false,

    // Basic Information
    val name: String = "",
    val age: String = "",
    val experience: String = "",
    val location: String = "",
    val bio: String = "",

    // Contact Information
    val contactNumber: String = "",
    val whatsapp: String = "",
    val email: String = "",
    val workingHours: String = "",

    // Professional Information
    val selectedSpecializations: Set<String> = emptySet(),
    val selectedServices: Set<String> = emptySet(),
    val selectedLanguages: Set<String> = emptySet(),
    val consultationFee: String = "",

    // Social Media
    val facebook: String = "",
    val instagram: String = "",
    val linkedin: String = "",
    val website: String = "",

    // Validation
    val nameError: String? = null,
    val ageError: String? = null,
    val experienceError: String? = null,
    val locationError: String? = null,
    val contactNumberError: String? = null,
    val bioError: String? = null
)

sealed class PublishMatchmakerAction {
    data class SelectImage(val uri: Uri?) : PublishMatchmakerAction()
    data class UpdateName(val name: String) : PublishMatchmakerAction()
    data class UpdateAge(val age: String) : PublishMatchmakerAction()
    data class UpdateExperience(val experience: String) : PublishMatchmakerAction()
    data class UpdateLocation(val location: String) : PublishMatchmakerAction()
    data class UpdateBio(val bio: String) : PublishMatchmakerAction()
    data class UpdateContactNumber(val contactNumber: String) : PublishMatchmakerAction()
    data class UpdateWhatsApp(val whatsapp: String) : PublishMatchmakerAction()
    data class UpdateEmail(val email: String) : PublishMatchmakerAction()
    data class UpdateWorkingHours(val workingHours: String) : PublishMatchmakerAction()
    data class UpdateConsultationFee(val fee: String) : PublishMatchmakerAction()
    data class ToggleSpecialization(val specialization: String) : PublishMatchmakerAction()
    data class ToggleService(val service: String) : PublishMatchmakerAction()
    data class ToggleLanguage(val language: String) : PublishMatchmakerAction()
    data class UpdateFacebook(val facebook: String) : PublishMatchmakerAction()
    data class UpdateInstagram(val instagram: String) : PublishMatchmakerAction()
    data class UpdateLinkedIn(val linkedin: String) : PublishMatchmakerAction()
    data class UpdateWebsite(val website: String) : PublishMatchmakerAction()
    object Publish : PublishMatchmakerAction()
    object ResetPublishSuccess : PublishMatchmakerAction()
}

