package com.akash.beautifulbhaluka.presentation.screens.lawyer

import com.akash.beautifulbhaluka.domain.model.LawyerInfo

data class LawyerUiState(
    val isLoading: Boolean = false,
    val lawyers: List<LawyerInfo> = emptyList(),
    val error: String? = null
)
