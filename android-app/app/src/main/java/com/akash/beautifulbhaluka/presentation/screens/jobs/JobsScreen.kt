package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun JobsScreen(
    onNavigateToJobDetails: (String) -> Unit = {},
    onNavigateToPublishJob: () -> Unit = {},
    viewModel: JobsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "স্ক্রিন খুব শীঘ্রই আসছে",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center,
        )
    }

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
