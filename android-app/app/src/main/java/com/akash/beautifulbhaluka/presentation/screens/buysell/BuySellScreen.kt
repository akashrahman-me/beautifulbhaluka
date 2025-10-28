package com.akash.beautifulbhaluka.presentation.screens.buysell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BuySellScreen(
    viewModel: BuySellViewModel = viewModel(),
    onNavigateToDetails: ((String) -> Unit)? = null,
    onNavigateToPublish: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    BuySellContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToPublish = onNavigateToPublish
    )
}

