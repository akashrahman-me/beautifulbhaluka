package com.akash.beautifulbhaluka.presentation.screens.ambulance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.ambulance.components.AmbulanceCard

@Composable
fun AmbulanceScreen(
    viewModel: AmbulanceViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AmbulanceContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack,
        navigateToHome = navigateToHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmbulanceContent(
    uiState: AmbulanceUiState,
    onAction: (AmbulanceAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "এম্বুলেন্স",
                onNavigateBack = navigateBack,
                onNavigateHome = navigateToHome
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(
                            items = uiState.ambulances,
                            key = { "${it.title}-${it.organization}-${it.phoneNumbers.joinToString()}" }
                        ) { ambulanceInfo ->
                            AmbulanceCard(
                                ambulanceInfo = ambulanceInfo,
                                onPhoneClick = { phoneNumber ->
                                    onAction(AmbulanceAction.DialPhone(phoneNumber))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
