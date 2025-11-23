package com.akash.beautifulbhaluka.presentation.screens.courier.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublishCourierViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishCourierUiState())
    val uiState: StateFlow<PublishCourierUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishCourierAction) {
        when (action) {
            is PublishCourierAction.UpdateName -> updateName(action.name)
            is PublishCourierAction.UpdateAddress -> updateAddress(action.address)
            is PublishCourierAction.UpdateLocation -> updateLocation(action.location)
            is PublishCourierAction.UpdatePhone -> updatePhone(action.phone)
            is PublishCourierAction.UpdateImage -> updateImage(action.image)
            is PublishCourierAction.Submit -> submit()
        }
    }

    private fun updateName(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameError = if (name.isBlank()) "নাম লিখুন" else null
            )
        }
    }

    private fun updateAddress(address: String) {
        _uiState.update {
            it.copy(
                address = address,
                addressError = if (address.isBlank()) "ঠিকানা লিখুন" else null
            )
        }
    }

    private fun updateLocation(location: String) {
        _uiState.update {
            it.copy(
                location = location,
                locationError = if (location.isBlank()) "লোকেশন লিখুন" else null
            )
        }
    }

    private fun updatePhone(phone: String) {
        _uiState.update {
            it.copy(
                phone = phone,
                phoneError = when {
                    phone.isBlank() -> "মোবাইল নাম্বার লিখুন"
                    !phone.matches(Regex("^[০-৯0-9+\\-\\s()]+$")) -> "সঠিক মোবাইল নাম্বার লিখুন"
                    else -> null
                }
            )
        }
    }

    private fun updateImage(image: String) {
        _uiState.update {
            it.copy(image = image)
        }
    }

    private fun submit() {
        // Validate all fields
        val currentState = _uiState.value
        val hasErrors = currentState.name.isBlank() ||
                currentState.address.isBlank() ||
                currentState.location.isBlank() ||
                currentState.phone.isBlank()

        if (hasErrors) {
            _uiState.update {
                it.copy(
                    nameError = if (it.name.isBlank()) "নাম লিখুন" else null,
                    addressError = if (it.address.isBlank()) "ঠিকানা লিখুন" else null,
                    locationError = if (it.location.isBlank()) "লোকেশন লিখুন" else null,
                    phoneError = if (it.phone.isBlank()) "মোবাইল নাম্বার লিখুন" else null
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            try {
                // Simulate API call
                delay(2000)

                // TODO: Actual API call to submit courier service data
                // val result = courierRepository.publishCourier(currentState)

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        isSuccess = true
                    )
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

