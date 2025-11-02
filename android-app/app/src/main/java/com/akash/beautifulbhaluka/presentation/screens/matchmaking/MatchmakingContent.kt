package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.BridegroomContent
import com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.MatchmakerContent
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
    onNavigateToManageMatchmakers: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Sticky Top Bar (always visible at top - NOT scrollable)
            MatchmakingTopBar(
                onFilterClick = { onAction(MatchmakingAction.ToggleFilters) },
                showFilters = uiState.showFilters,
                onManageProfilesClick = {
                    if (uiState.selectedTab == MatchmakingTab.PROFILES) {
                        onNavigateToManageProfiles?.invoke()
                    } else {
                        onNavigateToManageMatchmakers?.invoke()
                    }
                }
            )

            // Scrollable Content (everything else scrolls together)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (uiState.selectedTab) {
                    MatchmakingTab.PROFILES -> {
                        BridegroomContent(
                            filteredProfiles = uiState.filteredProfiles,
                            selectedCategory = uiState.selectedCategory,
                            isLoading = uiState.isLoading,
                            showFilters = uiState.showFilters,
                            selectedGender = uiState.selectedGenderFilter,
                            selectedAgeRange = uiState.selectedAgeRange,
                            searchQuery = uiState.searchQuery,
                            onSearchChange = { onAction(MatchmakingAction.Search(it)) },
                            onTabSelected = { onAction(MatchmakingAction.SelectTab(it)) },
                            onCategorySelected = { category ->
                                onAction(MatchmakingAction.SelectCategory(category))
                            },
                            onGenderChange = { gender ->
                                onAction(MatchmakingAction.FilterByGender(gender))
                            },
                            onAgeRangeChange = { range ->
                                onAction(MatchmakingAction.FilterByAgeRange(range))
                            },
                            onClearFilters = {
                                onAction(MatchmakingAction.ClearFilters)
                            },
                            onNavigateToDetails = { profileId ->
                                onNavigateToDetails?.invoke(profileId)
                            }
                        )
                    }

                    MatchmakingTab.MATCHMAKERS -> {
                        MatchmakerContent(
                            filteredMatchmakers = uiState.filteredMatchmakers,
                            isLoading = uiState.isLoading,
                            showFilters = uiState.showFilters,
                            selectedSpecialization = uiState.selectedSpecialization,
                            searchQuery = uiState.searchQuery,
                            onSearchChange = { onAction(MatchmakingAction.Search(it)) },
                            onTabSelected = { onAction(MatchmakingAction.SelectTab(it)) },
                            onSpecializationChange = { specialization ->
                                onAction(MatchmakingAction.FilterBySpecialization(specialization))
                            },
                            onClearFilters = {
                                onAction(MatchmakingAction.ClearFilters)
                            },
                            onNavigateToDetails = { matchmakerId ->
                                onNavigateToMatchmakerDetails?.invoke(matchmakerId)
                            }
                        )
                    }
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
    onManageProfilesClick: () -> Unit
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

