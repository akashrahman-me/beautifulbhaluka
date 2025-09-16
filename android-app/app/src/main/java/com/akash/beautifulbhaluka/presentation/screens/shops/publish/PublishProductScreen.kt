package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition

// Main Screen Composable - Following Architecture Pattern
@Composable
fun PublishProductScreen(
    viewModel: PublishProductViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onProductPublished: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle success navigation with animation
    LaunchedEffect(uiState.isPublishSuccessful) {
        if (uiState.isPublishSuccessful) {
            onProductPublished()
            viewModel.onAction(PublishProductAction.DismissSuccessMessage)
        }
    }

    // Real Photo Picker for multiple images
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.onAction(PublishProductAction.AddImages(uris))
        }
    }

    // Stateless Content Composable
    PublishProductContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack,
        onOpenImagePicker = {
            photoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    )
}

// Stateless Content Composable - Following Architecture Pattern
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublishProductContent(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit,
    onNavigateBack: () -> Unit,
    onOpenImagePicker: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // Progress calculation for modern progress indicator
    val progress by remember(uiState) {
        derivedStateOf {
            calculateFormProgress(uiState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Modern Professional Top Bar
        ModernTopBar(
            progress = progress,
            onNavigateBack = onNavigateBack,
            onClearForm = { onAction(PublishProductAction.ClearForm) },
            isPublishing = uiState.isPublishing
        )

        when {
            uiState.isLoading -> {
                ModernLoadingState()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Hero Section with Progress
                    item {
                        HeroSection(progress = progress)
                    }

                    // Image Upload Section with Real Photo Picker
                    item {
                        ModernImageUploadSection(
                            selectedImages = uiState.selectedImages,
                            validationError = uiState.validationErrors["images"],
                            onAddImage = onOpenImagePicker,
                            onRemoveImage = { imageUri ->
                                onAction(PublishProductAction.RemoveImage(imageUri))
                            }
                        )
                    }

                    // Product Information Section
                    item {
                        ModernProductInfoSection(
                            uiState = uiState,
                            onAction = onAction
                        )
                    }

                    // Category & Condition Section
                    item {
                        ModernCategoryConditionSection(
                            uiState = uiState,
                            onAction = onAction
                        )
                    }

                    item {
                        ModernPricingSection(
                            uiState = uiState,
                            onAction = onAction
                        )
                    }

                    // Seller Information Section
                    item {
                        ModernSellerInfoSection(
                            uiState = uiState,
                            onAction = onAction
                        )
                    }

                    // Publish Action Section
                    item {
                        ModernPublishSection(
                            isPublishing = uiState.isPublishing,
                            hasValidationErrors = uiState.validationErrors.isNotEmpty(),
                            onPublish = {
                                keyboardController?.hide()
                                onAction(PublishProductAction.PublishProduct)
                            }
                        )
                    }

                    // Bottom padding
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernTopBar(
    progress: Float,
    onNavigateBack: () -> Unit,
    onClearForm: () -> Unit,
    isPublishing: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "পণ্য প্রকাশ করুন",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "আপনার পণ্যের সম্পূর্ণ তথ্য দিন",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    OutlinedButton(
                        onClick = onClearForm,
                        enabled = !isPublishing,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("রিসেট")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )

            // Modern Progress Indicator
            AnimatedLinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AnimatedLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500, easing = EaseOutCubic),
        label = "progress"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier.height(3.dp),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
private fun HeroSection(progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Sell,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "প্রগ্রেস: ${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = if (progress < 1.0f) "আরও কিছু তথ্য প্রয়োজন" else "সব তথ্য সম্পূর্ণ!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }

            if (progress >= 1.0f) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "Complete",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun ModernLoadingState() {
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
                text = "প্রস্তুতি নেওয়া হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ModernImageUploadSection(
    selectedImages: Set<Uri>,
    validationError: String?,
    onAddImage: () -> Unit,
    onRemoveImage: (Uri) -> Unit
) {
    ModernSectionCard(
        title = "পণ্যের ছবি",
        subtitle = "সর্বোচ্চ ৫টি ছবি যুক্ত করতে পারবেন",
        icon = Icons.Outlined.PhotoCamera,
        validationError = validationError
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            // Add image button
            if (selectedImages.size < 5) {
                item {
                    ModernAddImageButton(onClick = onAddImage)
                }
            }

            // Display selected images
            items(selectedImages.toList()) { imageUri ->
                ModernSelectedImageCard(
                    imageUri = imageUri,
                    onRemove = { onRemoveImage(imageUri) }
                )
            }
        }
    }
}

@Composable
private fun ModernAddImageButton(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.AddPhotoAlternate,
                contentDescription = "Add Image",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ছবি যুক্ত করুন",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun ModernSelectedImageCard(
    imageUri: Uri,
    onRemove: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.size(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Real image display using AsyncImage
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.government_seal_of_bangladesh),
                placeholder = painterResource(R.drawable.government_seal_of_bangladesh)
            )

            // Gradient overlay for better button visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f),
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f)
                            )
                        )
                    )
            )

            // Modern remove button
            FilledIconButton(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(28.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Remove",
                    modifier = Modifier.size(16.dp)
                )
            }

            // Image count badge
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Image,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "✓",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun ModernProductInfoSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    ModernSectionCard(
        title = "পণ্যের তথ্য",
        subtitle = "নাম ও বিস্তারিত বিবরণ দিন",
        icon = Icons.Outlined.Info
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ModernTextField(
                value = uiState.productName,
                onValueChange = { onAction(PublishProductAction.UpdateProductName(it)) },
                label = "পণ্যের নাম *",
                placeholder = "যেমন: iPhone 15 Pro Max",
                leadingIcon = Icons.AutoMirrored.Outlined.Label,
                isError = uiState.validationErrors.containsKey("productName"),
                errorMessage = uiState.validationErrors["productName"],
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )

            ModernTextField(
                value = uiState.productDescription,
                onValueChange = { onAction(PublishProductAction.UpdateProductDescription(it)) },
                label = "পণ্যের বিবরণ *",
                placeholder = "পণ্যের বৈশিষ্ট্য, অবস্থা ও অন্যান্য তথ্য লিখুন...",
                leadingIcon = Icons.Outlined.Description,
                isError = uiState.validationErrors.containsKey("productDescription"),
                errorMessage = uiState.validationErrors["productDescription"],
                minLines = 4,
                maxLines = 6,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                )
            )
        }
    }
}

@Composable
private fun ModernCategoryConditionSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    ModernSectionCard(
        title = "ক্যাটেগরি ও অবস্থা",
        subtitle = "পণ্যের ধরন ও অবস্থা নির্বাচন করুন",
        icon = Icons.Outlined.Category,
        validationError = uiState.validationErrors["category"]
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            // Category Selection
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "ক্যাটেগরি নির্বাচন করুন *",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(uiState.categories) { category ->
                        ModernFilterChip(
                            selected = uiState.selectedCategory?.id == category.id,
                            onClick = { onAction(PublishProductAction.SelectCategory(category)) },
                            label = category.name,
                            icon = Icons.Outlined.Category
                        )
                    }
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Condition Selection
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "পণ্যের অবস্থা",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(ProductCondition.entries.toTypedArray()) { condition ->
                        ModernFilterChip(
                            selected = uiState.condition == condition,
                            onClick = { onAction(PublishProductAction.UpdateCondition(condition)) },
                            label = condition.displayName,
                            icon = when (condition) {
                                ProductCondition.NEW -> Icons.Outlined.NewReleases
                                ProductCondition.USED -> Icons.Outlined.History
                                ProductCondition.REFURBISHED -> Icons.Outlined.Build
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernPricingSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    ModernSectionCard(
        title = "মূল্য নির্ধারণ",
        subtitle = "পণ্যের দাম ও স্টক পরিমাণ",
        icon = Icons.Outlined.MonetizationOn
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ModernTextField(
                    value = uiState.price,
                    onValueChange = { onAction(PublishProductAction.UpdatePrice(it)) },
                    label = "বিক্রয় মূল্য (৳) *",
                    placeholder = "0",
                    leadingIcon = Icons.Outlined.Sell,
                    modifier = Modifier.weight(1f),
                    isError = uiState.validationErrors.containsKey("price"),
                    errorMessage = uiState.validationErrors["price"],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )

                ModernTextField(
                    value = uiState.originalPrice,
                    onValueChange = { onAction(PublishProductAction.UpdateOriginalPrice(it)) },
                    label = "আসল দাম (৳)",
                    placeholder = "0",
                    leadingIcon = Icons.Outlined.PriceCheck,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }

            ModernTextField(
                value = uiState.stock,
                onValueChange = { onAction(PublishProductAction.UpdateStock(it)) },
                label = "স্টক পরিমাণ *",
                placeholder = "1",
                leadingIcon = Icons.Outlined.Inventory,
                isError = uiState.validationErrors.containsKey("stock"),
                errorMessage = uiState.validationErrors["stock"],
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            // Price calculation card
            if (uiState.price.isNotBlank() && uiState.originalPrice.isNotBlank()) {
                val price = uiState.price.toDoubleOrNull()
                val originalPrice = uiState.originalPrice.toDoubleOrNull()

                if (price != null && originalPrice != null && originalPrice > price) {
                    val discount = ((originalPrice - price) / originalPrice * 100).toInt()

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.LocalOffer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "$discount% ছাড় পাবেন ক্রেতারা",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernSellerInfoSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    ModernSectionCard(
        title = "বিক্রেতার তথ্য",
        subtitle = "যোগাযোগের জন্য প্রয়োজনীয় তথ্য",
        icon = Icons.Outlined.Person
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            ModernTextField(
                value = uiState.sellerName,
                onValueChange = { onAction(PublishProductAction.UpdateSellerName(it)) },
                label = "বিক্রেতার নাম *",
                placeholder = "আপনার নাম",
                leadingIcon = Icons.Outlined.Badge,
                isError = uiState.validationErrors.containsKey("sellerName"),
                errorMessage = uiState.validationErrors["sellerName"],
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                )
            )

            ModernTextField(
                value = uiState.sellerContact,
                onValueChange = { onAction(PublishProductAction.UpdateSellerContact(it)) },
                label = "মোবাইল নম্বর *",
                placeholder = "+880 1XXX XXXXXX",
                leadingIcon = Icons.Outlined.Phone,
                isError = uiState.validationErrors.containsKey("sellerContact"),
                errorMessage = uiState.validationErrors["sellerContact"],
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            ModernTextField(
                value = uiState.location,
                onValueChange = { onAction(PublishProductAction.UpdateLocation(it)) },
                label = "ঠিকানা *",
                placeholder = "এলাকা, জেলা",
                leadingIcon = Icons.Outlined.LocationOn,
                isError = uiState.validationErrors.containsKey("location"),
                errorMessage = uiState.validationErrors["location"],
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}

@Composable
private fun ModernPublishSection(
    isPublishing: Boolean,
    hasValidationErrors: Boolean,
    onPublish: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Terms and conditions
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "পণ্য প্রকাশ করে আপনি আমাদের নীতিমালা মেনে নিচ্ছেন",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Publish button
        Button(
            onClick = onPublish,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isPublishing && !hasValidationErrors,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (hasValidationErrors)
                    MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = if (hasValidationErrors) 0.dp else 4.dp
            )
        ) {
            AnimatedContent(
                targetState = isPublishing,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                },
                label = "button_content"
            ) { publishing ->
                if (publishing) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "প্রকাশ হচ্ছে...",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            if (hasValidationErrors) Icons.Outlined.ErrorOutline else Icons.Outlined.Publish,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (hasValidationErrors) "তথ্য সম্পূর্ণ করুন" else "পণ্য প্রকাশ করুন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// Modern UI Components
@Composable
private fun ModernSectionCard(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    validationError: String? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = if (validationError != null) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
        } else null
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Validation error
            if (validationError != null) {
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = validationError,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Content
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    minLines: Int = 1,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = leadingIcon?.let { icon ->
                {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            trailingIcon = trailingIcon?.let { icon ->
                {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = if (isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: ImageVector
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
            )
        },
        leadingIcon = {
            Icon(
                if (selected) Icons.Filled.Check else icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            selectedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            borderWidth = 1.dp,
            selectedBorderWidth = 1.5.dp
        )
    )
}

// Helper function to calculate form progress
private fun calculateFormProgress(uiState: PublishProductUiState): Float {
    val fields = listOf(
        uiState.productName.isNotBlank(),
        uiState.productDescription.isNotBlank(),
        uiState.selectedCategory != null,
        uiState.price.isNotBlank(),
        uiState.stock.isNotBlank(),
        uiState.sellerName.isNotBlank(),
        uiState.sellerContact.isNotBlank(),
        uiState.location.isNotBlank(),
        uiState.selectedImages.isNotEmpty()
    )

    val completedFields = fields.count { it }
    return completedFields.toFloat() / fields.size
}
