package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishProductScreen(
    viewModel: PublishProductViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        LoadingState()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Modern Header with Back Button
            ModernHeader(onNavigateBack = onNavigateBack)

            // Product Images Section
            ModernImageSection(
                selectedImages = uiState.selectedImages.toList(),
                onImagesSelected = { uris ->
                    viewModel.onAction(PublishProductAction.AddImages(uris))
                }
            )

            // Product Information
            ModernInfoSection(
                title = "পণ্যের তথ্য",
                subtitle = "আপনার পণ্যের বিস্তারিত তথ্য দিন",
                icon = Icons.Outlined.Inventory2
            ) {
                ModernTextField(
                    value = uiState.productName,
                    onValueChange = { viewModel.onAction(PublishProductAction.UpdateProductName(it)) },
                    label = "পণ্যের নাম",
                    placeholder = "যেমন: স্যামসাং গ্যালাক্সি A54",
                    leadingIcon = Icons.Outlined.ShoppingBag,
                    isRequired = true,
                    isError = uiState.validationErrors.containsKey("productName")
                )

                ModernTextField(
                    value = uiState.productDescription,
                    onValueChange = {
                        viewModel.onAction(
                            PublishProductAction.UpdateProductDescription(
                                it
                            )
                        )
                    },
                    label = "পণ্যের বিবরণ",
                    placeholder = "পণ্যের বৈশিষ্ট্য, অবস্থা এবং অন্যান্য গুরু��্বপূর্ণ তথ্য লিখুন...",
                    leadingIcon = Icons.Outlined.Description,
                    minLines = 4,
                    maxLines = 6,
                    singleLine = false,
                    isError = uiState.validationErrors.containsKey("productDescription")
                )
            }

            // Category & Pricing Section
            ModernInfoSection(
                title = "ক্যাটেগরি ও মূল্য",
                subtitle = "পণ্যের ধরন এবং দাম নির্ধারণ করুন",
                icon = Icons.Outlined.Category
            ) {
                CategoryDropdown(
                    selectedCategory = uiState.selectedCategory?.name ?: "",
                    onCategorySelect = { categoryName ->
                        // Find the category object and pass it to SelectCategory action
                        val category = uiState.categories.find { it.name == categoryName }
                        category?.let { viewModel.onAction(PublishProductAction.SelectCategory(it)) }
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ModernTextField(
                        value = uiState.price,
                        onValueChange = { viewModel.onAction(PublishProductAction.UpdatePrice(it)) },
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
                        onValueChange = { viewModel.onAction(PublishProductAction.UpdateStock(it)) },
                        label = "স্টক",
                        placeholder = "0",
                        leadingIcon = Icons.Outlined.Inventory,
                        isRequired = true,
                        modifier = Modifier.weight(1f),
                        isError = uiState.validationErrors.containsKey("stock")
                    )
                }
            }

            // Seller Information Section
            ModernInfoSection(
                title = "বিক্রেতার তথ্য",
                subtitle = "যোগাযোগের জন্য আপনার তথ্য দিন",
                icon = Icons.Outlined.Person
            ) {
                ModernTextField(
                    value = uiState.sellerName,
                    onValueChange = { viewModel.onAction(PublishProductAction.UpdateSellerName(it)) },
                    label = "আপনার নাম",
                    placeholder = "যেমন: মোহাম্মদ আলী",
                    leadingIcon = Icons.Outlined.Person,
                    isRequired = true,
                    isError = uiState.validationErrors.containsKey("sellerName")
                )

                ModernTextField(
                    value = uiState.sellerContact,
                    onValueChange = { viewModel.onAction(PublishProductAction.UpdateSellerContact(it)) },
                    label = "মোবাইল নম্বর",
                    placeholder = "+880 1XXX XXXXXX",
                    leadingIcon = Icons.Outlined.Phone,
                    isRequired = true,
                    isError = uiState.validationErrors.containsKey("sellerContact")
                )

                ModernTextField(
                    value = uiState.location,
                    onValueChange = { viewModel.onAction(PublishProductAction.UpdateLocation(it)) },
                    label = "এলাকা/ঠিকানা",
                    placeholder = "যেমন: ধানমন্ডি, ঢাকা",
                    leadingIcon = Icons.Outlined.LocationOn,
                    isRequired = true,
                    isError = uiState.validationErrors.containsKey("location")
                )
            }

            // Error Display
            if (uiState.validationErrors.isNotEmpty()) {
                ModernErrorCard(errors = uiState.validationErrors)
            }

            // Modern Publish Button
            ModernPublishButton(
                isPublishing = uiState.isPublishing,
                hasValidationErrors = uiState.validationErrors.isNotEmpty(),
                onPublish = { viewModel.onAction(PublishProductAction.PublishProduct) }
            )
        }
    }
}

@Composable
private fun LoadingState() {
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun ModernHeader(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .size(40.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column {
            Text(
                text = "পণ্য প্রকাশ করুন",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "আপনার পণ্যের তথ্য দিয়ে বিক্রয় শুরু করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ModernImageSection(
    selectedImages: List<Uri>,
    onImagesSelected: (List<Uri>) -> Unit
) {
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        onImagesSelected(uris)
    }

    ModernInfoSection(
        title = "পণ্যের ছবি",
        subtitle = "সর্বোচ্চ ৫টি ছবি যোগ করুন",
        icon = Icons.Outlined.PhotoCamera
    ) {
        if (selectedImages.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { imageLauncher.launch("image/*") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "ছবি যোগ করুন",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${selectedImages.size} টি ছবি নির্বাচিত",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                FilledTonalButton(
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

@Composable
private fun ModernInfoSection(
    title: String,
    subtitle: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
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
                    MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        },
        prefix = prefix?.let { { Text(it) } },
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = minLines,
        maxLines = if (singleLine) 1 else maxLines,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        isError = isError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryDropdown(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf(
        "ইলেকট্রনিক্স",
        "ফ্যাশন",
        "হোম অ্যাপ্লায়েন্স",
        "বই ও স্টেশনারি",
        "খেলাধুলা",
        "গাড়ি ও যন্ত্রাংশ",
        "সৌন্দর্য ও স্বাস্থ্য",
        "অন্যান্য"
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
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
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
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "ত্রুটি সমূহ",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error
                )
            }

            errors.forEach { (_, message) ->
                Text(
                    text = "• $message",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun ModernPublishButton(
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
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        )
    ) {
        if (isPublishing) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "প্রকাশ হচ্ছে...",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.Publish,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "পণ্য প্রকাশ করুন",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
