package com.akash.beautifulbhaluka.presentation.screens.shopping.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishMarketViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishMarketUiState())
    val uiState: StateFlow<PublishMarketUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishMarketAction) {
        when (action) {
            is PublishMarketAction.UpdateMarketName -> {
                _uiState.update {
                    it.copy(
                        marketName = action.name,
                        marketNameError = validateMarketName(action.name)
                    )
                }
            }

            is PublishMarketAction.UpdateAddress -> {
                _uiState.update {
                    it.copy(
                        address = action.address,
                        addressError = validateAddress(action.address)
                    )
                }
            }

            is PublishMarketAction.UpdateMarketDays -> {
                _uiState.update {
                    it.copy(
                        marketDays = action.days,
                        marketDaysError = validateMarketDays(action.days)
                    )
                }
            }

            is PublishMarketAction.UpdateImage -> {
                _uiState.update { it.copy(image = action.image) }
            }

            is PublishMarketAction.Submit -> submitMarket()
            is PublishMarketAction.ClearSuccess -> {
                _uiState.update { it.copy(isSuccess = false) }
            }
        }
    }

    private fun validateMarketName(name: String): String? {
        return when {
            name.isBlank() -> "বাজারের নাম আবশ্যক"
            name.length < 3 -> "বাজারের নাম অন্তত ৩ অক্ষরের হতে হবে"
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

    private fun validateMarketDays(days: String): String? {
        return when {
            days.isBlank() -> "বাজারের দিন আবশ্যক"
            days.length < 3 -> "বাজারের দিন অন্তত ৩ অক্ষরের হতে হবে"
            else -> null
        }
    }

    private fun submitMarket() {
        val currentState = _uiState.value

        val nameError = validateMarketName(currentState.marketName)
        val addressError = validateAddress(currentState.address)
        val daysError = validateMarketDays(currentState.marketDays)

        if (nameError != null || addressError != null || daysError != null) {
            _uiState.update {
                it.copy(
                    marketNameError = nameError,
                    addressError = addressError,
                    marketDaysError = daysError
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
                        error = "বাজার যোগ করতে ব্যর্থ হয়েছে। অনুগ্রহ করে আবার চেষ্টা করুন।"
                    )
                }
            }
        }
    }
}

