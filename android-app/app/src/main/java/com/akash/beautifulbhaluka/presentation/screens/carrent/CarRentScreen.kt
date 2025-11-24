package com.akash.beautifulbhaluka.presentation.screens.carrent

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.carrent.components.CarCard

@Composable
fun CarRentScreen(
    viewModel: CarRentViewModel = viewModel(),
    navigateBack: () -> Unit,
    navigateToPublish: () -> Unit,
    navigateToCategory: (String) -> Unit,
    navigateHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CarRentContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
        navigateToPublish = navigateToPublish,
        navigateToCategory = navigateToCategory,
        navigateHome = navigateHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarRentContent(
    uiState: CarRentUiState,
    onAction: (CarRentAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToPublish: () -> Unit,
    navigateToCategory: (String) -> Unit,
    navigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "গাড়ি ভাড়া",
                onNavigateBack = navigateBack,
                onNavigateHome = navigateHome
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPublish,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "নতুন গাড়ি যোগ করুন",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                strokeWidth = 4.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "লোড হচ্ছে...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
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
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = uiState.error,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                uiState.cars.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                modifier = Modifier.size(100.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsCar,
                                        contentDescription = null,
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "কোনো গাড়ি নেই",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "নতুন গাড়ি যোগ করতে + বাটনে ক্লিক করুন",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {

                        item {
                            Column(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "ক্যাটাগরি",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(CarCategory.categories) { category ->
                                        FilterChip(
                                            selected = uiState.selectedCategory == category,
                                            onClick = {
                                                onAction(
                                                    CarRentAction.OnCategoryChange(
                                                        category
                                                    )
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = category,
                                                    style = MaterialTheme.typography.labelLarge.copy(
                                                        fontWeight = if (uiState.selectedCategory == category)
                                                            FontWeight.Bold
                                                        else
                                                            FontWeight.Medium
                                                    )
                                                )
                                            },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                            ),
                                            border = FilterChipDefaults.filterChipBorder(
                                                enabled = true,
                                                selected = uiState.selectedCategory == category,
                                                borderColor = if (uiState.selectedCategory == category)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                                selectedBorderColor = MaterialTheme.colorScheme.primary,
                                                borderWidth = 1.dp,
                                                selectedBorderWidth = 2.dp
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                    }
                                }
                            }
                        }

                        val filteredCategories = if (uiState.selectedCategory == CarCategory.ALL) {
                            CarCategory.categories.filter { it != CarCategory.ALL }
                        } else {
                            listOf(uiState.selectedCategory)
                        }

                        filteredCategories.forEach { category ->
                            val categoryCars = uiState.cars.filter { it.category == category }
                            if (categoryCars.isNotEmpty()) {
                                item {
                                    CategorySection(
                                        category = category,
                                        cars = categoryCars.take(4),
                                        totalCount = categoryCars.size,
                                        onShowMore = { navigateToCategory(category) },
                                        onCarClick = { car ->
                                            onAction(
                                                CarRentAction.OnCarClick(
                                                    car
                                                )
                                            )
                                        },
                                        onPhoneClick = { phone ->
                                            onAction(
                                                CarRentAction.OnPhoneClick(
                                                    phone
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategorySection(
    category: String,
    cars: List<Car>,
    totalCount: Int,
    onShowMore: () -> Unit,
    onCarClick: (Car) -> Unit,
    onPhoneClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            if (totalCount > 4) {
                TextButton(onClick = onShowMore) {
                    Text(
                        text = "আরও দেখুন ($totalCount)",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val pagerState = rememberPagerState(pageCount = { cars.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 16.dp
        ) { page ->
            CarCard(
                car = cars[page],
                onClick = { onCarClick(cars[page]) },
                onPhoneClick = onPhoneClick
            )
        }
    }
}

