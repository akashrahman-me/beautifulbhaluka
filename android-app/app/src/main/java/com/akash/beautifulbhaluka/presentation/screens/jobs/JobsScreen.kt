package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier

@Composable
fun JobsScreen(
    onNavigateToJobDetails: (String) -> Unit = {},
    onNavigateToPublishJob: () -> Unit = {},
    viewModel: JobsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
    }
}
