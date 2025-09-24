package com.akash.beautifulbhaluka.presentation.screens.upazila

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.presentation.screens.upazila.components.CollapsibleSection

@Composable
fun UpazilaScreen(
    viewModel: UpazilaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    UpazilaContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun UpazilaContent(
    uiState: UpazilaUiState,
    onAction: (UpazilaAction) -> Unit
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "একটি সমস্যা হয়েছে",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = uiState.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Button(
                        onClick = { onAction(UpazilaAction.LoadData) },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("পুনরায় চেষ্টা করুন")
                    }
                }
            }
        }

        uiState.upazilaInfo != null -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                item {
                    Text(
                        text = uiState.upazilaInfo.title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // Description
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    ) {
                        Text(
                            text = uiState.upazilaInfo.description,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(16.dp),
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
                        )
                    }
                }

                // Image placeholder
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        AsyncImage(
                            model = uiState.upazilaInfo.imageUrl,
                            contentDescription = "ভালুকা উপজেলার ছবি",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Section title
                item {
                    Text(
                        text = "বিস্তারিত তথ্য",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                // Collapsible sections
                itemsIndexed(uiState.upazilaInfo.sections) { index, section ->
                    CollapsibleSection(
                        section = section,
                        isExpanded = uiState.expandedSections.contains(index),
                        onToggle = { onAction(UpazilaAction.ToggleSection(index)) }
                    )
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
