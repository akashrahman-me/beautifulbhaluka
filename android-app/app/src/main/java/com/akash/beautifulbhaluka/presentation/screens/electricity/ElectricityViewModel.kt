package com.akash.beautifulbhaluka.presentation.screens.electricity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ElectricityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ElectricityUiState())
    val uiState: StateFlow<ElectricityUiState> = _uiState.asStateFlow()

    init {
        onAction(ElectricityAction.LoadData)
    }

    fun onAction(action: ElectricityAction) {
        when (action) {
            is ElectricityAction.LoadData -> loadData()
            is ElectricityAction.CallOffice -> callOffice(action.phone)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the provided electricity offices
            val electricityOffices = listOf(
                ElectricityOffice(
                    name = "পল্লী বিদ্যুৎ",
                    office = "ভালুকা পল্লী বিদ্যুৎ অফিস",
                    phone = "01769-404010",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483114815.png"
                ),
                ElectricityOffice(
                    name = "পিডিপি",
                    office = "ভালুকা পিডিপি অফিস",
                    phone = "01773-828746",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483114815.png"
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    electricityOffices = electricityOffices,
                    error = null
                )
            }
        }
    }

    private fun callOffice(phone: String) {
        // TODO: Implement phone call functionality
        // For now, this is just a placeholder
    }
}
