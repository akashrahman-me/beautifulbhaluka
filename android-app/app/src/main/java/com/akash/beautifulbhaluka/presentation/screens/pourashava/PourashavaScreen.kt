package com.akash.beautifulbhaluka.presentation.screens.pourashava

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.CollapsibleSection
import com.akash.beautifulbhaluka.presentation.components.common.MarkdownText
import com.akash.beautifulbhaluka.presentation.components.common.ModernTable

@Composable
fun PourashavaScreen(
    viewModel: PourashavaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    PourashavaContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun PourashavaContent(
    uiState: PourashavaUiState,
    onAction: (PourashavaAction) -> Unit
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        strokeWidth = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "তথ্য লোড হচ্ছে...",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )
                }
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "⚠️",
                            style = MaterialTheme.typography.displaySmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "একটি সমস্যা হয়েছে",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = uiState.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        FilledTonalButton(
                            onClick = { onAction(PourashavaAction.LoadData) },
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .fillMaxWidth()
                        ) {
                            Text("পুনরায় চেষ্টা করুন")
                        }
                    }
                }
            }
        }

        uiState.pourashavaInfo != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Main title with modern styling
                TitleSection(title = uiState.pourashavaInfo.title)

                // Collapsible sections
                SectionHeader(title = "পৌরসভার তথ্যাবলী")

                uiState.pourashavaInfo.sections.forEachIndexed { index, section ->
                    CollapsibleSection(
                        title = section.title,
                        content = {
                            MarkdownText(
                                text = section.content,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        isExpanded = uiState.expandedSections.contains(index),
                        onToggle = { onAction(PourashavaAction.ToggleSection(index)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Modern table section
                Spacer(modifier = Modifier.height(16.dp))
                ModernTable(
                    title = uiState.pourashavaInfo.wardsTable.title,
                    headers = listOf("ওয়ার্ডের নং", "ওয়ার্ডে অন্তর্ভুক্ত গ্রাম/মহল্লার নাম"),
                    rows = uiState.pourashavaInfo.wardsTable.wards.map { ward ->
                        listOf(ward.number, ward.areas)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TitleSection(
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)
                        )
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp,
                    lineHeight = 40.sp
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(24.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(2.dp)
                )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}
