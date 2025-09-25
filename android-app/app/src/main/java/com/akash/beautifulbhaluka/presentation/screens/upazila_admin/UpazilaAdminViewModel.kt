package com.akash.beautifulbhaluka.presentation.screens.upazila_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpazilaAdminViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UpazilaAdminUiState())
    val uiState: StateFlow<UpazilaAdminUiState> = _uiState.asStateFlow()

    private var onPhoneCall: ((String) -> Unit)? = null
    private var onEmailSend: ((String) -> Unit)? = null

    fun setPhoneCallback(callback: (String) -> Unit) {
        onPhoneCall = callback
    }

    fun setEmailCallback(callback: (String) -> Unit) {
        onEmailSend = callback
    }

    fun onAction(action: UpazilaAdminAction) {
        when (action) {
            is UpazilaAdminAction.LoadData -> loadData()
            is UpazilaAdminAction.CallPhone -> callPhone(action.phoneNumber)
            is UpazilaAdminAction.SendEmail -> sendEmail(action.email)
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

    private fun sendEmail(email: String) {
        onEmailSend?.invoke(email)
    }
}
