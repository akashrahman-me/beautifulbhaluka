package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile
import com.akash.beautifulbhaluka.domain.model.ProfileCategory
import com.akash.beautifulbhaluka.presentation.components.common.MatchmakingProfileCard
import com.composables.icons.lucide.*

@Composable
fun BridegroomContent(
    filteredProfiles: List<MatchmakingProfile>,
    selectedCategory: ProfileCategory,
    isLoading: Boolean,
    showFilters: Boolean,
    selectedGender: String,
    selectedAgeRange: IntRange,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onTabSelected: (com.akash.beautifulbhaluka.presentation.screens.matchmaking.MatchmakingTab) -> Unit,
    onCategorySelected: (ProfileCategory) -> Unit,
    onGenderChange: (String) -> Unit,
    onAgeRangeChange: (IntRange) -> Unit,
    onClearFilters: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        Color(0xFFFF6B9D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B)
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Search Bar
        item {
            com.akash.beautifulbhaluka.presentation.screens.matchmaking.SearchBar(
                searchQuery = searchQuery,
                onSearchChange = onSearchChange
            )
        }

        // Tab Selector
        item {
            com.akash.beautifulbhaluka.presentation.screens.matchmaking.TabSelector(
                selectedTab = com.akash.beautifulbhaluka.presentation.screens.matchmaking.MatchmakingTab.PROFILES,
                onTabSelected = onTabSelected
            )
        }

        // Hero Section with Gradient
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Brush.horizontalGradient(gradientColors)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Lucide.Heart,
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
                        text = "${filteredProfiles.size} Profiles Available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Category Chips
        item {
            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(ProfileCategory.entries) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
        }

        // Filters Section
        item {
            AnimatedVisibility(
                visible = showFilters,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                BridegroomFiltersSection(
                    selectedGender = selectedGender,
                    selectedAgeRange = selectedAgeRange,
                    onGenderChange = onGenderChange,
                    onAgeRangeChange = onAgeRangeChange,
                    onClearFilters = onClearFilters
                )
            }
        }

        // Profiles List
        if (isLoading) {
            items(3) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    ProfileCardShimmer()
                }
            }
        } else if (filteredProfiles.isEmpty()) {
            item {
                EmptyState()
            }
        } else {
            items(filteredProfiles) { profile ->
                MatchmakingProfileCard(
                    profile = profile,
                    onClick = { onNavigateToDetails(profile.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryChip(
    category: ProfileCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val icon = when (category) {
        ProfileCategory.ALL -> Lucide.Users
        ProfileCategory.RECENT -> Lucide.Clock
        ProfileCategory.VERIFIED -> Lucide.BadgeCheck
        ProfileCategory.PREMIUM -> Lucide.Star
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
private fun BridegroomFiltersSection(
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
private fun ProfileCardShimmer() {
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
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(30.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Lucide.SearchX,
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

