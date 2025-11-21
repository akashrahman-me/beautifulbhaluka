package com.akash.beautifulbhaluka.presentation.screens.famouspeople.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.akash.beautifulbhaluka.presentation.components.common.MarkdownText
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar

@Composable
fun FamousPersonDetailsScreen(
    personTitle: String = "",
    viewModel: FamousPersonDetailsViewModel = viewModel(),
    navigateBack: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(personTitle) {
        if (personTitle.isNotEmpty()) {
            viewModel.loadPersonDetails(personTitle)
        }
    }

    FamousPersonDetailsContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                FamousPersonDetailsAction.NavigateBack -> navigateBack()
                FamousPersonDetailsAction.NavigateHome -> navigateToHome()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamousPersonDetailsContent(
    uiState: FamousPersonDetailsUiState,
    onAction: (FamousPersonDetailsAction) -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "প্রসিদ্ধ ব্যক্তিত্ব",
                onNavigateBack = { onAction(FamousPersonDetailsAction.NavigateBack) },
                onNavigateHome = { onAction(FamousPersonDetailsAction.NavigateHome) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        )
                    )
                )
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 3.dp
                        )
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = uiState.error,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                                            )
                                        )
                                    )
                                    .padding(24.dp)
                            ) {
                                MarkdownText(
                                    text = uiState.personContent,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

