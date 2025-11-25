package com.akash.beautifulbhaluka.presentation.screens.butchercook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.screens.butchercook.components.ButcherCookCard

@Composable
fun ButcherCookScreen(
    viewModel: ButcherCookViewModel = viewModel(),
    navigateBack: () -> Unit = {},
    navigateToPublish: () -> Unit = {},
    navigateHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ButcherCookContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
        navigateToPublish = navigateToPublish,
        navigateHome = navigateHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButcherCookContent(
    uiState: ButcherCookUiState,
    onAction: (ButcherCookAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToPublish: () -> Unit,
    navigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar(
                title = "কসাই ও বাবুর্চি",
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
                    contentDescription = "নতুন যোগ করুন"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {

            // Search and Filter Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Search Bar
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = { onAction(ButcherCookAction.SearchQueryChanged(it)) },
                        placeholder = { Text("নাম, ঠিকানা বা ধরন দিয়ে খোঁজ করুন...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "খোঁজ করুন"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Type Filter Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "ফিল্টার",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )

                        ButcherCookType.values().forEach { type ->
                            FilterChip(
                                onClick = { onAction(ButcherCookAction.TypeFilterChanged(type)) },
                                label = { Text(type.displayName) },
                                selected = uiState.selectedType == type,
                                leadingIcon = if (uiState.selectedType == type) {
                                    {
                                        Text(
                                            text = if (type == ButcherCookType.BUTCHER) "🐄"
                                            else if (type == ButcherCookType.COOK) "🧑‍🍳"
                                            else "🔍",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                } else null
                            )
                        }
                    }
                }
            }

            // Results Count - Only show when search or filter is active
            if (uiState.searchQuery.isNotEmpty() || uiState.selectedType != ButcherCookType.ALL) {
                item {
                    Text(
                        text = "${uiState.filteredButcherCooks.size}টি ফলাফল পাওয়া গেছে",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Content Section
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
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Text(
                                        text = "তথ্য লোড করা হচ্ছে...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
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
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "ত্রুটি: ${uiState.error}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                uiState.filteredButcherCooks.isEmpty() && !uiState.isLoading -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "🔍",
                                        style = MaterialTheme.typography.displayMedium
                                    )
                                    Text(
                                        text = "কোনো ফলাফল পাওয়া যায়নি",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "অন্য কিওয়ার্ড দিয়ে খোঁজ করে দেখুন",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    // Cards List
                    items(
                        items = uiState.filteredButcherCooks,
                        key = { "${it.name}_${it.number}" }
                    ) { butcherCook ->
                        ButcherCookCard(
                            butcherCook = butcherCook,
                            onCallClick = { number ->
                                onAction(ButcherCookAction.CallNumber(number))
                            }
                        )
                    }
                }
            }
        }
    }
}
