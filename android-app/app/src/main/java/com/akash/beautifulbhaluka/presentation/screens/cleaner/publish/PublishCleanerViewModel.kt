package com.akash.beautifulbhaluka.presentation.screens.cleaner.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishCleanerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishCleanerUiState())
    val uiState: StateFlow<PublishCleanerUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishCleanerAction) {
        when (action) {
            is PublishCleanerAction.UpdateName -> {
                _uiState.update {
                    it.copy(
                        name = action.name,
                        nameError = validateName(action.name)
                    )
                }
            }

            is PublishCleanerAction.UpdateAddress -> {
                _uiState.update {
                    it.copy(
                        address = action.address,
                        addressError = validateAddress(action.address)
                    )
                }
            }

            is PublishCleanerAction.UpdateLocation -> {
                _uiState.update {
                    it.copy(
                        location = action.location,
                        locationError = validateLocation(action.location)
                    )
                }
            }

            is PublishCleanerAction.UpdatePhone -> {
                _uiState.update {
                    it.copy(
                        phone = action.phone,
                        phoneError = validatePhone(action.phone)
                    )
                }
            }

            is PublishCleanerAction.UpdateImage -> {
                _uiState.update { it.copy(image = action.image) }
            }

            is PublishCleanerAction.Submit -> submitCleaner()
            is PublishCleanerAction.ClearSuccess -> {
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

    private fun validateAddress(address: String): String? {
        return when {
            address.isBlank() -> "ঠিকানা আবশ্যক"
            address.length < 5 -> "ঠিকানা অন্তত ৫ অক্ষরের হতে হবে"
            else -> null
        }
    }

    private fun validateLocation(location: String): String? {
        return when {
            location.isBlank() -> "লোকেশন আবশ্যক"
            location.length < 3 -> "লোকেশন অন্তত ৩ অক্ষরের হতে হবে"
            else -> null
        }
    }

    private fun validatePhone(phone: String): String? {
        return when {
            phone.isBlank() -> "মোবাইল নাম্বার আবশ্যক"
            !phone.matches(Regex("^[+]?[0-9\\s-]+$")) -> "সঠিক মোবাইল নাম্বার লিখুন"
            phone.replace(
                Regex("[^0-9]"),
                ""
            ).length < 10 -> "মোবাইল নাম্বার কমপক্ষে ১০ সংখ্যার হতে হবে"

            else -> null
        }
    }

    private fun submitCleaner() {
        val currentState = _uiState.value

        val nameError = validateName(currentState.name)
        val addressError = validateAddress(currentState.address)
        val locationError = validateLocation(currentState.location)
        val phoneError = validatePhone(currentState.phone)

        if (nameError != null || addressError != null || locationError != null || phoneError != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    addressError = addressError,
                    locationError = locationError,
                    phoneError = phoneError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            try {
                delay(1500)

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        isSuccess = true,
                        error = null
                    )
                }

                delay(500)
                _uiState.update {
                    PublishCleanerUiState(isSuccess = true)
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

