package com.akash.beautifulbhaluka.presentation.screens.fireservice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.akash.beautifulbhaluka.domain.model.FireServiceInfo
import com.akash.beautifulbhaluka.domain.model.FireStation

class FireServiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FireServiceUiState())
    val uiState: StateFlow<FireServiceUiState> = _uiState.asStateFlow()

    init {
        loadFireServiceData()
    }

    fun onAction(action: FireServiceAction) {
        when (action) {
            is FireServiceAction.LoadData -> loadFireServiceData()
            is FireServiceAction.CallNumber -> {
                // Handle phone call logic here
                // For now, this is a placeholder for future implementation
            }
        }
    }

    private fun loadFireServiceData() {
        _uiState.update { it.copy(isLoading = true) }

        // Static data as per requirement - will integrate with backend later
        val mockData = FireServiceInfo(
            title = "ফায়ার সার্ভিস",
            stations = listOf(
                FireStation(
                    avatarUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/364617.png",
                    stationName = "ফায়ার সার্ভিস ভালুকা",
                    phoneNumbers = listOf("01901-024229", "01730-002368")
                )
            )
        )

        _uiState.update {
            it.copy(
                isLoading = false,
                fireServiceInfo = mockData,
                error = null
            )
        }
    }
}
