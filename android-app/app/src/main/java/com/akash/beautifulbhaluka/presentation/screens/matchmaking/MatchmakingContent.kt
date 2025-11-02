package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.domain.model.ProfileCategory
import com.akash.beautifulbhaluka.presentation.components.common.MatchmakerCard
import com.akash.beautifulbhaluka.presentation.components.common.MatchmakingProfileCard
import com.composables.icons.lucide.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingContent(
    uiState: MatchmakingUiState,
    onAction: (MatchmakingAction) -> Unit,
    onNavigateToDetails: ((String) -> Unit)?,
    onNavigateToPublish: (() -> Unit)?,
    onNavigateToManageProfiles: (() -> Unit)?,
    onNavigateToMatchmakerDetails: ((String) -> Unit)? = null,
    onNavigateToPublishMatchmaker: (() -> Unit)? = null,
    onNavigateToManageMatchmakers: (() -> Unit)? = null,
    scrollState: LazyListState,
    showHeader: Boolean
) {
    val gradientColors = listOf(
        Color(0xFFFF6B9D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Sticky Top Bar (always visible at top)
            MatchmakingTopBar(
                onFilterClick = { onAction(MatchmakingAction.ToggleFilters) },
                showFilters = uiState.showFilters,
                onManageProfilesClick = {
                    if (uiState.selectedTab == MatchmakingTab.PROFILES) {
                        onNavigateToManageProfiles?.invoke()
                    } else {
                        onNavigateToManageMatchmakers?.invoke()
                    }
                },
                selectedTab = uiState.selectedTab
            )

            // Scrollable Content
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                // Search Bar (scrolls with content)
                item {
                    SearchBar(
                        searchQuery = uiState.searchQuery,
                        onSearchChange = { onAction(MatchmakingAction.Search(it)) }
                    )
                }

                // Tab Selector
                item {
                    TabSelector(
                        selectedTab = uiState.selectedTab,
                        onTabSelected = { onAction(MatchmakingAction.SelectTab(it)) }
                    )
                }

                // Hero Section with Gradient
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(
                                Brush.horizontalGradient(
                                    if (uiState.selectedTab == MatchmakingTab.PROFILES)
                                        gradientColors
                                    else
                                        listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                imageVector = if (uiState.selectedTab == MatchmakingTab.PROFILES)
                                    Lucide.Heart
                                else
                                    Lucide.Users,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (uiState.selectedTab == MatchmakingTab.PROFILES)
                                    "Find Your Perfect Match"
                                else
                                    "Connect with Matchmakers",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = if (uiState.selectedTab == MatchmakingTab.PROFILES)
                                    "${uiState.filteredProfiles.size} Profiles Available"
                                else
                                    "${uiState.filteredMatchmakers.size} Matchmakers Available",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }

                // Category Chips (Only for Profiles)
                if (uiState.selectedTab == MatchmakingTab.PROFILES) {
                    item {
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
                    }
                }

                // Filters Section
                item {
                    AnimatedVisibility(
                        visible = uiState.showFilters,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        if (uiState.selectedTab == MatchmakingTab.PROFILES) {
                            FiltersSection(
                                selectedGender = uiState.selectedGenderFilter,
                                selectedAgeRange = uiState.selectedAgeRange,
                                onGenderChange = { onAction(MatchmakingAction.FilterByGender(it)) },
                                onAgeRangeChange = { onAction(MatchmakingAction.FilterByAgeRange(it)) },
                                onClearFilters = { onAction(MatchmakingAction.ClearFilters) }
                            )
                        } else {
                            MatchmakerFiltersSection(
                                selectedSpecialization = uiState.selectedSpecialization,
                                onSpecializationChange = {
                                    onAction(
                                        MatchmakingAction.FilterBySpecialization(
                                            it
                                        )
                                    )
                                },
                                onClearFilters = { onAction(MatchmakingAction.ClearFilters) }
                            )
                        }
                    }
                }

                // Content based on selected tab
                if (uiState.selectedTab == MatchmakingTab.PROFILES) {
                    // Profiles List
                    if (uiState.isLoading) {
                        items(3) {
                            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                ProfileCardShimmer()
                            }
                        }
                    } else if (uiState.filteredProfiles.isEmpty()) {
                        item {
                            EmptyState()
                        }
                    } else {
                        items(uiState.filteredProfiles) { profile ->
                            MatchmakingProfileCard(
                                profile = profile,
                                onClick = { onNavigateToDetails?.invoke(profile.id) },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }
                    }
                } else {
                    // Matchmakers List
                    if (uiState.isLoading) {
                        items(3) {
                            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                ProfileCardShimmer()
                            }
                        }
                    } else if (uiState.filteredMatchmakers.isEmpty()) {
                        item {
                            EmptyState()
                        }
                    } else {
                        items(uiState.filteredMatchmakers) { matchmaker ->
                            MatchmakerCard(
                                matchmaker = matchmaker,
                                onClick = { onNavigateToMatchmakerDetails?.invoke(matchmaker.id) },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        // Floating Action Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    if (uiState.selectedTab == MatchmakingTab.PROFILES) {
                        onNavigateToPublish?.invoke()
                    } else {
                        onNavigateToPublishMatchmaker?.invoke()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.shadow(8.dp, CircleShape)
            ) {
                Icon(
                    imageVector = Lucide.Plus,
                    contentDescription = if (uiState.selectedTab == MatchmakingTab.PROFILES)
                        "Create Profile"
                    else
                        "Create Matchmaker Profile"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingTopBar(
    onFilterClick: () -> Unit,
    showFilters: Boolean,
    onManageProfilesClick: () -> Unit,
    selectedTab: MatchmakingTab
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(start = 16.dp, end = 10.dp, top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💍 Matchmaking",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Manage Profiles button
                IconButton(
                    onClick = onManageProfilesClick
                ) {
                    Icon(
                        imageVector = Lucide.CircleUserRound,
                        contentDescription = "My Profiles",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Filter button
                IconButton(
                    onClick = onFilterClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (showFilters)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Lucide.ListFilter,
                        contentDescription = "Filters",
                        tint = if (showFilters)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        placeholder = {
            Text(
                text = "Search by name, occupation, location...",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Lucide.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchChange("") }) {
                    Icon(
                        imageVector = Lucide.X,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun CategoryChip(
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

@Composable
fun TabSelector(
    selectedTab: MatchmakingTab,
    onTabSelected: (MatchmakingTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        MatchmakingTab.PROFILES to "Bride & Groom",
        MatchmakingTab.MATCHMAKERS to "Matchmakers"
    )
    val selectedTabIndex = tabs.indexOfFirst { it.first == selectedTab }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {}
    ) {
        tabs.forEachIndexed { index, (tab, title) ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (tab == MatchmakingTab.PROFILES) Lucide.Heart else Lucide.Users,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MatchmakerFiltersSection(
    selectedSpecialization: String,
    onSpecializationChange: (String) -> Unit,
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

            // Specialization Filter
            Text(
                text = "Specialization",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("All", "Elite Families", "Doctors", "Engineers").forEach { spec ->
                        FilterChip(
                            selected = selectedSpecialization == spec,
                            onClick = { onSpecializationChange(spec) },
                            label = {
                                Text(
                                    text = spec,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Business", "Overseas", "General").forEach { spec ->
                        FilterChip(
                            selected = selectedSpecialization == spec,
                            onClick = { onSpecializationChange(spec) },
                            label = {
                                Text(
                                    text = spec,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
            }
        }
    }
}
