package com.akash.beautifulbhaluka.presentation.screens.houserent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HouseRentScreen(
    viewModel: HouseRentViewModel = viewModel(),
    onNavigateToDetails: ((String) -> Unit)? = null,
    onNavigateToPublish: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    HouseRentContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToPublish = onNavigateToPublish
    )
}

