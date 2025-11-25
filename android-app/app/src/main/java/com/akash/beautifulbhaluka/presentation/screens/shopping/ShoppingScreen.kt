package com.akash.beautifulbhaluka.presentation.screens.shopping

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.shopping.components.MarketCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = viewModel(),
    navigateBack: () -> Unit = {},
    navigateToPublish: () -> Unit = {},
    navigateHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ShoppingContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
        navigateToPublish = navigateToPublish,
        navigateHome = navigateHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingContent(
    uiState: ShoppingUiState,
    onAction: (ShoppingAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToPublish: () -> Unit,
    navigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "হাট বাজার",
                onNavigateBack = navigateBack,
                onNavigateHome = navigateHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPublish,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "নতুন বাজার যোগ করুন"
                )
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = uiState.markets,
                        key = { it.title }
                    ) { market ->
                        MarketCard(market = market)
                    }
                }
            }
        }
    }
}
