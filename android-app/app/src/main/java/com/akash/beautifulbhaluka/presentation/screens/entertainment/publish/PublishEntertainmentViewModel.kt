package com.akash.beautifulbhaluka.presentation.screens.entertainment.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishEntertainmentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishEntertainmentUiState())
    val uiState: StateFlow<PublishEntertainmentUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishEntertainmentAction) {
        when (action) {
            is PublishEntertainmentAction.UpdateName -> {
                _uiState.update {
                    it.copy(
                        name = action.name,
                        nameError = validateName(action.name)
                    )
                }
            }

            is PublishEntertainmentAction.UpdateAddress -> {
                _uiState.update {
                    it.copy(
                        address = action.address,
                        addressError = validateAddress(action.address)
                    )
                }
            }

            is PublishEntertainmentAction.UpdatePhone -> {
                _uiState.update {
                    it.copy(
                        phone = action.phone,
                        phoneError = validatePhone(action.phone)
                    )
                }
            }

            is PublishEntertainmentAction.UpdateImage -> {
                _uiState.update { it.copy(image = action.image) }
            }

            is PublishEntertainmentAction.UpdateEntertainmentType -> {
                _uiState.update { it.copy(entertainmentType = action.type) }
            }

            is PublishEntertainmentAction.Submit -> submitEntertainment()
            is PublishEntertainmentAction.ClearSuccess -> {
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

    private fun validatePhone(phone: String): String? {
        return when {
            phone.isBlank() -> "মোবাইল নাম্বার আবশ্যক"
            phone.length < 11 -> "সঠিক মোবাইল নাম্বার দিন"
            else -> null
        }
    }

    private fun submitEntertainment() {
        val currentState = _uiState.value

        val nameError = validateName(currentState.name)
        val addressError = validateAddress(currentState.address)
        val phoneError = validatePhone(currentState.phone)

        if (nameError != null || addressError != null || phoneError != null) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    addressError = addressError,
                    phoneError = phoneError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            try {
                delay(2000)

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
                        error = "তথ্য যোগ করতে ব্যর্থ হয়েছে। অনুগ্রহ করে আবার চেষ্টা করুন।"
                    )
                }
            }
        }
    }
}

