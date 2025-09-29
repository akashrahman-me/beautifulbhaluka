package com.akash.beautifulbhaluka.presentation.screens.freedomfighter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ModernTable

@Composable
fun FreedomFighterScreen(
    viewModel: FreedomFighterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FreedomFighterContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun FreedomFighterContent(
    uiState: FreedomFighterUiState,
    onAction: (FreedomFighterAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            // Modern Header Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Minimalist Title
                    Text(
                        text = "মুক্তিযোদ্ধার তালিকা",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = 36.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Clean underline accent
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(2.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(1.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Subtitle with refined typography
                    Text(
                        text = "ভালুকার বীর মুক্তিযোদ্ধাদের সম্মানিত তালিকা",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Clean Information Notice
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "এখানে অল্প কয়েকজনের তালিকা দেওয়া হলো। সম্পূর্ণ তালিকা দেখতে নিচে থেকে PDF ডাউনলোড করুন।",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }

            // Table Section (unchanged)
            item {
                when {
                    uiState.isLoading -> {
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

                    uiState.error != null -> {
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

                    else -> {
                        // Convert data to table format
                        val headers = listOf(
                            "ক্রঃ নং",
                            "পরিচিতি নম্বর",
                            "বীর মুক্তিযোদ্ধার নাম",
                            "পিতার নাম",
                            "গ্রাম",
                            "ডাকঘর",
                            "প্রমাণকের বিবরণ"
                        )

                        val rows = uiState.freedomFighters.map { fighter ->
                            listOf(
                                fighter.serialNumber,
                                fighter.identificationNumber,
                                fighter.name,
                                fighter.fatherName,
                                fighter.village,
                                fighter.postOffice,
                                fighter.certificateDetails
                            )
                        }

                        ModernTable(
                            title = "",
                            headers = headers,
                            rows = rows,
                            showTitle = false,
                            modifier = Modifier.fillMaxWidth(),
                            minColumnWidth = 100.dp,
                            maxColumnWidth = 250.dp,
                            alternateRowColors = true
                        )
                    }
                }
            }

            // Minimal Download Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Clean section divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Download section title
                    Text(
                        text = "সম্পূর্ণ তালিকা",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Description with cleaner formatting
                    Text(
                        text = "ভালুকা উপজেলার সকল বীর মুক্তিযোদ্ধার সম্পূর্ণ তালিকা PDF ফরম্যাটে উপলব্ধ।",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Modern minimal button
                    OutlinedButton(
                        onClick = { onAction(FreedomFighterAction.DownloadPdf) },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 1.5.dp
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FileDownload,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PDF ডাউনলোড",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
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
