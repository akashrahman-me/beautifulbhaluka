package com.akash.beautifulbhaluka.presentation.screens.carrent.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublishCarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishCarUiState())
    val uiState: StateFlow<PublishCarUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishCarAction) {
        when (action) {
            is PublishCarAction.OnCarNameChange -> {
                _uiState.update { it.copy(carName = action.name) }
            }

            is PublishCarAction.OnDriverNameChange -> {
                _uiState.update { it.copy(driverName = action.name) }
            }

            is PublishCarAction.OnMobileChange -> {
                _uiState.update { it.copy(mobile = action.mobile) }
            }

            is PublishCarAction.OnAddressChange -> {
                _uiState.update { it.copy(address = action.address) }
            }

            is PublishCarAction.OnLocationChange -> {
                _uiState.update { it.copy(location = action.location) }
            }

            is PublishCarAction.OnCarTypeChange -> {
                _uiState.update { it.copy(carType = action.type) }
            }

            is PublishCarAction.OnPriceChange -> {
                _uiState.update { it.copy(pricePerDay = action.price) }
            }

            is PublishCarAction.OnImageSelect -> {
                _uiState.update { it.copy(imageUrl = action.imageUrl) }
            }

            is PublishCarAction.OnPublish -> {
                publishCar()
            }

            is PublishCarAction.ClearError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun publishCar() {
        val state = _uiState.value

        if (state.carName.isBlank()) {
            _uiState.update { it.copy(error = "গাড়ির নাম দিন") }
            return
        }
        if (state.driverName.isBlank()) {
            _uiState.update { it.copy(error = "ড্রাইভারের নাম দিন") }
            return
        }
        if (state.mobile.isBlank()) {
            _uiState.update { it.copy(error = "মোবাইল নম্বর দিন") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isPublished = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "প্রকাশ করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }
}

