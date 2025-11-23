package com.akash.beautifulbhaluka.presentation.screens.carrent.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.carrent.CarRentAction
import com.akash.beautifulbhaluka.presentation.screens.carrent.CarRentViewModel
import com.akash.beautifulbhaluka.presentation.screens.carrent.components.CarCard

@Composable
fun CategoryCarsScreen(
    category: String,
    viewModel: CarRentViewModel = viewModel(),
    navigateBack: () -> Unit,
    navigateHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val filteredCars = remember(uiState.cars, category) {
        uiState.cars.filter { it.category == category }
    }

    CategoryCarsContent(
        category = category,
        cars = filteredCars,
        isLoading = uiState.isLoading,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
        navigateHome = navigateHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCarsContent(
    category: String,
    cars: List<com.akash.beautifulbhaluka.presentation.screens.carrent.Car>,
    isLoading: Boolean,
    onAction: (CarRentAction) -> Unit,
    navigateBack: () -> Unit,
    navigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = category,
                onNavigateBack = navigateBack,
                onNavigateHome = navigateHome
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                cars.isEmpty() -> {
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
                                text = "কোনো $category গাড়ি নেই",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "এই ক্যাটাগরিতে এখনো কোনো গাড়ি যুক্ত হয়নি",
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
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(
                                text = "${cars.size}টি গাড়ি পাওয়া গেছে",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        items(cars) { car ->
                            CarCard(
                                car = car,
                                onClick = { onAction(CarRentAction.OnCarClick(car)) },
                                onPhoneClick = { phone ->
                                    onAction(CarRentAction.OnPhoneClick(phone))
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

