package com.akash.beautifulbhaluka.presentation.screens.bloodbank.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublishDonorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishDonorUiState())
    val uiState: StateFlow<PublishDonorUiState> = _uiState.asStateFlow()

    private var onPublishSuccess: (() -> Unit)? = null

    fun setPublishSuccessCallback(callback: () -> Unit) {
        onPublishSuccess = callback
    }

    fun onAction(action: PublishDonorAction) {
        when (action) {
            is PublishDonorAction.UpdateFullName -> updateFullName(action.name)
            is PublishDonorAction.UpdateMobileNumber -> updateMobileNumber(action.number)
            is PublishDonorAction.UpdateBloodGroup -> updateBloodGroup(action.group)
            is PublishDonorAction.UpdateAddress -> updateAddress(action.address)
            is PublishDonorAction.UpdateFacebookLink -> updateFacebookLink(action.link)
            is PublishDonorAction.UpdateWhatsAppNumber -> updateWhatsAppNumber(action.number)
            is PublishDonorAction.SetBloodGroupDropdownExpanded -> setDropdownExpanded(action.expanded)
            is PublishDonorAction.Submit -> submitDonor()
            is PublishDonorAction.ClearError -> clearError()
        }
    }

    private fun updateFullName(name: String) {
        _uiState.update { it.copy(fullName = name) }
    }

    private fun updateMobileNumber(number: String) {
        _uiState.update { it.copy(mobileNumber = number) }
    }

    private fun updateBloodGroup(group: String) {
        _uiState.update { it.copy(bloodGroup = group) }
    }

    private fun updateAddress(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    private fun updateFacebookLink(link: String) {
        _uiState.update { it.copy(facebookLink = link) }
    }

    private fun updateWhatsAppNumber(number: String) {
        _uiState.update { it.copy(whatsappNumber = number) }
    }

    private fun setDropdownExpanded(expanded: Boolean) {
        _uiState.update { it.copy(isBloodGroupDropdownExpanded = expanded) }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun submitDonor() {
        val currentState = _uiState.value

        // Validate inputs
        val validationErrors = validateInputs(currentState)

        if (validationErrors.hasErrors()) {
            _uiState.update { it.copy(validationErrors = validationErrors) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, validationErrors = ValidationErrors()) }

            try {
                // TODO: Implement actual submission to repository
                // Simulating API call
                kotlinx.coroutines.delay(1500)

                // Success
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        error = null
                    )
                }

                // Trigger success callback
                onPublishSuccess?.invoke()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to publish donor information"
                    )
                }
            }
        }
    }

    private fun validateInputs(state: PublishDonorUiState): ValidationErrors {
        return ValidationErrors(
            fullName = when {
                state.fullName.isBlank() -> "পূর্ণ নাম প্রয়োজন"
                state.fullName.length < 3 -> "নাম কমপক্ষে ৩ অক্ষরের হতে হবে"
                else -> null
            },
            mobileNumber = when {
                state.mobileNumber.isBlank() -> "মোবাইল নম্বর প্রয়োজন"
                !state.mobileNumber.matches(Regex("^01[0-9]{9}$")) -> "সঠিক মোবাইল নম্বর দিন (01XXXXXXXXX)"
                else -> null
            },
            bloodGroup = if (state.bloodGroup.isBlank()) {
                "রক্তের গ্রুপ নির্বাচন করুন"
            } else null,
            address = when {
                state.address.isBlank() -> "ঠিকানা প্রয়োজন"
                state.address.length < 5 -> "ঠিকানা কমপক্ষে ৫ অক্ষরের হতে হবে"
                else -> null
            }
        )
    }
}

private fun ValidationErrors.hasErrors(): Boolean {
    return fullName != null || mobileNumber != null || bloodGroup != null || address != null
}

