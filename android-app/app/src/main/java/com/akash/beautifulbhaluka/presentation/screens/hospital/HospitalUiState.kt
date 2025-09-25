package com.akash.beautifulbhaluka.presentation.screens.hospital

import com.akash.beautifulbhaluka.domain.model.HospitalInfo

data class HospitalUiState(
    val isLoading: Boolean = false,
    val hospitals: List<HospitalInfo> = emptyList(),
    val error: String? = null
)
