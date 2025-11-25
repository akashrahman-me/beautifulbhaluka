package com.akash.beautifulbhaluka.presentation.screens.gentsparlour

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.gentsparlour.components.GentsParlourCard

@Composable
fun GentsParlourScreen(
    viewModel: GentsParlourViewModel = viewModel(),
    navigateToPublish: () -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    GentsParlourContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is GentsParlourAction.CallPhone -> {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${action.phoneNumber}")
                    }
                    context.startActivity(intent)
                }

                else -> viewModel.onAction(action)
            }
        },
        navigateToPublish = navigateToPublish,
        navigateBack = navigateBack,
        navigateToHome = navigateToHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GentsParlourContent(
    uiState: GentsParlourUiState,
    onAction: (GentsParlourAction) -> Unit,
    navigateToPublish: () -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "জেন্টস পার্লার",
                onNavigateBack = navigateBack,
                onNavigateHome = navigateToHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPublish,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "নতুন পার্লার যোগ করুন"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    )
                )
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(24.dp)
                    ) {
                        items(
                            items = uiState.parlours,
                            key = { it.id }
                        ) { parlour ->
                            GentsParlourCard(
                                parlour = parlour,
                                onCallClick = { phoneNumber ->
                                    onAction(GentsParlourAction.CallPhone(phoneNumber))
                                },
                                onRatingChange = { parlourId, rating ->
                                    onAction(GentsParlourAction.RateParlour(parlourId, rating))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
