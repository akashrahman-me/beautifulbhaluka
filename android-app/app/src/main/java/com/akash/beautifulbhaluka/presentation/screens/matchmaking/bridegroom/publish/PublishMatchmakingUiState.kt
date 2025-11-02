package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.publish

import android.net.Uri

data class PublishMatchmakingUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val age: String = "",
    val gender: String = "Male",
    val occupation: String = "",
    val education: String = "",
    val location: String = "",
    val height: String = "",
    val religion: String = "Islam",
    val maritalStatus: String = "Never Married",
    val bio: String = "",
    val interests: List<String> = emptyList(),
    val phoneNumber: String = "",
    val email: String = "",
    val selectedImageUri: Uri? = null,
    val savedImagePath: String? = null,
    val isUploadingImage: Boolean = false,
    val currentInterest: String = "",
    val error: String? = null,
    val isPublishing: Boolean = false,
    val publishSuccess: Boolean = false,
    val validationErrors: Map<String, String> = emptyMap()
)

sealed class PublishMatchmakingAction {
    data class UpdateName(val name: String) : PublishMatchmakingAction()
    data class UpdateAge(val age: String) : PublishMatchmakingAction()
    data class UpdateGender(val gender: String) : PublishMatchmakingAction()
    data class UpdateOccupation(val occupation: String) : PublishMatchmakingAction()
    data class UpdateEducation(val education: String) : PublishMatchmakingAction()
    data class UpdateLocation(val location: String) : PublishMatchmakingAction()
    data class UpdateHeight(val height: String) : PublishMatchmakingAction()
    data class UpdateReligion(val religion: String) : PublishMatchmakingAction()
    data class UpdateMaritalStatus(val maritalStatus: String) : PublishMatchmakingAction()
    data class UpdateBio(val bio: String) : PublishMatchmakingAction()
    data class UpdateCurrentInterest(val interest: String) : PublishMatchmakingAction()
    data class AddInterest(val interest: String) : PublishMatchmakingAction()
    data class RemoveInterest(val interest: String) : PublishMatchmakingAction()
    data class UpdatePhoneNumber(val phoneNumber: String) : PublishMatchmakingAction()
    data class UpdateEmail(val email: String) : PublishMatchmakingAction()
    data class SelectImage(val uri: Uri?) : PublishMatchmakingAction()
    object PublishProfile : PublishMatchmakingAction()
    object ClearError : PublishMatchmakingAction()
    object ResetForm : PublishMatchmakingAction()
}

