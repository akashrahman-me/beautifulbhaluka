package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun JobsScreen(
    onNavigateToJobDetails: (String) -> Unit = {},
    onNavigateToPublishJob: () -> Unit = {},
    viewModel: JobsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    JobsContent(
        uiState = uiState,
        listState = listState,
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
