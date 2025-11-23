package com.akash.beautifulbhaluka.presentation.screens.hotels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.hotels.components.HotelCard

@Composable
fun HotelsScreen(
    viewModel: HotelsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateHome: (() -> Unit)? = null,
    onNavigateToPublish: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HotelsContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack,
        onNavigateHome = onNavigateHome,
        onNavigateToPublish = onNavigateToPublish
    )
}

@Composable
fun HotelsContent(
    uiState: HotelsUiState,
    onAction: (HotelsAction) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateHome: (() -> Unit)?,
    onNavigateToPublish: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "আবাসিক হোটেল",
                onNavigateBack = onNavigateBack,
                onNavigateHome = onNavigateHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToPublish,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "হোটেল যোগ করুন"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            // Loading/Error States
            when {
                uiState.isLoading -> {
                    item {
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

                uiState.error != null -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
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
                    // Hotel Cards Section
                    items(uiState.hotels) { hotel ->
                        HotelCard(
                            hotel = hotel,
                            onClick = { onAction(HotelsAction.OnHotelClick(hotel)) },
                            onPhoneClick = { phone -> onAction(HotelsAction.OnPhoneClick(phone)) },
                            onRatingChange = { rating ->
                                onAction(
                                    HotelsAction.OnRatingChange(
                                        hotel,
                                        rating
                                    )
                                )
                            }
                        )
                    }
                }
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
