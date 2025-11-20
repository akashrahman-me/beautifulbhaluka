package com.akash.beautifulbhaluka.presentation.screens.lawyer.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishLawyerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishLawyerUiState())
    val uiState: StateFlow<PublishLawyerUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishLawyerAction) {
        when (action) {
            is PublishLawyerAction.UpdateName -> {
                _uiState.update {
                    it.copy(
                        name = action.name,
                        nameError = validateName(action.name)
                    )
                }
            }

            is PublishLawyerAction.UpdateDesignation -> {
                _uiState.update {
                    it.copy(
                        designation = action.designation,
                        designationError = validateDesignation(action.designation)
                    )
                }
            }

            is PublishLawyerAction.UpdatePhone -> {
                _uiState.update {
                    it.copy(
                        phone = action.phone,
                        phoneError = validatePhone(action.phone)
                    )
                }
            }

            is PublishLawyerAction.UpdateImage -> {
                _uiState.update { it.copy(image = action.image) }
            }

            is PublishLawyerAction.Submit -> submitLawyer()
            is PublishLawyerAction.ClearSuccess -> {
                _uiState.update { it.copy(isSuccess = false) }
            }
        }
    }

    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "নাম আবশ্যক"
            name.length < 3 -> "নাম অন্তত ৩ অক্ষরের হতে হবে"
            else -> null
        }
    }

    private fun validateDesignation(designation: String): String? {
        return when {
            designation.isBlank() -> "পদবী আবশ্যক"
            designation.length < 3 -> "পদবী অন্তত ৩ অক্ষরের হতে হবে"
            else -> null
        }
    }

    private fun validatePhone(phone: String): String? {
        return when {
            phone.isBlank() -> "ফোন নম্বর আবশ্যক"
            !phone.matches(Regex("^[+]?[0-9\\s-]+$")) -> "সঠিক ফোন নম্বর লিখুন"
            phone.replace(Regex("[^0-9]"), "").length < 10 -> "ফোন নম্বর কমপক্ষে ১০ সংখ্যার হতে হবে"
            else -> null
        }
    }

    private fun submitLawyer() {
        val currentState = _uiState.value

        // Validate all fields
        val nameError = validateName(currentState.name)
        val designationError = validateDesignation(currentState.designation)
        val phoneError = validatePhone(currentState.phone)

        if (nameError != null || designationError != null || phoneError != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    designationError = designationError,
                    phoneError = phoneError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            try {
                // Simulate API call
                delay(1500)

                // TODO: Implement actual API call to save lawyer data
                // For now, just simulate success

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        isSuccess = true,
                        error = null
                    )
                }

                // Clear form after success
                delay(500)
                _uiState.update {
                    PublishLawyerUiState(isSuccess = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        error = "তথ্য জমা দিতে সমস্যা হয়েছে। আবার চেষ্টা করুন।"
                    )
                }
            }
        }
    }
}

