package com.akash.beautifulbhaluka.presentation.screens.bloodbank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.composables.icons.lucide.Droplet

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
    val bloodGroups = viewModel.getBloodGroups()
    val availabilityStatuses = viewModel.getAvailabilityStatuses()

    LaunchedEffect(Unit) {
        viewModel.setPhoneCallback(onPhoneCall)
        viewModel.onAction(BloodBankAction.LoadData)
    }

    BloodBankContent(
        uiState = uiState,
        bloodGroups = bloodGroups,
        availabilityStatuses = availabilityStatuses,
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
    bloodGroups: List<String>,
    availabilityStatuses: List<String>,
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
                            Icon(
                                imageVector = Lucide.Droplet,
                                contentDescription = "Blood Donor",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
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
                                text = "মোট ${toBengaliNumber(uiState.totalDonors)} জন",
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

            // Filters Section
            item {
                FiltersSection(
                    bloodGroups = bloodGroups,
                    availabilityStatuses = availabilityStatuses,
                    selectedBloodGroup = uiState.selectedBloodGroup,
                    selectedAvailability = uiState.selectedAvailability,
                    bloodGroupCounts = uiState.bloodGroupCounts,
                    totalDonors = uiState.totalDonors,
                    onBloodGroupSelected = { onAction(BloodBankAction.FilterByBloodGroup(it)) },
                    onAvailabilitySelected = { onAction(BloodBankAction.FilterByAvailability(it)) }
                )
            }

            // Section Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(20.dp),
                            spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Icon Container
                            Surface(
                                modifier = Modifier.size(56.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                shadowElevation = 0.dp
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                                                )
                                            )
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Group,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            // Text Content
                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "উপলব্ধ রক্তদাতা",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    letterSpacing = (-0.3).sp
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "যাচাইকৃত এবং বিশ্বস্ত রক্তদাতা",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Count Badge
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary,
                                shadowElevation = 0.dp
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    MaterialTheme.colorScheme.primary,
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                                )
                                            )
                                        )
                                        .padding(horizontal = 14.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        text = "${uiState.donors.size}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }
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

            // Empty State
            if (!uiState.isLoading && uiState.error == null && uiState.donors.isEmpty()) {
                item {
                    EmptyStateCard()
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

@Composable
private fun EmptyStateCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 0.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Icon Container
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                                    )
                                )
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                // Text Content
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "কোনো ফলাফল পাওয়া যায়নি",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "ফিল্টার পরিবর্তন করে আবার চেষ্টা করুন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun FiltersSection(
    bloodGroups: List<String>,
    availabilityStatuses: List<String>,
    selectedBloodGroup: String?,
    selectedAvailability: String?,
    bloodGroupCounts: Map<String, Int>,
    totalDonors: Int,
    onBloodGroupSelected: (String?) -> Unit,
    onAvailabilitySelected: (String?) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Blood Group Filter
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Lucide.Droplet,
                    contentDescription = null,
                    tint = Color(0xFFE53935), // Red color for blood icon
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "রক্তের গ্রুপ",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedBloodGroup == null,
                        onClick = { onBloodGroupSelected(null) },
                        label = { Text("সকল (${toBengaliNumber(totalDonors)})") },
                        leadingIcon = if (selectedBloodGroup == null) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }

                items(bloodGroups) { bloodGroup ->
                    val count = bloodGroupCounts[bloodGroup] ?: 0
                    FilterChip(
                        selected = selectedBloodGroup == bloodGroup,
                        onClick = {
                            onBloodGroupSelected(if (selectedBloodGroup == bloodGroup) null else bloodGroup)
                        },
                        label = { Text("$bloodGroup (${toBengaliNumber(count)})") },
                        leadingIcon = if (selectedBloodGroup == bloodGroup) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Availability Filter
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "উপলব্ধতা",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedAvailability == null,
                    onClick = { onAvailabilitySelected(null) },
                    label = { Text("সকল") },
                    leadingIcon = if (selectedAvailability == null) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                availabilityStatuses.forEachIndexed { index, status ->
                    val (color, icon) = when (status) {
                        "সময় হয়েছে" -> Color(0xFF4CAF50) to Icons.Default.CheckCircle
                        "সময় হয়নি" -> Color(0xFFFF9800) to Icons.Default.Cancel
                        else -> MaterialTheme.colorScheme.primary to Icons.Default.Circle
                    }

                    FilterChip(
                        selected = selectedAvailability == status,
                        onClick = {
                            onAvailabilitySelected(if (selectedAvailability == status) null else status)
                        },
                        label = { Text(status) },
                        leadingIcon = if (selectedAvailability == status) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else {
                            {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = color,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = color,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

// Helper function to convert numbers to Bengali numerals
private fun toBengaliNumber(number: Int): String {
    return number.toString().map { char ->
        when (char) {
            '0' -> '০'
            '1' -> '১'
            '2' -> '২'
            '3' -> '৩'
            '4' -> '৪'
            '5' -> '৫'
            '6' -> '৬'
            '7' -> '৭'
            '8' -> '৮'
            '9' -> '৯'
            else -> char
        }
    }.joinToString("")
}

