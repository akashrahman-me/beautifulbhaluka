package com.akash.beautifulbhaluka.presentation.screens.buysell.details

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.presentation.screens.buysell.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuySellDetailsScreen(
    itemId: String,
    viewModel: BuySellDetailsViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load item on screen initialization
    LaunchedEffect(itemId) {
        viewModel.onAction(BuySellDetailsAction.LoadItem(itemId))
    }

    Scaffold(
        topBar = {
            BuySellDetailsTopBar(
                onNavigateBack = onNavigateBack,
                onShare = { viewModel.onAction(BuySellDetailsAction.ShareItem) },
                onReport = { viewModel.onAction(BuySellDetailsAction.ShowReportDialog) },
                onToggleFavorite = { viewModel.onAction(BuySellDetailsAction.ToggleFavorite) },
                isFavorite = uiState.isFavorite,
                hasItem = uiState.item != null
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> LoadingState()
                uiState.error != null -> ErrorState(
                    error = uiState.error!!,
                    onRetry = { viewModel.onAction(BuySellDetailsAction.LoadItem(itemId)) },
                    onNavigateBack = onNavigateBack
                )
                uiState.item != null -> BuySellDetailsContent(
                    item = uiState.item!!,
                    uiState = uiState,
                    onAction = viewModel::onAction
                )
            }
        }
    }

    // Report Dialog
    if (uiState.showReportDialog) {
        ReportDialog(
            onDismiss = { viewModel.onAction(BuySellDetailsAction.HideReportDialog) },
            onReport = { viewModel.onAction(BuySellDetailsAction.ReportItem) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuySellDetailsTopBar(
    onNavigateBack: () -> Unit,
    onShare: () -> Unit,
    onReport: () -> Unit,
    onToggleFavorite: () -> Unit,
    isFavorite: Boolean,
    hasItem: Boolean
) {
    TopAppBar(
        title = { Text("বিস্তারিত") },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ফিরে যান"
                )
            }
        },
        actions = {
            if (hasItem) {
                IconButton(onClick = onShare) {
                    Icon(Icons.Outlined.Share, contentDescription = "শেয়ার")
                }
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "পছন্দ",
                        tint = if (isFavorite) Color(0xFFEF4444) else LocalContentColor.current
                    )
                }
                IconButton(onClick = onReport) {
                    Icon(Icons.Outlined.Flag, contentDescription = "রিপোর্ট")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 4.dp
            )
            Text(
                text = "লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ErrorState(
    error: String,
    onRetry: () -> Unit,
    onNavigateBack: () -> Unit
) {
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
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = error,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onNavigateBack) {
                    Text("ফিরে যান")
                }
                Button(onClick = onRetry) {
                    Text("আবার চেষ্টা করুন")
                }
            }
        }
    }
}

@Composable
fun BuySellDetailsContent(
    item: BuySellItem,
    uiState: BuySellDetailsUiState,
    onAction: (BuySellDetailsAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Image Gallery
            item {
                ImageGallerySection(
                    images = if (item.imageUrls.isNotEmpty()) item.imageUrls else listOf(item.imageUrl),
                    selectedIndex = uiState.selectedImageIndex,
                    onImageSelected = { onAction(BuySellDetailsAction.SelectImage(it)) },
                    onImageClick = { onAction(BuySellDetailsAction.ShowImageViewer) }
                )
            }

            // Badges & Status
            item {
                BadgesSection(item = item)
            }

            // Title & Price
            item {
                TitlePriceSection(item = item)
            }

            // Quick Info
            item {
                QuickInfoSection(item = item)
            }

            // Description
            item {
                DescriptionSection(description = item.description)
            }

            // Seller Info
            item {
                SellerInfoSection(
                    item = item,
                    isContactingSeller = uiState.isContactingSeller,
                    onAction = onAction
                )
            }

            // Location & Posted Time
            item {
                LocationTimeSection(item = item)
            }

            // Related Items
            if (uiState.relatedItems.isNotEmpty()) {
                item {
                    RelatedItemsSection(
                        items = uiState.relatedItems,
                        onItemClick = { /* Navigate to details */ }
                    )
                }
            }

            // Safety Tips
            item {
                SafetyTipsSection()
            }
        }

        // Bottom Action Bar
        BottomActionBar(
            item = item,
            isContactingSeller = uiState.isContactingSeller,
            onAction = onAction
        )
    }
}

@Composable
fun ImageGallerySection(
    images: List<String>,
    selectedIndex: Int,
    onImageSelected: (Int) -> Unit,
    onImageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Main Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clickable(onClick = onImageClick)
        ) {
            AsyncImage(
                model = images.getOrNull(selectedIndex) ?: images.first(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Image Counter
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.Black.copy(alpha = 0.7f)
            ) {
                Text(
                    text = "${selectedIndex + 1} / ${images.size}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Thumbnail Strip
        if (images.size > 1) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(images) { index, imageUrl ->
                    ThumbnailImage(
                        imageUrl = imageUrl,
                        isSelected = index == selectedIndex,
                        onClick = { onImageSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun ThumbnailImage(
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else Color.Transparent
            )
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isSelected) 4.dp else 0.dp)
                .clip(RoundedCornerShape(if (isSelected) 6.dp else 8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BadgesSection(item: BuySellItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Type Badge
        ModernBadge(
            text = item.type.displayName,
            icon = when (item.type) {
                ItemType.SELL -> Icons.Outlined.Sell
                ItemType.BUY -> Icons.Outlined.ShoppingCart
                ItemType.EXCHANGE -> Icons.Outlined.SwapHoriz
            },
            backgroundColor = when (item.type) {
                ItemType.SELL -> Color(0xFF10B981)
                ItemType.BUY -> Color(0xFF3B82F6)
                ItemType.EXCHANGE -> Color(0xFFF59E0B)
            }
        )

        // Condition Badge
        ModernBadge(
            text = item.condition.displayName,
            icon = Icons.Outlined.CheckCircle,
            backgroundColor = Color(0xFF8B5CF6)
        )

        if (item.isUrgent) {
            ModernBadge(
                text = "জরুরী",
                icon = Icons.Outlined.Bolt,
                backgroundColor = Color(0xFFEF4444)
            )
        }

        if (item.isFeatured) {
            ModernBadge(
                text = "ফিচারড",
                icon = Icons.Outlined.Star,
                backgroundColor = Color(0xFFFFD700),
                textColor = Color(0xFF1F2937)
            )
        }
    }
}

@Composable
fun ModernBadge(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    textColor: Color = Color.White
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = textColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun TitlePriceSection(item: BuySellItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        )
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 22.sp
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
            )

            // Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "মূল্য",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "${item.currency} ${formatPrice(item.price)}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 32.sp
                        )
                        if (item.originalPrice != null && item.originalPrice > item.price) {
                            Text(
                                text = "${item.currency} ${formatPrice(item.originalPrice)}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                if (item.originalPrice != null && item.originalPrice > item.price) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF10B981)
                    ) {
                        Text(
                            text = "${((item.originalPrice - item.price) / item.originalPrice * 100).toInt()}% ছাড়",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleMedium,
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
fun QuickInfoSection(item: BuySellItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickInfoItem(
                icon = Icons.Outlined.Category,
                label = "ক্যাটাগরি",
                value = item.category.name,
                iconColor = Color(item.category.color)
            )
            QuickInfoItem(
                icon = Icons.Outlined.Visibility,
                label = "দেখা হয়েছে",
                value = "${item.viewCount} বার"
            )
            if (item.isVerified) {
                QuickInfoItem(
                    icon = Icons.Filled.Verified,
                    label = "স্ট্যাটাস",
                    value = "যাচাইকৃত বিক্রেতা",
                    iconColor = Color(0xFF10B981)
                )
            }
        }
    }
}

@Composable
fun QuickInfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = iconColor.copy(alpha = 0.1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp),
                tint = iconColor
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun DescriptionSection(description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "বিস্তারিত বিবরণ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SellerInfoSection(
    item: BuySellItem,
    isContactingSeller: Boolean,
    onAction: (BuySellDetailsAction) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "বিক্রেতার তথ্য",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.sellerName.first().toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = item.sellerName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        if (item.isVerified) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = "যাচাইকৃত",
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF10B981)
                            )
                        }
                    }
                    if (item.rating > 0f) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFFFFA500)
                            )
                            Text(
                                text = String.format(Locale.US, "%.1f", item.rating),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "রেটিং",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Contact Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onAction(BuySellDetailsAction.CallSeller) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("কল করুন")
                }

                Button(
                    onClick = { onAction(BuySellDetailsAction.WhatsAppSeller) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubble,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("WhatsApp")
                }
            }
        }
    }
}

@Composable
fun LocationTimeSection(item: BuySellItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "অবস্থান",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "পোস্ট করা হয়েছে",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = getTimeAgo(item.createdAt),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun RelatedItemsSection(
    items: List<BuySellItem>,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "সম্পর্কিত পণ্য",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                RelatedItemCard(
                    item = item,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}

@Composable
fun RelatedItemCard(
    item: BuySellItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${item.currency} ${formatPrice(item.price)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SafetyTipsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3CD)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = Color(0xFFFF9800)
                )
                Text(
                    text = "সতর্কতা",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF856404)
                )
            }

            val tips = listOf(
                "সরাসরি দেখে পণ্য কিনুন",
                "আগাম টাকা পাঠাবেন না",
                "পাবলিক প্লেসে দেখা করুন",
                "ব্যাংক ট্রান্সফার নিরাপদ"
            )

            tips.forEach { tip ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF856404)
                    )
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF856404)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomActionBar(
    item: BuySellItem,
    isContactingSeller: Boolean,
    onAction: (BuySellDetailsAction) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onAction(BuySellDetailsAction.CallSeller) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("কল করুন")
            }

            Button(
                onClick = { onAction(BuySellDetailsAction.ContactSeller) },
                modifier = Modifier.weight(1f),
                enabled = !isContactingSeller
            ) {
                if (isContactingSeller) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Message,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("মেসেজ করুন")
                }
            }
        }
    }
}

@Composable
fun ReportDialog(
    onDismiss: () -> Unit,
    onReport: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Flag,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = "রিপোর্ট করুন",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "এই বিজ্ঞাপনটি কি সন্দেহজনক বা নিয়মবিরুদ্ধ মনে হচ্ছে? আপনি এটি রিপোর্ট করতে পারেন।",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = onReport,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("রিপোর্ট করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}

private fun formatPrice(price: Double): String {
    return NumberFormat.getNumberInstance(Locale("bn", "BD")).format(price.toInt())
}

private fun getTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "এইমাত্র"
        diff < 3600000 -> "${(diff / 60000).toInt()} মিনিট আগে"
        diff < 86400000 -> "${(diff / 3600000).toInt()} ঘন্টা আগে"
        diff < 604800000 -> "${(diff / 86400000).toInt()} দিন আগে"
        else -> SimpleDateFormat("dd MMM yyyy", Locale("bn", "BD")).format(Date(timestamp))
    }
}
