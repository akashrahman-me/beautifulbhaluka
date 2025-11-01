package com.akash.beautifulbhaluka.presentation.screens.bloodbank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.repository.BloodBankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloodBankViewModel @Inject constructor(
    private val repository: BloodBankRepository
) : ViewModel() {

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
                // Fetch from repository
                val result = repository.getDonors()

                result.onSuccess { donors ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            donors = donors.map { donor -> donor.toDonorInfo() },
                            error = null
                        )
                    }
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to load donors"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    private fun callPhone(phoneNumber: String) {
        onPhoneCall?.invoke(phoneNumber)
    }
}

