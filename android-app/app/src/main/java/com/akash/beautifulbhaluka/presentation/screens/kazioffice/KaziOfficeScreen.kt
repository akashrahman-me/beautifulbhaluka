package com.akash.beautifulbhaluka.presentation.screens.kazioffice

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar
import com.akash.beautifulbhaluka.presentation.screens.kazioffice.components.KaziCard

@Composable
fun KaziOfficeScreen(
    viewModel: KaziOfficeViewModel = viewModel(),
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    KaziOfficeContent(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is KaziOfficeAction.CallPhone -> {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${action.phoneNumber}")
                    }
                    context.startActivity(intent)
                }

                else -> viewModel.onAction(action)
            }
        },
        navigateBack = navigateBack,
        navigateToHome = navigateToHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KaziOfficeContent(
    uiState: KaziOfficeUiState,
    onAction: (KaziOfficeAction) -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "কাজী অফিস",
                onNavigateBack = navigateBack,
                onNavigateHome = navigateToHome
            )
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Content based on state
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
                                        .height(200.dp),
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
                        // Kazi cards
                        items(
                            items = uiState.kazis,
                            key = { it.name }
                        ) { kazi ->
                            KaziCard(
                                kazi = kazi,
                                onCallClick = { phoneNumber ->
                                    onAction(KaziOfficeAction.CallPhone(phoneNumber))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
