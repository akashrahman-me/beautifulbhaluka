package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishMatchmakerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PublishMatchmakerUiState())
    val uiState: StateFlow<PublishMatchmakerUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishMatchmakerAction) {
        when (action) {
            is PublishMatchmakerAction.SelectImage -> selectImage(action.uri)
            is PublishMatchmakerAction.UpdateName -> updateName(action.name)
            is PublishMatchmakerAction.UpdateAge -> updateAge(action.age)
            is PublishMatchmakerAction.UpdateExperience -> updateExperience(action.experience)
            is PublishMatchmakerAction.UpdateLocation -> updateLocation(action.location)
            is PublishMatchmakerAction.UpdateBio -> updateBio(action.bio)
            is PublishMatchmakerAction.UpdateContactNumber -> updateContactNumber(action.contactNumber)
            is PublishMatchmakerAction.UpdateWhatsApp -> updateWhatsApp(action.whatsapp)
            is PublishMatchmakerAction.UpdateEmail -> updateEmail(action.email)
            is PublishMatchmakerAction.UpdateWorkingHours -> updateWorkingHours(action.workingHours)
            is PublishMatchmakerAction.UpdateConsultationFee -> updateConsultationFee(action.fee)
            is PublishMatchmakerAction.ToggleSpecialization -> toggleSpecialization(action.specialization)
            is PublishMatchmakerAction.ToggleService -> toggleService(action.service)
            is PublishMatchmakerAction.ToggleLanguage -> toggleLanguage(action.language)
            is PublishMatchmakerAction.UpdateFacebook -> updateFacebook(action.facebook)
            is PublishMatchmakerAction.UpdateInstagram -> updateInstagram(action.instagram)
            is PublishMatchmakerAction.UpdateLinkedIn -> updateLinkedIn(action.linkedin)
            is PublishMatchmakerAction.UpdateWebsite -> updateWebsite(action.website)
            is PublishMatchmakerAction.Publish -> publish()
            is PublishMatchmakerAction.ResetPublishSuccess -> resetPublishSuccess()
        }
    }

    private fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    private fun selectImage(uri: android.net.Uri?) {
        if (uri == null) {
            _uiState.update { it.copy(selectedImageUri = null, savedImagePath = null) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isUploadingImage = true) }

            try {
                delay(1000) // Simulate image processing/upload

                // Here you would typically:
                // 1. Save the image to local storage or upload to server
                // 2. Get the saved path or URL
                val savedPath = "path/to/saved/image_${System.currentTimeMillis()}.jpg"

                _uiState.update {
                    it.copy(
                        selectedImageUri = uri,
                        savedImagePath = savedPath,
                        isUploadingImage = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isUploadingImage = false,
                        error = "Failed to upload image: ${e.message}"
                    )
                }
            }
        }
    }

    private fun updateAge(age: String) {
        _uiState.update { it.copy(age = age, ageError = null) }
    }

    private fun updateExperience(experience: String) {
        _uiState.update { it.copy(experience = experience, experienceError = null) }
    }

    private fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location, locationError = null) }
    }

    private fun updateBio(bio: String) {
        _uiState.update { it.copy(bio = bio, bioError = null) }
    }

    private fun updateContactNumber(contactNumber: String) {
        _uiState.update { it.copy(contactNumber = contactNumber, contactNumberError = null) }
    }

    private fun updateWhatsApp(whatsapp: String) {
        _uiState.update { it.copy(whatsapp = whatsapp) }
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun updateWorkingHours(workingHours: String) {
        _uiState.update { it.copy(workingHours = workingHours) }
    }

    private fun updateConsultationFee(fee: String) {
        _uiState.update { it.copy(consultationFee = fee) }
    }

    private fun toggleSpecialization(specialization: String) {
        _uiState.update { state ->
            val updated = if (state.selectedSpecializations.contains(specialization)) {
                state.selectedSpecializations - specialization
            } else {
                state.selectedSpecializations + specialization
            }
            state.copy(selectedSpecializations = updated)
        }
    }

    private fun toggleService(service: String) {
        _uiState.update { state ->
            val updated = if (state.selectedServices.contains(service)) {
                state.selectedServices - service
            } else {
                state.selectedServices + service
            }
            state.copy(selectedServices = updated)
        }
    }

    private fun toggleLanguage(language: String) {
        _uiState.update { state ->
            val updated = if (state.selectedLanguages.contains(language)) {
                state.selectedLanguages - language
            } else {
                state.selectedLanguages + language
            }
            state.copy(selectedLanguages = updated)
        }
    }

    private fun updateFacebook(facebook: String) {
        _uiState.update { it.copy(facebook = facebook) }
    }

    private fun updateInstagram(instagram: String) {
        _uiState.update { it.copy(instagram = instagram) }
    }

    private fun updateLinkedIn(linkedin: String) {
        _uiState.update { it.copy(linkedin = linkedin) }
    }

    private fun updateWebsite(website: String) {
        _uiState.update { it.copy(website = website) }
    }

    private fun resetPublishSuccess() {
        _uiState.update { it.copy(publishSuccess = false) }
    }

    private fun publish() {
        if (!validateForm()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isPublishing = true, error = null) }
            delay(2000) // Simulate network call

            // Here you would call the repository to save the matchmaker profile
            // For now, we'll just simulate success
            _uiState.update {
                it.copy(
                    isPublishing = false,
                    publishSuccess = true
                )
            }
        }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        var isValid = true

        if (state.name.isBlank()) {
            _uiState.update { it.copy(nameError = "Name is required") }
            isValid = false
        }

        if (state.age.isBlank()) {
            _uiState.update { it.copy(ageError = "Age is required") }
            isValid = false
        } else {
            val ageInt = state.age.toIntOrNull()
            if (ageInt == null || ageInt < 18 || ageInt > 100) {
                _uiState.update { it.copy(ageError = "Age must be between 18 and 100") }
                isValid = false
            }
        }

        if (state.experience.isBlank()) {
            _uiState.update { it.copy(experienceError = "Experience is required") }
            isValid = false
        }

        if (state.location.isBlank()) {
            _uiState.update { it.copy(locationError = "Location is required") }
            isValid = false
        }

        if (state.contactNumber.isBlank()) {
            _uiState.update { it.copy(contactNumberError = "Contact number is required") }
            isValid = false
        } else if (!state.contactNumber.matches(Regex("^01[0-9]{9}$"))) {
            _uiState.update { it.copy(contactNumberError = "Invalid phone number format") }
            isValid = false
        }

        if (state.bio.isBlank()) {
            _uiState.update { it.copy(bioError = "Bio is required") }
            isValid = false
        } else if (state.bio.length < 50) {
            _uiState.update { it.copy(bioError = "Bio must be at least 50 characters") }
            isValid = false
        }

        return isValid
    }
}
