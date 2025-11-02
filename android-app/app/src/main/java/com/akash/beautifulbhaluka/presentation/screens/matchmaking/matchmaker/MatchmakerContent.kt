package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.akash.beautifulbhaluka.domain.model.Matchmaker
import com.akash.beautifulbhaluka.presentation.components.common.MatchmakerCard
import com.composables.icons.lucide.*

@Composable
fun MatchmakerContent(
    filteredMatchmakers: List<Matchmaker>,
    isLoading: Boolean,
    showFilters: Boolean,
    selectedSpecialization: String,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onTabSelected: (com.akash.beautifulbhaluka.presentation.screens.matchmaking.MatchmakingTab) -> Unit,
    onSpecializationChange: (String) -> Unit,
    onClearFilters: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        Color(0xFF667EEA),
        Color(0xFF764BA2)
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
                selectedTab = com.akash.beautifulbhaluka.presentation.screens.matchmaking.MatchmakingTab.MATCHMAKERS,
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
                        imageVector = Lucide.Users,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Connect with Matchmakers",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${filteredMatchmakers.size} Matchmakers Available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
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
                MatchmakerFiltersSection(
                    selectedSpecialization = selectedSpecialization,
                    onSpecializationChange = onSpecializationChange,
                    onClearFilters = onClearFilters
                )
            }
        }

        // Matchmakers List
        if (isLoading) {
            items(3) {
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    MatchmakerCardShimmer()
                }
            }
        } else if (filteredMatchmakers.isEmpty()) {
            item {
                EmptyState()
            }
        } else {
            items(filteredMatchmakers) { matchmaker ->
                MatchmakerCard(
                    matchmaker = matchmaker,
                    onClick = { onNavigateToDetails(matchmaker.id) },
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
private fun MatchmakerFiltersSection(
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

@Composable
private fun MatchmakerCardShimmer() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
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
                        .fillMaxWidth(0.6f)
                        .height(24.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
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
                text = "No matchmakers found",
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

