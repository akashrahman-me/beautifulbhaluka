package com.akash.beautifulbhaluka.presentation.screens.shops

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShopsScreen(
    viewModel: ShopsViewModel = viewModel(),
    onNavigateToDetails: ((String) -> Unit)? = null,
    onNavigateToPublish: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    ShopsContent(
        uiState = uiState,
        filteredProducts = uiState.filteredProducts,
        onAction = viewModel::onAction,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToPublish = onNavigateToPublish
    )
}
