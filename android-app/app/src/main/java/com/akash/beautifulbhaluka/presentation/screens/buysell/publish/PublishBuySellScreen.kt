package com.akash.beautifulbhaluka.presentation.screens.buysell.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.presentation.screens.buysell.BuySellCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishBuySellScreen(
    viewModel: PublishBuySellViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            viewModel.onAction(PublishBuySellAction.AddImages(uris))
        }
    }

    // Success dialog
    if (uiState.successMessage != null) {
        SuccessDialog(
            message = uiState.successMessage!!,
            onDismiss = {
                onNavigateBack()
            }
        )
    }

    Scaffold(
        topBar = {
            PublishTopBar(
                onNavigateBack = onNavigateBack,
                isPublishing = uiState.isPublishing
            )
        },
        bottomBar = {
            PublishBottomBar(
                isPublishing = uiState.isPublishing,
                onPublish = { viewModel.onAction(PublishBuySellAction.PublishItem) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with gradient
                item {
                    PublishHeader()
                }

                // Image Upload Section
                item {
                    ImageUploadSection(
                        selectedImages = uiState.selectedImages,
                        imageError = uiState.imageError,
                        onAddImages = { imagePickerLauncher.launch("image/*") },
                        onRemoveImage = { uri ->
                            viewModel.onAction(PublishBuySellAction.RemoveImage(uri))
                        }
                    )
                }

                // Type Selection
                item {
                    TypeSelectionSection(
                        selectedType = uiState.selectedType,
                        types = viewModel.types,
                        onTypeSelected = { type ->
                            viewModel.onAction(PublishBuySellAction.SelectType(type))
                        }
                    )
                }

                // Title Field
                item {
                    ModernTextField(
                        value = uiState.title,
                        onValueChange = { viewModel.onAction(PublishBuySellAction.UpdateTitle(it)) },
                        label = "শিরোনাম *",
                        placeholder = "পণ্যের শিরোনাম লিখুন",
                        icon = Icons.Outlined.Title,
                        error = uiState.titleError,
                        maxLines = 2
                    )
                }

                // Category Selection
                item {
                    CategorySelectionCard(
                        selectedCategoryId = uiState.selectedCategory,
                        categories = viewModel.categories,
                        categoryError = uiState.categoryError,
                        onClick = { viewModel.onAction(PublishBuySellAction.ShowCategoryPicker) }
                    )
                }

                // Condition Selection
                item {
                    ConditionSelectionCard(
                        selectedCondition = uiState.selectedCondition,
                        conditions = viewModel.conditions,
                        onClick = { viewModel.onAction(PublishBuySellAction.ShowConditionPicker) }
                    )
                }

                // Price Fields
                item {
                    PriceSection(
                        price = uiState.price,
                        originalPrice = uiState.originalPrice,
                        priceError = uiState.priceError,
                        isNegotiable = uiState.isNegotiable,
                        onPriceChange = { viewModel.onAction(PublishBuySellAction.UpdatePrice(it)) },
                        onOriginalPriceChange = {
                            viewModel.onAction(
                                PublishBuySellAction.UpdateOriginalPrice(
                                    it
                                )
                            )
                        },
                        onNegotiableToggle = {
                            viewModel.onAction(
                                PublishBuySellAction.ToggleNegotiable(
                                    it
                                )
                            )
                        }
                    )
                }

                // Description Field
                item {
                    ModernTextField(
                        value = uiState.description,
                        onValueChange = {
                            viewModel.onAction(
                                PublishBuySellAction.UpdateDescription(
                                    it
                                )
                            )
                        },
                        label = "বিবরণ *",
                        placeholder = "পণ্যের বিস্তারিত বিবরণ লিখুন...",
                        icon = Icons.Outlined.Description,
                        error = uiState.descriptionError,
                        minLines = 5,
                        maxLines = 10
                    )
                }

                // Location Field
                item {
                    ModernTextField(
                        value = uiState.location,
                        onValueChange = { viewModel.onAction(PublishBuySellAction.UpdateLocation(it)) },
                        label = "অবস্থান *",
                        placeholder = "এলাকা, থানা, জেলা",
                        icon = Icons.Outlined.LocationOn,
                        error = uiState.locationError
                    )
                }

                // Contact Field
                item {
                    ModernTextField(
                        value = uiState.contactNumber,
                        onValueChange = { viewModel.onAction(PublishBuySellAction.UpdateContact(it)) },
                        label = "যোগাযোগ নম্বর *",
                        placeholder = "+880 1XXX-XXXXXX",
                        icon = Icons.Outlined.Phone,
                        error = uiState.contactError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }

                // Additional Options
                item {
                    AdditionalOptionsSection(
                        isUrgent = uiState.isUrgent,
                        isFeatured = uiState.isFeatured,
                        onUrgentToggle = { viewModel.onAction(PublishBuySellAction.ToggleUrgent(it)) },
                        onFeaturedToggle = {
                            viewModel.onAction(
                                PublishBuySellAction.ToggleFeatured(
                                    it
                                )
                            )
                        }
                    )
                }

                // Tips Section
                item {
                    PublishingTipsSection()
                }
            }

            // Error Snackbar
            if (uiState.error != null) {
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.onAction(PublishBuySellAction.ClearError) }) {
                            Text("ঠিক আছে")
                        }
                    }
                ) {
                    Text(uiState.error!!)
                }
            }
        }
    }

    // Category Picker Dialog
    if (uiState.showCategoryPicker) {
        CategoryPickerDialog(
            categories = viewModel.categories,
            selectedCategoryId = uiState.selectedCategory,
            onCategorySelected = { categoryId ->
                viewModel.onAction(PublishBuySellAction.SelectCategory(categoryId))
            },
            onDismiss = { viewModel.onAction(PublishBuySellAction.HideCategoryPicker) }
        )
    }

    // Condition Picker Dialog
    if (uiState.showConditionPicker) {
        ConditionPickerDialog(
            conditions = viewModel.conditions,
            selectedCondition = uiState.selectedCondition,
            onConditionSelected = { condition ->
                viewModel.onAction(PublishBuySellAction.SelectCondition(condition))
            },
            onDismiss = { viewModel.onAction(PublishBuySellAction.HideConditionPicker) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishTopBar(
    onNavigateBack: () -> Unit,
    isPublishing: Boolean
) {
    TopAppBar(
        title = {
            Text(
                "বিজ্ঞাপন প্রকাশ করুন",
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                enabled = !isPublishing
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ফিরে যান"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun PublishHeader() {
    Box(
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
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
            Text(
                text = "আপনার পণ্য বিক্রয় করুন",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "সম্পূর্ণ তথ্য দিয়ে দ্রুত বিক্রয় করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ImageUploadSection(
    selectedImages: List<Uri>,
    imageError: String?,
    onAddImages: () -> Unit,
    onRemoveImage: (Uri) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
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
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "ছবি যোগ করুন *",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "(${selectedImages.size}/5)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (imageError != null) {
                Text(
                    text = imageError,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Add image button
                if (selectedImages.size < 5) {
                    item {
                        AddImageButton(onClick = onAddImages)
                    }
                }

                // Selected images
                items(selectedImages) { uri ->
                    SelectedImageCard(
                        uri = uri,
                        onRemove = { onRemoveImage(uri) }
                    )
                }
            }

            Text(
                text = "প্রথম ছবিটি প্রধান ছবি হিসেবে দেখানো হবে",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AddImageButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                )
            )
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "ছবি যোগ করুন",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SelectedImageCard(
    uri: Uri,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier.size(120.dp)
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(28.dp)
                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "মুছুন",
                modifier = Modifier.size(18.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun TypeSelectionSection(
    selectedType: String,
    types: List<Pair<String, String>>,
    onTypeSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "বিজ্ঞাপনের ধরন *",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                types.forEach { (code, name) ->
                    TypeChip(
                        text = name,
                        icon = when (code) {
                            "SELL" -> Icons.Outlined.Sell
                            "BUY" -> Icons.Outlined.ShoppingCart
                            else -> Icons.Outlined.SwapHoriz
                        },
                        isSelected = selectedType == code,
                        onClick = { onTypeSelected(code) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun TypeChip(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surfaceVariant,
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else null,
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    error: String? = null,
    minLines: Int = 1,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(placeholder) },
                minLines = minLines,
                maxLines = maxLines,
                isError = error != null,
                keyboardOptions = keyboardOptions,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )

            if (error != null) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun CategorySelectionCard(
    selectedCategoryId: String?,
    categories: List<BuySellCategory>,
    categoryError: String?,
    onClick: () -> Unit
) {
    val selectedCategory = categories.find { it.id == selectedCategoryId }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Category,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "ক্যাটাগরি *",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (selectedCategory != null) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(selectedCategory.color).copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(selectedCategory.color).copy(alpha = 0.2f)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(20.dp),
                                tint = Color(selectedCategory.color)
                            )
                        }
                        Column {
                            Text(
                                text = selectedCategory.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = selectedCategory.nameEn,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "ক্যাটাগরি নির্বাচন করুন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (categoryError != null) {
                Text(
                    text = categoryError,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun ConditionSelectionCard(
    selectedCondition: String,
    conditions: List<Pair<String, String>>,
    onClick: () -> Unit
) {
    val selectedConditionName = conditions.find { it.first == selectedCondition }?.second

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.VerifiedUser,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "কন্ডিশন",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = selectedConditionName ?: "নতুন",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun PriceSection(
    price: String,
    originalPrice: String,
    priceError: String?,
    isNegotiable: Boolean,
    onPriceChange: (String) -> Unit,
    onOriginalPriceChange: (String) -> Unit,
    onNegotiableToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
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
                    imageVector = Icons.Outlined.AttachMoney,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "মূল্য *",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = onPriceChange,
                    modifier = Modifier.weight(1f),
                    label = { Text("বর্তমান মূল্য") },
                    placeholder = { Text("৳ 0") },
                    leadingIcon = {
                        Text(
                            text = "৳",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = priceError != null,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = originalPrice,
                    onValueChange = onOriginalPriceChange,
                    modifier = Modifier.weight(1f),
                    label = { Text("পূর্ববর্তী মূল্য") },
                    placeholder = { Text("৳ 0") },
                    leadingIcon = {
                        Text(
                            text = "৳",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            if (priceError != null) {
                Text(
                    text = priceError,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Switch(
                    checked = isNegotiable,
                    onCheckedChange = onNegotiableToggle
                )
                Text(
                    text = "দাম আলোচনা সাপেক্ষে",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun AdditionalOptionsSection(
    isUrgent: Boolean,
    isFeatured: Boolean,
    onUrgentToggle: (Boolean) -> Unit,
    onFeaturedToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "অতিরিক্ত অপশন",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            OptionRow(
                icon = Icons.Outlined.Bolt,
                title = "জরুরী বিক্রয়",
                description = "দ্রুত বিক্রয়ের জন্য চিহ্নিত করুন",
                isChecked = isUrgent,
                onCheckedChange = onUrgentToggle,
                iconColor = Color(0xFFEF4444)
            )

            OptionRow(
                icon = Icons.Outlined.Star,
                title = "ফিচারড বিজ্ঞাপন",
                description = "উপরে দেখানোর জন্য",
                isChecked = isFeatured,
                onCheckedChange = onFeaturedToggle,
                iconColor = Color(0xFFFFA500)
            )
        }
    }
}

@Composable
fun OptionRow(
    icon: ImageVector,
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
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
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun PublishingTipsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
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
                    imageVector = Icons.Outlined.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFF1976D2)
                )
                Text(
                    text = "বিজ্ঞাপন টিপস",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            val tips = listOf(
                "স্পষ্ট এবং আকর্ষণীয় শিরোনাম দিন",
                "পণ্যের ভালো ছবি যোগ করুন",
                "বিস্তারিত বিবরণ লিখুন",
                "সঠিক মূল্য নির্ধারণ করুন",
                "যোগাযোগ নম্বর সঠিক রাখুন"
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
                        tint = Color(0xFF1976D2)
                    )
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF1565C0)
                    )
                }
            }
        }
    }
}

@Composable
fun PublishBottomBar(
    isPublishing: Boolean,
    onPublish: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        Button(
            onClick = onPublish,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            enabled = !isPublishing,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            if (isPublishing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "প্রকাশ করা হচ্ছে...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "বিজ্ঞাপন প্রকাশ করুন",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPickerDialog(
    categories: List<BuySellCategory>,
    selectedCategoryId: String?,
    onCategorySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
                text = "ক্যাটাগরি নির্বাচন করুন",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            LazyColumn {
                items(categories) { category ->
                    CategoryItem(
                        category = category,
                        isSelected = category.id == selectedCategoryId,
                        onClick = { onCategorySelected(category.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: BuySellCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = if (isSelected) Color(category.color).copy(alpha = 0.1f)
        else Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = Color(category.color).copy(alpha = 0.2f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Category,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp),
                    tint = Color(category.color)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = category.nameEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Color(category.color)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionPickerDialog(
    conditions: List<Pair<String, String>>,
    selectedCondition: String,
    onConditionSelected: (String) -> Unit,
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
                text = "কন্ডিশন নির্বাচন করুন",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )

            conditions.forEach { (code, name) ->
                ListItem(
                    headlineContent = { Text(name) },
                    leadingContent = {
                        RadioButton(
                            selected = selectedCondition == code,
                            onClick = { onConditionSelected(code) }
                        )
                    },
                    modifier = Modifier.clickable { onConditionSelected(code) }
                )
            }
        }
    }
}

@Composable
fun SuccessDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color(0xFF10B981)
            )
        },
        title = {
            Text(
                text = "সফল!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF10B981)
                )
            ) {
                Text("ঠিক আছে")
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
