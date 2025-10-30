package com.akash.beautifulbhaluka.presentation.screens.bloodbank.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManageDonorsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ManageDonorsUiState())
    val uiState: StateFlow<ManageDonorsUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onAction(action: ManageDonorsAction) {
        when (action) {
            is ManageDonorsAction.LoadData -> loadData()
            is ManageDonorsAction.DeleteDonor -> deleteDonor(action.donorId)
            is ManageDonorsAction.ClearError -> clearError()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // TODO: Fetch from repository
                // Simulating API call
                kotlinx.coroutines.delay(500)

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
                        error = e.message ?: "Failed to load donors"
                    )
                }
            }
        }
    }

    private fun deleteDonor(donorId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // TODO: Delete from repository
                // Simulating API call
                kotlinx.coroutines.delay(800)

                // Remove from list
                val updatedDonors = _uiState.value.myPublishedDonors.filter { it.id != donorId }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        myPublishedDonors = updatedDonors,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to delete donor"
                    )
                }
            }
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

