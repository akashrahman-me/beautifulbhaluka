package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile
import com.akash.beautifulbhaluka.domain.model.ProfileCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingContent(
    uiState: MatchmakingUiState,
    onAction: (MatchmakingAction) -> Unit,
    onNavigateToDetails: ((String) -> Unit)?,
    onNavigateToPublish: (() -> Unit)?
) {
    val gradientColors = listOf(
        Color(0xFFFF6B9D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B)
    )

    Scaffold(
        topBar = {
            MatchmakingTopBar(
                searchQuery = uiState.searchQuery,
                onSearchChange = { onAction(MatchmakingAction.Search(it)) },
                onFilterClick = { onAction(MatchmakingAction.ToggleFilters) },
                showFilters = uiState.showFilters
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToPublish?.invoke() },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.shadow(8.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Profile"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Hero Section with Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.horizontalGradient(gradientColors)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Find Your Perfect Match",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${uiState.filteredProfiles.size} Profiles Available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // Category Chips
            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(ProfileCategory.entries) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = uiState.selectedCategory == category,
                        onClick = { onAction(MatchmakingAction.SelectCategory(category)) }
                    )
                }
            }

            // Filters Section
            AnimatedVisibility(
                visible = uiState.showFilters,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                FiltersSection(
                    selectedGender = uiState.selectedGenderFilter,
                    selectedAgeRange = uiState.selectedAgeRange,
                    onGenderChange = { onAction(MatchmakingAction.FilterByGender(it)) },
                    onAgeRangeChange = { onAction(MatchmakingAction.FilterByAgeRange(it)) },
                    onClearFilters = { onAction(MatchmakingAction.ClearFilters) }
                )
            }

            // Profiles List
            if (uiState.isLoading) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(3) {
                        ProfileCardShimmer()
                    }
                }
            } else if (uiState.filteredProfiles.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.filteredProfiles) { profile ->
                        ProfileCard(
                            profile = profile,
                            onClick = { onNavigateToDetails?.invoke(profile.id) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingTopBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    showFilters: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💍 Matchmaking",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                IconButton(
                    onClick = onFilterClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (showFilters)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filters",
                        tint = if (showFilters)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by name, occupation, location...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: ProfileCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val icon = when (category) {
        ProfileCategory.ALL -> Icons.Default.People
        ProfileCategory.RECENT -> Icons.Default.Schedule
        ProfileCategory.VERIFIED -> Icons.Default.Verified
        ProfileCategory.PREMIUM -> Icons.Default.Star
    }

    val label = when (category) {
        ProfileCategory.ALL -> "All"
        ProfileCategory.RECENT -> "Recent"
        ProfileCategory.VERIFIED -> "Verified"
        ProfileCategory.PREMIUM -> "Premium"
    }

    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = Color.White,
            selectedLeadingIconColor = Color.White
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            selectedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun FiltersSection(
    selectedGender: String,
    selectedAgeRange: IntRange,
    onGenderChange: (String) -> Unit,
    onAgeRangeChange: (IntRange) -> Unit,
    onClearFilters: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onClearFilters) {
                    Text("Clear All")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Gender Filter
            Text(
                text = "Gender",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Male", "Female").forEach { gender ->
                    FilterChip(
                        selected = selectedGender == gender,
                        onClick = { onGenderChange(gender) },
                        label = { Text(gender) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Age Range Filter
            Text(
                text = "Age Range: ${selectedAgeRange.first} - ${selectedAgeRange.last}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            var sliderPosition by remember(selectedAgeRange) {
                mutableStateOf(selectedAgeRange.first.toFloat()..selectedAgeRange.last.toFloat())
            }
            RangeSlider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                onValueChangeFinished = {
                    onAgeRangeChange(sliderPosition.start.toInt()..sliderPosition.endInclusive.toInt())
                },
                valueRange = 18f..60f,
                steps = 41
            )
        }
    }
}

@Composable
fun ProfileCard(
    profile: MatchmakingProfile,
    onClick: () -> Unit
) {
    val gradientColors = if (profile.verified) {
        listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)
        )
    } else {
        listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(gradientColors))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFFFF6B9D),
                                            Color(0xFFC06C84)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = profile.name.first().toString(),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                if (profile.verified) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified",
                                        tint = Color(0xFF1DA1F2),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Text(
                                text = "${profile.age} years • ${profile.height}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    // Gender Icon
                    Icon(
                        imageVector = if (profile.gender == "Male")
                            Icons.Default.Male
                        else Icons.Default.Female,
                        contentDescription = profile.gender,
                        tint = if (profile.gender == "Male")
                            Color(0xFF2196F3)
                        else Color(0xFFE91E63),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Info Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoChip(
                        icon = Icons.Default.Work,
                        text = profile.occupation,
                        modifier = Modifier.weight(1f)
                    )
                    InfoChip(
                        icon = Icons.Default.School,
                        text = profile.education,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                InfoChip(
                    icon = Icons.Default.LocationOn,
                    text = profile.location,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Bio
                Text(
                    text = profile.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                if (profile.interests.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(profile.interests.take(3)) { interest ->
                            AssistChip(
                                onClick = { },
                                label = { Text(interest, style = MaterialTheme.typography.bodySmall) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Tag,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Button
                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Full Profile")
                }
            }
        }
    }
}

@Composable
fun InfoChip(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ProfileCardShimmer() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Shimmer placeholder content
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )
            }
        }
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
            Text(
                text = "No profiles found",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Try adjusting your filters",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
