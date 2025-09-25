package com.akash.beautifulbhaluka.presentation.screens.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorUiState())
    val uiState: StateFlow<DoctorUiState> = _uiState.asStateFlow()

    private var onPhoneCall: ((String) -> Unit)? = null

    fun setPhoneCallback(callback: (String) -> Unit) {
        onPhoneCall = callback
    }

    fun onAction(action: DoctorAction) {
        when (action) {
            is DoctorAction.LoadData -> loadData()
            is DoctorAction.CallPhone -> callPhone(action.phoneNumber)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Simulate loading delay (in real app, this would fetch from repository)
            try {
                // Data is already loaded from default values in UiState
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun callPhone(phoneNumber: String) {
        onPhoneCall?.invoke(phoneNumber)
    }
}
