package com.akash.beautifulbhaluka.presentation.screens.bloodbank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.components.DonorCard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.LogOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodBankScreen(
    viewModel: BloodBankViewModel = hiltViewModel(),
    onPhoneCall: (String) -> Unit = {},
    onNavigateToGuidelines: () -> Unit = {},
    onNavigateToPublish: () -> Unit = {},
    onNavigateToManage: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setPhoneCallback(onPhoneCall)
        viewModel.onAction(BloodBankAction.LoadData)
    }

    BloodBankContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateToGuidelines = onNavigateToGuidelines,
        onNavigateToPublish = onNavigateToPublish,
        onNavigateToManage = onNavigateToManage,
        onNavigateToHome = onNavigateToHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodBankContent(
    uiState: BloodBankUiState,
    onAction: (BloodBankAction) -> Unit,
    onNavigateToGuidelines: () -> Unit,
    onNavigateToPublish: () -> Unit,
    onNavigateToManage: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = CircleShape,
                                    spotColor = Color(0xFFE53935).copy(alpha = 0.3f)
                                )
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFE53935),
                                            Color(0xFFFF6B6B)
                                        )
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🩸",
                                fontSize = 20.sp
                            )
                        }
                        Column {
                            Text(
                                text = "রক্তদাতা",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.5).sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "${uiState.donors.size} জন উপলব্ধ",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(
                            imageVector = Lucide.LogOut,
                            contentDescription = "Go to Home",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            // Quick Actions Grid
            item {
                QuickActionsGrid(
                    onGuidelinesClick = onNavigateToGuidelines,
                    onPublishClick = onNavigateToPublish,
                    onManageClick = onNavigateToManage
                )
            }

            // Section Header
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Available Donors",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Verified blood donors in your area",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Loading State
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // Error State
            if (uiState.error != null) {
                item {
                    ErrorCard(error = uiState.error)
                }
            }

            // Donor Cards
            items(uiState.donors, key = { it.id }) { donor ->
                DonorCard(
                    donor = donor,
                    onCallClick = { onAction(BloodBankAction.CallPhone(donor.phone)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Bottom Spacer
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun QuickActionsGrid(
    onGuidelinesClick: () -> Unit,
    onPublishClick: () -> Unit,
    onManageClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Guidelines Button (Full Width)
        QuickActionButton(
            title = "রক্তদান নির্দেশিকা",
            subtitle = "গুরুত্বপূর্ণ তথ্য ও পরামর্শ",
            icon = Icons.AutoMirrored.Outlined.MenuBook,
            gradientColors = listOf(Color(0xFF2196F3), Color(0xFF42A5F5)),
            onClick = onGuidelinesClick,
            modifier = Modifier.fillMaxWidth(),
            nextArrow = true
        )

        // Publish and Manage Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                title = "প্রকাশ করুন",
                subtitle = "নতুন রক্তদাতা",
                icon = Icons.Outlined.Add,
                gradientColors = listOf(Color(0xFF4CAF50), Color(0xFF66BB6A)),
                onClick = onPublishClick,
                modifier = Modifier.weight(1f)
            )

            QuickActionButton(
                title = "পরিচালনা",
                subtitle = "আপনার তথ্য",
                icon = Icons.Outlined.ManageAccounts,
                gradientColors = listOf(Color(0xFFFF9800), Color(0xFFFFB74D)),
                onClick = onManageClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuickActionButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    nextArrow: Boolean = false
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = gradientColors[0].copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(gradientColors)
                )
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
                if (nextArrow) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorCard(error: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

