package com.akash.beautifulbhaluka.presentation.screens.matchmaking.publish

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.usecase.SaveProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishMatchmakingViewModel @Inject constructor(
    private val saveProfileImageUseCase: SaveProfileImageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PublishMatchmakingUiState())
    val uiState: StateFlow<PublishMatchmakingUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishMatchmakingAction) {
        when (action) {
            is PublishMatchmakingAction.UpdateName -> updateName(action.name)
            is PublishMatchmakingAction.UpdateAge -> updateAge(action.age)
            is PublishMatchmakingAction.UpdateGender -> updateGender(action.gender)
            is PublishMatchmakingAction.UpdateOccupation -> updateOccupation(action.occupation)
            is PublishMatchmakingAction.UpdateEducation -> updateEducation(action.education)
            is PublishMatchmakingAction.UpdateLocation -> updateLocation(action.location)
            is PublishMatchmakingAction.UpdateHeight -> updateHeight(action.height)
            is PublishMatchmakingAction.UpdateReligion -> updateReligion(action.religion)
            is PublishMatchmakingAction.UpdateMaritalStatus -> updateMaritalStatus(action.maritalStatus)
            is PublishMatchmakingAction.UpdateBio -> updateBio(action.bio)
            is PublishMatchmakingAction.UpdateCurrentInterest -> updateCurrentInterest(action.interest)
            is PublishMatchmakingAction.AddInterest -> addInterest(action.interest)
            is PublishMatchmakingAction.RemoveInterest -> removeInterest(action.interest)
            is PublishMatchmakingAction.UpdatePhoneNumber -> updatePhoneNumber(action.phoneNumber)
            is PublishMatchmakingAction.UpdateEmail -> updateEmail(action.email)
            is PublishMatchmakingAction.SelectImage -> selectImage(action.uri)
            is PublishMatchmakingAction.PublishProfile -> publishProfile()
            is PublishMatchmakingAction.ClearError -> clearError()
            is PublishMatchmakingAction.ResetForm -> resetForm()
        }
    }

    private fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    private fun updateAge(age: String) {
        _uiState.update { it.copy(age = age) }
    }

    private fun updateGender(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    private fun updateOccupation(occupation: String) {
        _uiState.update { it.copy(occupation = occupation) }
    }

    private fun updateEducation(education: String) {
        _uiState.update { it.copy(education = education) }
    }

    private fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    private fun updateHeight(height: String) {
        _uiState.update { it.copy(height = height) }
    }

    private fun updateReligion(religion: String) {
        _uiState.update { it.copy(religion = religion) }
    }

    private fun updateMaritalStatus(maritalStatus: String) {
        _uiState.update { it.copy(maritalStatus = maritalStatus) }
    }

    private fun updateBio(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }

    private fun updateCurrentInterest(interest: String) {
        _uiState.update { it.copy(currentInterest = interest) }
    }

    private fun addInterest(interest: String) {
        val trimmedInterest = interest.trim()
        if (trimmedInterest.isNotEmpty() && !_uiState.value.interests.contains(trimmedInterest)) {
            _uiState.update {
                it.copy(
                    interests = it.interests + trimmedInterest,
                    currentInterest = ""
                )
            }
        }
    }

    private fun removeInterest(interest: String) {
        _uiState.update {
            it.copy(interests = it.interests - interest)
        }
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun selectImage(uri: Uri?) {
        if (uri == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isUploadingImage = true) }

            saveProfileImageUseCase(uri)
                .onSuccess { savedPath ->
                    _uiState.update {
                        it.copy(
                            selectedImageUri = uri,
                            savedImagePath = savedPath,
                            isUploadingImage = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isUploadingImage = false,
                            error = "Failed to save image: ${error.message}"
                        )
                    }
                }
        }
    }

    private fun publishProfile() {
        val validationErrors = validateForm()
        if (validationErrors.isNotEmpty()) {
            _uiState.update { it.copy(validationErrors = validationErrors) }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isPublishing = true,
                    error = null,
                    validationErrors = emptyMap()
                )
            }
            try {
                // TODO: Implement actual API call
                delay(2000) // Simulate network call
                _uiState.update {
                    it.copy(
                        isPublishing = false,
                        publishSuccess = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isPublishing = false,
                        error = e.message ?: "Failed to publish profile"
                    )
                }
            }
        }
    }

    private fun validateForm(): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (_uiState.value.name.isBlank()) {
            errors["name"] = "Name is required"
        }

        if (_uiState.value.age.isBlank()) {
            errors["age"] = "Age is required"
        } else {
            val ageInt = _uiState.value.age.toIntOrNull()
            if (ageInt == null || ageInt < 18 || ageInt > 100) {
                errors["age"] = "Please enter a valid age (18-100)"
            }
        }

        if (_uiState.value.occupation.isBlank()) {
            errors["occupation"] = "Occupation is required"
        }

        if (_uiState.value.education.isBlank()) {
            errors["education"] = "Education is required"
        }

        if (_uiState.value.location.isBlank()) {
            errors["location"] = "Location is required"
        }

        if (_uiState.value.bio.isBlank()) {
            errors["bio"] = "Bio is required"
        }

        if (_uiState.value.phoneNumber.isNotBlank() && !isValidPhoneNumber(_uiState.value.phoneNumber)) {
            errors["phoneNumber"] = "Please enter a valid phone number"
        }

        if (_uiState.value.email.isNotBlank() && !isValidEmail(_uiState.value.email)) {
            errors["email"] = "Please enter a valid email"
        }

        return errors
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.matches(Regex("^[+]?[0-9]{10,15}$"))
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null, validationErrors = emptyMap()) }
    }

    private fun resetForm() {
        _uiState.value = PublishMatchmakingUiState()
    }
}

