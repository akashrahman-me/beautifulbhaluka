package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.details

import com.akash.beautifulbhaluka.domain.model.Matchmaker

data class MatchmakerDetailsUiState(
    val isLoading: Boolean = false,
    val matchmaker: Matchmaker? = null,
    val error: String? = null
)

