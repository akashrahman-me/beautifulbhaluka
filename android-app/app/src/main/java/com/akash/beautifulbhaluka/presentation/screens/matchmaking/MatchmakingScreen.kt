package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MatchmakingScreen(
    viewModel: MatchmakingViewModel = viewModel(),
    onNavigateToDetails: ((String) -> Unit)? = null,
    onNavigateToPublish: (() -> Unit)? = null,
    onNavigateToManageProfiles: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    // Track scroll state for smooth header animation
    val scrollState = rememberLazyListState()

    MatchmakingContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToPublish = onNavigateToPublish,
        onNavigateToManageProfiles = onNavigateToManageProfiles,
        scrollState = scrollState,
        showHeader = true
    )
}
