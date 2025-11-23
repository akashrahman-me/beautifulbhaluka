package com.akash.beautifulbhaluka.presentation.screens.places.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.MarkdownText
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar

@Composable
fun PlaceDetailsScreen(
    placeTitle: String = "",
    viewModel: PlaceDetailsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(placeTitle) {
        if (placeTitle.isNotEmpty()) {
            viewModel.loadPlaceDetails(placeTitle)
        }
    }

    PlaceDetailsContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                PlaceDetailsAction.NavigateBack -> onNavigateBack()
                PlaceDetailsAction.NavigateHome -> onNavigateHome()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailsContent(
    uiState: PlaceDetailsUiState,
    onAction: (PlaceDetailsAction) -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "দর্শনীয় স্থান",
                onNavigateBack = { onAction(PlaceDetailsAction.NavigateBack) }
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
                                imageVector = Icons.Default.Info,
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

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            MarkdownText(
                                text = uiState.placeContent,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }


                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

