package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MatchmakingScreen(
    viewModel: MatchmakingViewModel = viewModel(),
    onNavigateToDetails: ((String) -> Unit)? = null,
    onNavigateToPublish: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    MatchmakingContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToPublish = onNavigateToPublish
    )
}

