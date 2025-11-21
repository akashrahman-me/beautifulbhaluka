package com.akash.beautifulbhaluka.presentation.screens.famouspeople.details

data class FamousPersonDetailsUiState(
    val isLoading: Boolean = false,
    val personTitle: String = "",
    val personContent: String = "",
    val error: String? = null
)

