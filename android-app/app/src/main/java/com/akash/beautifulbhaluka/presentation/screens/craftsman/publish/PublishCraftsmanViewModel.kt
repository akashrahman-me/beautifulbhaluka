package com.akash.beautifulbhaluka.presentation.screens.craftsman.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.presentation.screens.craftsman.CraftsmanType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishCraftsmanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishCraftsmanUiState())
    val uiState: StateFlow<PublishCraftsmanUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishCraftsmanAction) {
        when (action) {
            is PublishCraftsmanAction.UpdateName -> {
                _uiState.update {
                    it.copy(
                        name = action.name,
                        nameError = validateName(action.name)
                    )
                }
            }

            is PublishCraftsmanAction.UpdateAddress -> {
                _uiState.update {
                    it.copy(
                        address = action.address,
                        addressError = validateAddress(action.address)
                    )
                }
            }

            is PublishCraftsmanAction.UpdatePhone -> {
                _uiState.update {
                    it.copy(
                        phone = action.phone,
                        phoneError = validatePhone(action.phone)
                    )
                }
            }

            is PublishCraftsmanAction.UpdateImage -> {
                _uiState.update { it.copy(image = action.image) }
            }

            is PublishCraftsmanAction.UpdateCraftsmanType -> {
                _uiState.update { it.copy(craftsmanType = action.type) }
            }

            is PublishCraftsmanAction.Submit -> submitCraftsman()
            is PublishCraftsmanAction.ClearSuccess -> {
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

    private fun submitCraftsman() {
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

