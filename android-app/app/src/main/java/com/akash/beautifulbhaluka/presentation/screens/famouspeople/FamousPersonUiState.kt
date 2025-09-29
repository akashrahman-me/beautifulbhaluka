package com.akash.beautifulbhaluka.presentation.screens.famouspeople

import com.akash.beautifulbhaluka.domain.model.FamousPerson

data class FamousPersonUiState(
    val isLoading: Boolean = false,
    val famousPeople: List<FamousPerson> = emptyList(),
    val error: String? = null
)
