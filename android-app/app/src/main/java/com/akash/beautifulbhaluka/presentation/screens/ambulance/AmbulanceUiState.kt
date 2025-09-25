package com.akash.beautifulbhaluka.presentation.screens.ambulance

import com.akash.beautifulbhaluka.domain.model.AmbulanceInfo

data class AmbulanceUiState(
    val isLoading: Boolean = false,
    val ambulances: List<AmbulanceInfo> = emptyList(),
    val error: String? = null
)
