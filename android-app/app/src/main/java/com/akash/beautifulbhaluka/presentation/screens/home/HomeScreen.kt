package com.akash.beautifulbhaluka.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigateToScreen: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Set the navigation callback in the viewModel
    LaunchedEffect(onNavigateToScreen) {
        viewModel.setNavigationCallback(onNavigateToScreen)
    }

    HomeContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}
