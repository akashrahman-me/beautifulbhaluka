package com.akash.beautifulbhaluka.presentation.screens.courier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.akash.beautifulbhaluka.presentation.screens.courier.components.CourierServiceCard

@Composable
fun CourierScreen(
    viewModel: CourierViewModel = viewModel(),
    navigateBack: () -> Unit,
    navigateToPublish: () -> Unit,
    navigateHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CourierContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
        navigateToPublish = navigateToPublish,
        navigateHome = navigateHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourierContent(
    uiState: CourierUiState,
    onAction: (CourierAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToPublish: () -> Unit,
    navigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "কুরিয়ার সার্ভিস",
                onNavigateBack = navigateBack,
                onNavigateHome = navigateHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPublish,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "নতুন কুরিয়ার সার্ভিস যোগ করুন"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(24.dp)
                ) {
                    // Loading/Error States
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
                                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(
                                            alpha = 0.1f
                                        )
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
                            // Courier Service Cards Section
                            items(uiState.courierServices) { courierService ->
                                CourierServiceCard(
                                    courierService = courierService,
                                    onClick = { onAction(CourierAction.OnCourierClick(courierService)) },
                                    onPhoneClick = { phone ->
                                        onAction(
                                            CourierAction.OnPhoneClick(
                                                phone
                                            )
                                        )
                                    },
                                    onRatingChange = { rating ->
                                        onAction(
                                            CourierAction.OnRatingChange(
                                                courierService,
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
    }
}
