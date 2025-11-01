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

    private var allDonors: List<DonorInfo> = emptyList()
    private var onPhoneCall: ((String) -> Unit)? = null

    fun setPhoneCallback(callback: (String) -> Unit) {
        onPhoneCall = callback
    }

    fun onAction(action: BloodBankAction) {
        when (action) {
            is BloodBankAction.LoadData -> loadData()
            is BloodBankAction.CallPhone -> callPhone(action.phoneNumber)
            is BloodBankAction.FilterByBloodGroup -> filterByBloodGroup(action.bloodGroup)
            is BloodBankAction.FilterByAvailability -> filterByAvailability(action.availability)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Fetch from repository
                val result = repository.getDonors()

                result.onSuccess { donors ->
                    allDonors = donors.map { donor -> donor.toDonorInfo() }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            donors = getFilteredDonors(),
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

    private fun filterByBloodGroup(bloodGroup: String?) {
        _uiState.update {
            it.copy(
                selectedBloodGroup = bloodGroup,
                donors = getFilteredDonors(bloodGroup, it.selectedAvailability)
            )
        }
    }

    private fun filterByAvailability(availability: String?) {
        _uiState.update {
            it.copy(
                selectedAvailability = availability,
                donors = getFilteredDonors(it.selectedBloodGroup, availability)
            )
        }
    }

    private fun getFilteredDonors(
        bloodGroup: String? = _uiState.value.selectedBloodGroup,
        availability: String? = _uiState.value.selectedAvailability
    ): List<DonorInfo> {
        var filtered = allDonors

        // Filter by blood group
        if (bloodGroup != null) {
            filtered = filtered.filter { it.bloodGroup == bloodGroup }
        }

        // Filter by availability
        if (availability != null) {
            filtered = when (availability) {
                "সময় হয়েছে" -> filtered.filter { it.status == "সময় হয়েছে" }
                "সময় হয়নি" -> filtered.filter { it.status == "সময় হয়নি" }
                else -> filtered
            }
        }

        return filtered
    }

    private fun callPhone(phoneNumber: String) {
        onPhoneCall?.invoke(phoneNumber)
    }
}

