package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun JobsScreen(
    viewModel: JobsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    JobsContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}
