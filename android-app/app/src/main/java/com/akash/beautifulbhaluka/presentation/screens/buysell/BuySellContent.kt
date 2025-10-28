package com.akash.beautifulbhaluka.presentation.screens.buysell

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.presentation.components.common.ScrollAnimatedHeader
import com.akash.beautifulbhaluka.presentation.components.common.rememberScrollHeaderState
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuySellContent(
    uiState: BuySellUiState,
    onAction: (BuySellAction) -> Unit,
    onNavigateToDetails: ((String) -> Unit)?,
    onNavigateToPublish: (() -> Unit)?
) {
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    // Use reusable scroll header state hook
    val showHeader = rememberScrollHeaderState(scrollState = listState)

    // Modern gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Modern Top Bar with reusable scroll animation
            ScrollAnimatedHeader(visible = showHeader) {
                BuySellTopBar(
                    onAction = onAction,
                    uiState = uiState,
                    onSortClick = { showSortSheet = true },
                    onFilterClick = { showFilterSheet = true }
                )
            }

            // Animated Category Pills
            CategoryPillsRow(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { onAction(BuySellAction.SelectCategory(it)) }
            )

            // Filter Chips Row
            FilterChipsRow(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { onAction(BuySellAction.SelectFilter(it)) }
            )

            // Items List with modern cards
            if (uiState.isLoading) {
                LoadingShimmer()
            } else if (uiState.filteredItems.isEmpty()) {
                EmptyStateView()
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Stats Card at top
                    item {
                        StatsCard(itemCount = uiState.filteredItems.size)
                    }

                    items(
                        items = uiState.filteredItems,
                        key = { it.id }
                    ) { item ->
                        ModernBuySellCard(
                            item = item,
                            onClick = { onNavigateToDetails?.invoke(item.id) }
                        )
                    }

                    // Bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }

        // Floating Action Button with gradient
        FloatingActionButton(
            onClick = { onNavigateToPublish?.invoke() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
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
                    contentDescription = "বিজ্ঞাপন দিন",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }

    // Sort Bottom Sheet
    if (showSortSheet) {
        SortBottomSheet(
            currentSort = uiState.sortOption,
            onSortSelected = {
                onAction(BuySellAction.SelectSortOption(it))
                showSortSheet = false
            },
            onDismiss = { showSortSheet = false }
        )
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        FilterBottomSheet(
            currentFilter = uiState.selectedFilter,
            onFilterSelected = {
                onAction(BuySellAction.SelectFilter(it))
                showFilterSheet = false
            },
            onDismiss = { showFilterSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuySellTopBar(
    onAction: (BuySellAction) -> Unit,
    uiState: BuySellUiState,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6366F1),
                        Color(0xFF8B5CF6)
                    )
                )
            )
            .padding(16.dp)
    ) {
        // Title Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ক্রয়-বিক্রয়",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Buy & Sell Marketplace",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color.White.copy(alpha = 0.2f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FilterList,
                        contentDescription = "ফিল্টার",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = onSortClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color.White.copy(alpha = 0.2f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Sort,
                        contentDescription = "সাজান",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Modern Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { onAction(BuySellAction.UpdateSearchQuery(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(28.dp)),
            placeholder = {
                Text(
                    "খুঁজুন...",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { onAction(BuySellAction.UpdateSearchQuery("")) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "মুছুন"
                        )
                    }
                }
            },
            shape = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}

@Composable
fun CategoryPillsRow(
    categories: List<BuySellCategory>,
    selectedCategory: BuySellCategory?,
    onCategorySelected: (BuySellCategory?) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // All category pill
        item {
            CategoryPill(
                name = "সব",
                nameEn = "All",
                count = null,
                isSelected = selectedCategory == null,
                color = Color(0xFF6366F1),
                onClick = { onCategorySelected(null) }
            )
        }

        items(categories) { category ->
            CategoryPill(
                name = category.name,
                nameEn = category.nameEn,
                count = category.itemCount,
                isSelected = selectedCategory?.id == category.id,
                color = Color(category.color),
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryPill(
    name: String,
    nameEn: String,
    count: Int?,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Surface(
        modifier = Modifier
            .height(48.dp)
            .animateContentSize()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) color else MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
            if (count != null && count > 0) {
                Surface(
                    shape = CircleShape,
                    color = if (isSelected) Color.White.copy(alpha = 0.3f) else color.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = count.toString(),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else color,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChipsRow(
    selectedFilter: BuySellFilter,
    onFilterSelected: (BuySellFilter) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(BuySellFilter.entries) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = {
                    Text(
                        filter.displayName,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = when (filter) {
                            BuySellFilter.ALL -> Icons.Outlined.GridView
                            BuySellFilter.SELL -> Icons.Outlined.Sell
                            BuySellFilter.BUY -> Icons.Outlined.ShoppingCart
                            BuySellFilter.EXCHANGE -> Icons.Outlined.SwapHoriz
                            BuySellFilter.FEATURED -> Icons.Outlined.Star
                            BuySellFilter.URGENT -> Icons.Outlined.Bolt
                        },
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}

@Composable
fun ModernBuySellCard(
    item: BuySellItem,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isPressed = true
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Image Section with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                // Top Badges Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        // Type Badge
                        Badge(
                            text = item.type.displayName,
                            backgroundColor = when (item.type) {
                                ItemType.SELL -> Color(0xFF10B981)
                                ItemType.BUY -> Color(0xFF3B82F6)
                                ItemType.EXCHANGE -> Color(0xFFF59E0B)
                            },
                            icon = when (item.type) {
                                ItemType.SELL -> Icons.Outlined.Sell
                                ItemType.BUY -> Icons.Outlined.ShoppingCart
                                ItemType.EXCHANGE -> Icons.Outlined.SwapHoriz
                            }
                        )

                        if (item.isUrgent) {
                            Badge(
                                text = "জরুরী",
                                backgroundColor = Color(0xFFEF4444),
                                icon = Icons.Outlined.Bolt
                            )
                        }

                        if (item.isFeatured) {
                            Badge(
                                text = "ফিচারড",
                                backgroundColor = Color(0xFFFFD700),
                                textColor = Color(0xFF1F2937),
                                icon = Icons.Outlined.Star
                            )
                        }
                    }

                    if (item.isVerified) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF10B981)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = "যাচাইকৃত",
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(16.dp),
                                tint = Color.White
                            )
                        }
                    }
                }

                // View Count at bottom right
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Black.copy(alpha = 0.6f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                        Text(
                            text = item.viewCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Title
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp
                )

                // Price Row with gradient
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${item.currency} ${formatPrice(item.price)}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp
                            )
                            if (item.originalPrice != null && item.originalPrice > item.price) {
                                Text(
                                    text = "${item.currency} ${formatPrice(item.originalPrice)}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        if (item.originalPrice != null && item.originalPrice > item.price) {
                            val discount = ((item.originalPrice - item.price) / item.originalPrice * 100).toInt()
                            Text(
                                text = "$discount% ছাড়",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF10B981),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Condition Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = item.condition.displayName,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = 11.sp
                        )
                    }
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )

                // Seller Info & Location
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(20.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Column {
                            Text(
                                text = item.sellerName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                            if (item.rating > 0f) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = Color(0xFFFFA500)
                                    )
                                    Text(
                                        text = String.format("%.1f", item.rating),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = item.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Badge(
    text: String,
    backgroundColor: Color,
    textColor: Color = Color.White,
    icon: ImageVector? = null
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = textColor
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun StatsCard(itemCount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(24.dp),
                        tint = Color.White
                    )
                }
                Column {
                    Text(
                        text = "$itemCount টি পণ্য",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "উপলব্ধ বিজ্ঞাপন",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            Icon(
                imageVector = Icons.Outlined.TrendingUp,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    currentSort: SortOption,
    onSortSelected: (SortOption) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "সাজান",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            SortOption.entries.forEach { option ->
                ListItem(
                    headlineContent = { Text(option.displayName) },
                    leadingContent = {
                        RadioButton(
                            selected = currentSort == option,
                            onClick = { onSortSelected(option) }
                        )
                    },
                    modifier = Modifier.clickable { onSortSelected(option) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilter: BuySellFilter,
    onFilterSelected: (BuySellFilter) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "ফিল্টার করুন",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            BuySellFilter.entries.forEach { filter ->
                ListItem(
                    headlineContent = { Text(filter.displayName) },
                    leadingContent = {
                        RadioButton(
                            selected = currentFilter == filter,
                            onClick = { onFilterSelected(filter) }
                        )
                    },
                    modifier = Modifier.clickable { onFilterSelected(filter) }
                )
            }
        }
    }
}

@Composable
fun LoadingShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(3) {
            ShimmerCard()
        }
    }
}

@Composable
fun ShimmerCard() {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha))
        )
    }
}

@Composable
fun EmptyStateView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.SearchOff,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "কোন পণ্য পাওয়া যায়নি",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "অন্য ক্যাটাগরি বা ফিল্টার চেষ্টা করুন",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

private fun formatPrice(price: Double): String {
    return NumberFormat.getNumberInstance(Locale("bn", "BD")).format(price.toInt())
}
