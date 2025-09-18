package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun JobsScreen(
    onNavigateToJobDetails: (String) -> Unit = {},
    onNavigateToPublishJob: () -> Unit = {},
    viewModel: JobsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    JobsContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is JobsAction.ViewJobDetails -> {
                    onNavigateToJobDetails(action.jobId)
                }

                is JobsAction.NavigateToPublishJob -> {
                    onNavigateToPublishJob()
                }

                else -> viewModel.onAction(action)
            }
        }
    )
}
