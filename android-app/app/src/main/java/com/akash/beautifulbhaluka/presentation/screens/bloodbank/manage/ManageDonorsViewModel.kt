package com.akash.beautifulbhaluka.presentation.screens.bloodbank.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.repository.BloodBankRepository
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.toDonorInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageDonorsViewModel @Inject constructor(
    private val repository: BloodBankRepository
) : ViewModel() {

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
                val result = repository.getMyPublishedDonors()

                result.onSuccess { donors ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            myPublishedDonors = donors.map { donor -> donor.toDonorInfo() },
                            error = null
                        )
                    }
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to load your donors"
                        )
                    }
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
                val result = repository.deleteDonor(donorId)

                result.onSuccess {
                    // Reload data after successful deletion
                    loadData()
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to delete donor"
                        )
                    }
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

