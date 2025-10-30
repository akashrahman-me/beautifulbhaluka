package com.akash.beautifulbhaluka.presentation.screens.bloodbank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BloodBankViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BloodBankUiState())
    val uiState: StateFlow<BloodBankUiState> = _uiState.asStateFlow()

    private var onPhoneCall: ((String) -> Unit)? = null

    fun setPhoneCallback(callback: (String) -> Unit) {
        onPhoneCall = callback
    }

    fun onAction(action: BloodBankAction) {
        when (action) {
            is BloodBankAction.LoadData -> loadData()
            is BloodBankAction.CallPhone -> callPhone(action.phoneNumber)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Data is already loaded from default values in UiState
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun callPhone(phoneNumber: String) {
        onPhoneCall?.invoke(phoneNumber)
    }
}

