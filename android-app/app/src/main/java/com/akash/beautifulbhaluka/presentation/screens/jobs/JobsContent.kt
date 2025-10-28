package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.*
import com.akash.beautifulbhaluka.presentation.components.common.ScrollAnimatedHeader
import com.akash.beautifulbhaluka.presentation.components.common.rememberScrollHeaderState
import java.text.NumberFormat
import java.util.*

@Composable
fun JobsContent(
    uiState: JobsUiState,
    listState: LazyListState = rememberLazyListState(),
    onAction: (JobsAction) -> Unit
) {
    val showHeader = rememberScrollHeaderState(scrollState = listState)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Animated Top Bar
            ScrollAnimatedHeader(visible = showHeader) {
                ModernJobsTopBar(currentTab = uiState.currentTab)
            }

            // Tab Navigation
            ModernTabBar(
                currentTab = uiState.currentTab,
                onTabSelected = { tab -> onAction(JobsAction.SelectTab(tab)) }
            )

            // Main Content
            when {
                uiState.isLoading && uiState.jobs.isEmpty() -> {
                    ModernLoadingState()
                }

                uiState.error != null && uiState.jobs.isEmpty() -> {
                    ModernErrorState(
                        errorMessage = uiState.error,
                        onRetry = { onAction(JobsAction.LoadJobs) }
                    )
                }

                else -> {
                    when (uiState.currentTab) {
                        JobTab.JOB_FEEDS -> JobFeedsContent(
                            uiState = uiState,
                            listState = listState,
                            onAction = onAction
                        )
                        JobTab.MY_APPLICATIONS -> ApplicationsContent(
                            applications = uiState.appliedJobs,
                            onJobClick = { jobId -> onAction(JobsAction.ViewJobDetails(jobId)) }
                        )
                        JobTab.FAVORITES -> FavoritesContent(
                            jobs = uiState.jobs,
                            favoriteJobIds = uiState.favoriteJobIds,
                            onJobClick = { jobId -> onAction(JobsAction.ViewJobDetails(jobId)) },
                            onFavoriteClick = { jobId -> onAction(JobsAction.ToggleFavorite(jobId)) }
                        )
                    }
                }
            }
        }

        // Modern Floating Action Button
        if (uiState.currentTab == JobTab.JOB_FEEDS) {
            ModernFAB(
                onClick = { onAction(JobsAction.NavigateToPublishJob) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun ModernJobsTopBar(currentTab: JobTab) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6366F1),
                        Color(0xFF8B5CF6),
                        Color(0xFFEC4899)
                    )
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "চাকরি",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = currentTab.banglaName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.WorkOutline,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ModernTabBar(
    currentTab: JobTab,
    onTabSelected: (JobTab) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 2.dp
    ) {
        TabRow(
            selectedTabIndex = currentTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color(0xFF6366F1),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentTab.ordinal]),
                    height = 3.dp,
                    color = Color(0xFF6366F1)
                )
            }
        ) {
            JobTab.values().forEach { tab ->
                Tab(
                    selected = currentTab == tab,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.height(56.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = getTabIcon(tab),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = if (currentTab == tab) Color(0xFF6366F1) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = tab.banglaName,
                            fontWeight = if (currentTab == tab) FontWeight.Bold else FontWeight.Normal,
                            color = if (currentTab == tab) Color(0xFF6366F1) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getTabIcon(tab: JobTab): ImageVector {
    return when (tab) {
        JobTab.JOB_FEEDS -> Icons.Outlined.Feed
        JobTab.MY_APPLICATIONS -> Icons.Outlined.Assignment
        JobTab.FAVORITES -> Icons.Outlined.FavoriteBorder
    }
}

@Composable
fun JobFeedsContent(
    uiState: JobsUiState,
    listState: LazyListState,
    onAction: (JobsAction) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Stats Card
        item {
            JobsStatsCard(
                totalJobs = uiState.jobs.size,
                favoriteJobs = uiState.favoriteJobIds.size,
                appliedJobs = uiState.appliedJobs.size
            )
        }

        // Featured Jobs Carousel
        if (uiState.featuredJobs.isNotEmpty()) {
            item {
                FeaturedJobsCarousel(
                    jobs = uiState.featuredJobs,
                    favoriteJobIds = uiState.favoriteJobIds,
                    onJobClick = { jobId -> onAction(JobsAction.ViewJobDetails(jobId)) },
                    onFavoriteClick = { jobId -> onAction(JobsAction.ToggleFavorite(jobId)) }
                )
            }
        }

        // Category Filter
        item {
            ModernCategoryFilter(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { category ->
                    onAction(JobsAction.FilterByCategory(category))
                }
            )
        }

        // Job Cards
        if (uiState.jobs.isEmpty()) {
            item {
                ModernEmptyState(
                    onPostJob = { onAction(JobsAction.NavigateToPublishJob) }
                )
            }
        } else {
            items(uiState.jobs, key = { it.id }) { job ->
                ModernJobCard(
                    job = job,
                    isFavorite = uiState.favoriteJobIds.contains(job.id),
                    onClick = { onAction(JobsAction.ViewJobDetails(job.id)) },
                    onFavoriteClick = { onAction(JobsAction.ToggleFavorite(job.id)) }
                )
            }

            // Pagination
            if (uiState.totalPages > 1) {
                item {
                    PaginationControls(
                        currentPage = uiState.currentPage,
                        totalPages = uiState.totalPages,
                        onPageChange = { page -> onAction(JobsAction.LoadPage(page)) }
                    )
                }
            }
        }

        // Bottom spacing for FAB
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun JobsStatsCard(
    totalJobs: Int,
    favoriteJobs: Int,
    appliedJobs: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                icon = Icons.Outlined.WorkOutline,
                value = totalJobs.toString(),
                label = "চাকরি",
                gradient = listOf(Color(0xFF6366F1), Color(0xFF8B5CF6))
            )

            VerticalDivider(
                modifier = Modifier.height(60.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            StatItem(
                icon = Icons.Outlined.FavoriteBorder,
                value = favoriteJobs.toString(),
                label = "সেভ",
                gradient = listOf(Color(0xFFEF4444), Color(0xFFF97316))
            )

            VerticalDivider(
                modifier = Modifier.height(60.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            StatItem(
                icon = Icons.Outlined.Assignment,
                value = appliedJobs.toString(),
                label = "আবেদন",
                gradient = listOf(Color(0xFF10B981), Color(0xFF059669))
            )
        }
    }
}

@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    label: String,
    gradient: List<Color>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    brush = Brush.linearGradient(gradient),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun FeaturedJobsCarousel(
    jobs: List<Job>,
    favoriteJobIds: Set<String>,
    onJobClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ফিচার্ড চাকরি",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFEF3C7)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFF59E0B)
                    )
                    Text(
                        text = "বিশেষ",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59E0B)
                    )
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(jobs, key = { it.id }) { job ->
                FeaturedJobCard(
                    job = job,
                    isFavorite = favoriteJobIds.contains(job.id),
                    onClick = { onJobClick(job.id) },
                    onFavoriteClick = { onFavoriteClick(job.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedJobCard(
    job: Job,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(200.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image with Gradient Overlay
            AsyncImage(
                model = job.imageUrl ?: "https://via.placeholder.com/300x200/6366F1/FFFFFF?text=${job.title.take(1)}",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Row - Featured Badge & Favorite
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFEF3C7).copy(alpha = 0.95f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = Color(0xFFF59E0B)
                            )
                            Text(
                                text = "Featured",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF59E0B)
                            )
                        }
                    }

                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.9f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color(0xFFEF4444) else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Bottom Content
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Business,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                            Text(
                                text = job.company,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF10B981).copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = formatSalary(job.salary),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModernCategoryFilter(
    selectedCategory: JobCategory,
    onCategorySelected: (JobCategory) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "ক্যাটাগরি",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(JobCategory.values()) { category ->
                CategoryChip(
                    text = category.banglaName,
                    icon = getCategoryIcon(category),
                    isSelected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Surface(
        onClick = onClick,
        modifier = Modifier.scale(animatedScale),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) {
            Color(0xFF6366F1)
        } else {
            MaterialTheme.colorScheme.surface
        },
        tonalElevation = if (isSelected) 0.dp else 2.dp,
        shadowElevation = if (isSelected) 4.dp else 0.dp,
        border = if (!isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        } else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun getCategoryIcon(category: JobCategory): ImageVector {
    return when (category) {
        JobCategory.ALL -> Icons.Outlined.GridView
        JobCategory.IT -> Icons.Outlined.Computer
        JobCategory.ENGINEERING -> Icons.Outlined.Engineering
        JobCategory.HEALTHCARE -> Icons.Outlined.MedicalServices
        JobCategory.EDUCATION -> Icons.Outlined.School
        JobCategory.BUSINESS -> Icons.Outlined.Business
        JobCategory.MARKETING -> Icons.Outlined.Campaign
        JobCategory.DESIGN -> Icons.Outlined.Palette
        JobCategory.FINANCE -> Icons.Outlined.AccountBalance
        JobCategory.SALES -> Icons.Outlined.TrendingUp
        JobCategory.HR -> Icons.Outlined.People
        JobCategory.OPERATIONS -> Icons.Outlined.Settings
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernJobCard(
    job: Job,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            // Company Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                AsyncImage(
                    model = job.imageUrl ?: "https://via.placeholder.com/400x120/6366F1/FFFFFF?text=${job.company.firstOrNull() ?: 'C'}",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.5f)
                                )
                            )
                        )
                )

                // Favorite button
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.95f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color(0xFFEF4444) else Color.Gray,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Work Location Badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.95f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF6366F1)
                        )
                        Text(
                            text = job.workLocation.banglaName,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6366F1)
                        )
                    }
                }
            }

            // Job Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Job Title
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Company Name
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Business,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF6366F1)
                    )
                    Text(
                        text = job.company,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Location
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF10B981)
                    )
                    Text(
                        text = job.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Info Chips
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InfoChip(
                        icon = Icons.Outlined.WorkOutline,
                        text = job.jobType.banglaName,
                        backgroundColor = Color(0xFFDEDEFE),
                        contentColor = Color(0xFF6366F1)
                    )

                    InfoChip(
                        icon = Icons.Outlined.Timer,
                        text = job.experience.banglaName,
                        backgroundColor = Color(0xFFD1FAE5),
                        contentColor = Color(0xFF059669)
                    )
                }

                // Salary Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF6366F1).copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Payments,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF6366F1)
                        )
                        Text(
                            text = formatSalary(job.salary),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6366F1)
                        )
                    }
                }

                // Bottom Info Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.People,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${job.applicationCount} জন আবেদন করেছেন",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = formatDeadline(job.deadline),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = if (isDeadlineSoon(job.deadline)) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun InfoChip(
    icon: ImageVector,
    text: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = contentColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

// Helper functions
fun formatSalary(salary: SalaryRange): String {
    val formatter = NumberFormat.getNumberInstance(Locale("bn", "BD"))
    val min = formatter.format(salary.min)
    val max = formatter.format(salary.max)
    return if (salary.isNegotiable) {
        "৳$min - ৳$max (আলোচনা সাপেক্ষ)"
    } else {
        "৳$min - ৳$max"
    }
}

fun formatDeadline(deadline: Long): String {
    val daysUntil = ((deadline - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
    return when {
        daysUntil < 0 -> "মেয়াদ শেষ"
        daysUntil == 0 -> "আজ শেষ"
        daysUntil == 1 -> "১ দিন বাকি"
        daysUntil < 7 -> "$daysUntil দিন বাকি"
        else -> "${daysUntil / 7} সপ্তাহ বাকি"
    }
}

fun isDeadlineSoon(deadline: Long): Boolean {
    val daysUntil = ((deadline - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
    return daysUntil in 0..3
}

@Composable
fun ModernLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(56.dp),
                strokeWidth = 5.dp,
                color = Color(0xFF6366F1)
            )
            Text(
                text = "চাকরি লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ModernErrorState(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )

            Text(
                text = "কিছু সমস্যা হয়েছে",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "আবার চেষ্টা করুন",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ModernEmptyState(onPostJob: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.WorkOutline,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF6366F1).copy(alpha = 0.6f)
            )

            Text(
                text = "কোনো চাকরি পাওয়া যায়নি",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "এখনো কোনো চাকরি পোস্ট করা হয়নি।\nপ্রথম চাকরি পোস্ট করুন!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onPostJob,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "চাকরি পোস্ট করুন",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ModernFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(64.dp),
        containerColor = Color.Transparent,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF6366F1),
                            Color(0xFF8B5CF6)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "চাকরি পোস্ট করুন",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun PaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onPageChange(currentPage - 1) },
            enabled = currentPage > 1
        ) {
            Icon(
                imageVector = Icons.Outlined.ChevronLeft,
                contentDescription = "Previous",
                tint = if (currentPage > 1) Color(0xFF6366F1) else Color.Gray
            )
        }

        Text(
            text = "পৃষ্ঠা $currentPage / $totalPages",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(
            onClick = { onPageChange(currentPage + 1) },
            enabled = currentPage < totalPages
        ) {
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Next",
                tint = if (currentPage < totalPages) Color(0xFF6366F1) else Color.Gray
            )
        }
    }
}

@Composable
fun ApplicationsContent(
    applications: List<JobApplication>,
    onJobClick: (String) -> Unit
) {
    if (applications.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Assignment,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color(0xFF6366F1).copy(alpha = 0.5f)
                )
                Text(
                    text = "কোনো আবেদন নেই",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "আপনি এখনো কোনো চাকরিতে আবেদন করেননি",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(applications, key = { it.id }) { application ->
                ApplicationCard(
                    application = application,
                    onClick = { onJobClick(application.job.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationCard(
    application: JobApplication,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = application.job.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = application.job.company,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                ApplicationStatusBadge(status = application.applicationStatus)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "আবেদন করা হয়েছে: ${formatTimestamp(application.appliedDate)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ApplicationStatusBadge(status: ApplicationStatus) {
    val (backgroundColor, textColor) = when (status) {
        ApplicationStatus.APPLIED -> Color(0xFFDEDEFE) to Color(0xFF6366F1)
        ApplicationStatus.REVIEWED -> Color(0xFFFFEDD5) to Color(0xFFF97316)
        ApplicationStatus.SHORTLISTED -> Color(0xFFD1FAE5) to Color(0xFF059669)
        ApplicationStatus.INTERVIEW_SCHEDULED -> Color(0xFFDBEAFE) to Color(0xFF2563EB)
        ApplicationStatus.REJECTED -> Color(0xFFFEE2E2) to Color(0xFFEF4444)
        ApplicationStatus.ACCEPTED -> Color(0xFFD1FAE5) to Color(0xFF059669)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Text(
            text = status.banglaName,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun FavoritesContent(
    jobs: List<Job>,
    favoriteJobIds: Set<String>,
    onJobClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    if (jobs.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color(0xFFEF4444).copy(alpha = 0.5f)
                )
                Text(
                    text = "কোনো সেভ করা চাকরি নেই",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "আপনার পছন্দের চাকরি সেভ করুন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(jobs, key = { it.id }) { job ->
                ModernJobCard(
                    job = job,
                    isFavorite = favoriteJobIds.contains(job.id),
                    onClick = { onJobClick(job.id) },
                    onFavoriteClick = { onFavoriteClick(job.id) }
                )
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val daysAgo = ((System.currentTimeMillis() - timestamp) / (1000 * 60 * 60 * 24)).toInt()
    return when {
        daysAgo == 0 -> "আজ"
        daysAgo == 1 -> "গতকাল"
        daysAgo < 7 -> "$daysAgo দিন আগে"
        daysAgo < 30 -> "${daysAgo / 7} সপ্তাহ আগে"
        else -> "${daysAgo / 30} মাস আগে"
    }
}
