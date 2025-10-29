package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishProductScreen(
    viewModel: PublishProductViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Modern gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF10B981),
            MaterialTheme.colorScheme.surface
        ),
        startY = 0f,
        endY = 800f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Modern Header
            ModernPublishHeader(
                onNavigateBack = onNavigateBack
            )

            if (uiState.isLoading) {
                ModernLoadingState()
            } else {
                // Scrollable Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Product Images Section
                    ModernImageUploadCard(
                        selectedImages = uiState.selectedImages.toList(),
                        onImagesSelected = { uris ->
                            viewModel.onAction(PublishProductAction.AddImages(uris))
                        }
                    )

                    // Product Information Card
                    ModernInfoCard(
                        title = "পণ্যের তথ্য",
                        subtitle = "আপনার পণ্যের বিস্তারিত তথ্য দিন",
                        icon = Icons.Outlined.Inventory2,
                        iconColor = Color(0xFF10B981)
                    ) {
                        ModernTextField(
                            value = uiState.productName,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishProductAction.UpdateProductName(
                                        it
                                    )
                                )
                            },
                            label = "পণ্যের নাম",
                            placeholder = "যেমন: স্যামসাং গ্যালাক্সি A54",
                            leadingIcon = Icons.Outlined.ShoppingBag,
                            isRequired = true,
                            isError = uiState.validationErrors.containsKey("productName")
                        )

                        ModernTextField(
                            value = uiState.productDescription,
                            onValueChange = {
                                viewModel.onAction(PublishProductAction.UpdateProductDescription(it))
                            },
                            label = "পণ্যের বিবরণ",
                            placeholder = "পণ্যের বৈশিষ্ট্য, অবস্থা এবং অন্যান্য গুরুত্বপূর্ণ তথ্য লিখুন...",
                            leadingIcon = Icons.Outlined.Description,
                            minLines = 4,
                            maxLines = 6,
                            singleLine = false,
                            isError = uiState.validationErrors.containsKey("productDescription")
                        )
                    }

                    // Category & Pricing Card
                    ModernInfoCard(
                        title = "ক্যাটেগরি ও মূল্য",
                        subtitle = "পণ্যের ধরন এবং দাম নির্ধারণ করুন",
                        icon = Icons.Outlined.Category,
                        iconColor = Color(0xFF8B5CF6)
                    ) {
                        ModernCategoryDropdown(
                            selectedCategory = uiState.selectedCategory?.name ?: "",
                            onCategorySelect = { categoryName ->
                                val category = uiState.categories.find { it.name == categoryName }
                                category?.let {
                                    viewModel.onAction(
                                        PublishProductAction.SelectCategory(
                                            it
                                        )
                                    )
                                }
                            },
                            isError = uiState.validationErrors.containsKey("category")
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ModernTextField(
                                value = uiState.price,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishProductAction.UpdatePrice(
                                            it
                                        )
                                    )
                                },
                                label = "দাম",
                                placeholder = "0",
                                leadingIcon = Icons.Outlined.AttachMoney,
                                isRequired = true,
                                modifier = Modifier.weight(1f),
                                prefix = "৳ ",
                                isError = uiState.validationErrors.containsKey("price")
                            )

                            ModernTextField(
                                value = uiState.stock,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishProductAction.UpdateStock(
                                            it
                                        )
                                    )
                                },
                                label = "স্টক",
                                placeholder = "0",
                                leadingIcon = Icons.Outlined.Inventory,
                                isRequired = true,
                                modifier = Modifier.weight(1f),
                                isError = uiState.validationErrors.containsKey("stock")
                            )
                        }
                    }

                    // Seller Information Card
                    ModernInfoCard(
                        title = "বিক্রেতার তথ্য",
                        subtitle = "যোগাযোগের জন্য আপনার তথ্য দিন",
                        icon = Icons.Outlined.Person,
                        iconColor = Color(0xFF3B82F6)
                    ) {
                        ModernTextField(
                            value = uiState.sellerName,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishProductAction.UpdateSellerName(
                                        it
                                    )
                                )
                            },
                            label = "আপনার নাম",
                            placeholder = "যেমন: মোহাম্মদ আলী",
                            leadingIcon = Icons.Outlined.Person,
                            isRequired = true,
                            isError = uiState.validationErrors.containsKey("sellerName")
                        )

                        ModernTextField(
                            value = uiState.sellerContact,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishProductAction.UpdateSellerContact(
                                        it
                                    )
                                )
                            },
                            label = "মোবাইল নম্বর",
                            placeholder = "+880 1XXX XXXXXX",
                            leadingIcon = Icons.Outlined.Phone,
                            isRequired = true,
                            isError = uiState.validationErrors.containsKey("sellerContact")
                        )

                        ModernTextField(
                            value = uiState.location,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishProductAction.UpdateLocation(
                                        it
                                    )
                                )
                            },
                            label = "এলাকা/ঠিকানা",
                            placeholder = "যেমন: ধানমন্ডি, ঢাকা",
                            leadingIcon = Icons.Outlined.LocationOn,
                            isRequired = true,
                            isError = uiState.validationErrors.containsKey("location")
                        )
                    }

                    // Error Display
                    AnimatedVisibility(
                        visible = uiState.validationErrors.isNotEmpty(),
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        ModernErrorCard(errors = uiState.validationErrors)
                    }

                    // Modern Publish Button with Gradient
                    GradientPublishButton(
                        isPublishing = uiState.isPublishing,
                        hasValidationErrors = uiState.validationErrors.isNotEmpty(),
                        onPublish = { viewModel.onAction(PublishProductAction.PublishProduct) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ModernPublishHeader(onNavigateBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "পণ্য প্রকাশ করুন",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "আপনার পণ্যের তথ্য দিয়ে বিক্রয় শুরু করুন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
private fun ModernImageUploadCard(
    selectedImages: List<Uri>,
    onImagesSelected: (List<Uri>) -> Unit
) {
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        onImagesSelected(uris)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFF59E0B)
                        )
                    }
                }

                Column {
                    Text(
                        text = "পণ্যের ছবি",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "সর্বোচ্চ ৫টি ছবি যোগ করুন",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (selectedImages.isEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { imageLauncher.launch("image/*") },
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                            modifier = Modifier.size(72.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.AddPhotoAlternate,
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp),
                                    tint = Color(0xFFF59E0B)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "ছবি যোগ করুন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "ট্যাপ করে ছবি নির্বাচন করুন",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF10B981).copy(alpha = 0.1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${selectedImages.size} টি ছবি নির্বাচিত",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF10B981),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Show image previews
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedImages.take(3).forEach { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        if (selectedImages.size > 3) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+${selectedImages.size - 3}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    OutlinedButton(
                        onClick = { imageLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("আরো ছবি যোগ করুন")
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernInfoCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = iconColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = iconColor
                        )
                    }
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = content
            )
        }
    }
}

@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    prefix: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = if (isRequired) "$label *" else label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = if (isError)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        prefix = prefix?.let { { Text(it) } },
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = minLines,
        maxLines = if (singleLine) 1 else maxLines,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        isError = isError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernCategoryDropdown(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    isError: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf(
        "ইলেকট্রনিক্স",
        "পোশাক",
        "খাবার",
        "বই",
        "মোবাইল",
        "গাড়ি",
        "আসবাবপত্র",
        "স্বাস্থ্য"
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,
            label = { Text("ক্যাটেগরি *") },
            placeholder = { Text("একটি ক্যাটেগরি নির্বাচন করুন") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Category,
                    contentDescription = null,
                    tint = if (isError)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            isError = isError,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelect(category)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Category,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ModernErrorCard(errors: Map<String, String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEF4444).copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "দয়া করে নিচের ত্রুটিগুলো সংশোধন করুন",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFEF4444)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                errors.forEach { (_, message) ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "•",
                            color = Color(0xFFEF4444),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = message,
                            color = Color(0xFFEF4444),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GradientPublishButton(
    isPublishing: Boolean,
    hasValidationErrors: Boolean,
    onPublish: () -> Unit
) {
    Button(
        onClick = onPublish,
        enabled = !isPublishing && !hasValidationErrors,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = if (!isPublishing && !hasValidationErrors) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF10B981),
                                Color(0xFF059669)
                            )
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isPublishing) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                    Text(
                        text = "প্রকাশ হচ্ছে...",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Publish,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = if (hasValidationErrors)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            Color.White
                    )
                    Text(
                        text = "পণ্য প্রকাশ করুন",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (hasValidationErrors)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            Color.White
                    )
                }
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
                strokeWidth = 4.dp,
                color = Color(0xFF10B981)
            )
            Text(
                text = "লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
