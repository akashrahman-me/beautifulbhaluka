package com.akash.beautifulbhaluka.presentation.screens.places

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.places.components.PlaceCard

@Composable
fun PlacesScreen(
    viewModel: PlacesViewModel = viewModel(),
    onNavigateToDetails: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    PlacesContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is PlacesAction.LoadData -> viewModel.onAction(action)
                is PlacesAction.OnPlaceClick -> {
                    viewModel.onAction(action)
                    onNavigateToDetails(action.place.title)
                }

                PlacesAction.NavigateBack -> onNavigateBack()
                PlacesAction.NavigateHome -> onNavigateHome()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesContent(
    uiState: PlacesUiState,
    onAction: (PlacesAction) -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "দর্শনীয় স্থান",
                onNavigateBack = { onAction(PlacesAction.NavigateBack) },
                onNavigateHome = { onAction(PlacesAction.NavigateHome) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    }
                }

                uiState.error != null -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = uiState.error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                else -> {
                    // Places Cards Section
                    items(uiState.places) { place ->
                        PlaceCard(
                            place = place,
                            onClick = { onAction(PlacesAction.OnPlaceClick(place)) }
                        )
                    }
                }
            }
        }
    }
}
