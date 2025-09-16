package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishProductScreen(
    viewModel: PublishProductViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onProductPublished: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle success navigation
    LaunchedEffect(uiState.isPublishSuccessful) {
        if (uiState.isPublishSuccessful) {
            onProductPublished()
            viewModel.onAction(PublishProductAction.DismissSuccessMessage)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar
        TopAppBar(
            title = { Text("পণ্য প্রকাশ করুন") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                TextButton(
                    onClick = { viewModel.onAction(PublishProductAction.ClearForm) },
                    enabled = !uiState.isPublishing
                ) {
                    Text("সব মুছুন")
                }
            }
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                PublishProductContent(
                    uiState = uiState,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublishProductContent(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image Upload Section
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "পণ্যের ছবি",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "সর্বোচ্চ ৫টি ছবি আপলোড করতে পারবেন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Image upload grid
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Add image button
                        if (uiState.selectedImages.size < 5) {
                            item {
                                AddImageButton(
                                    onClick = { onAction(PublishProductAction.AddImage) }
                                )
                            }
                        }

                        // Display selected images
                        items(uiState.selectedImages.toList()) { imageUri ->
                            SelectedImageCard(
                                imageUri = imageUri,
                                onRemove = { onAction(PublishProductAction.RemoveImage(imageUri)) }
                            )
                        }
                    }

                    uiState.validationErrors["images"]?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Product Basic Info
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "পণ্যের তথ্য",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = uiState.productName,
                        onValueChange = { onAction(PublishProductAction.UpdateProductName(it)) },
                        label = { Text("পণ্যের নাম *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.validationErrors.containsKey("productName"),
                        supportingText = {
                            uiState.validationErrors["productName"]?.let { error ->
                                Text(error, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = uiState.productDescription,
                        onValueChange = { onAction(PublishProductAction.UpdateProductDescription(it)) },
                        label = { Text("পণ্যের বিবরণ *") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5,
                        isError = uiState.validationErrors.containsKey("productDescription"),
                        supportingText = {
                            uiState.validationErrors["productDescription"]?.let { error ->
                                Text(error, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }
            }
        }

        // Category Selection
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "ক্যাটেগরি *",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.categories) { category ->
                            FilterChip(
                                selected = uiState.selectedCategory?.id == category.id,
                                onClick = { onAction(PublishProductAction.SelectCategory(category)) },
                                label = { Text(category.name) }
                            )
                        }
                    }

                    uiState.validationErrors["category"]?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Condition Selection
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "পণ্যের অবস্থা",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ProductCondition.entries.toTypedArray()) { condition ->
                            FilterChip(
                                selected = uiState.condition == condition,
                                onClick = { onAction(PublishProductAction.UpdateCondition(condition)) },
                                label = { Text(condition.displayName) }
                            )
                        }
                    }
                }
            }
        }

        // Price and Stock
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "দাম ও স্টক",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.price,
                            onValueChange = { onAction(PublishProductAction.UpdatePrice(it)) },
                            label = { Text("দাম (৳) *") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = uiState.validationErrors.containsKey("price"),
                            supportingText = {
                                uiState.validationErrors["price"]?.let { error ->
                                    Text(error, color = MaterialTheme.colorScheme.error)
                                }
                            }
                        )

                        OutlinedTextField(
                            value = uiState.originalPrice,
                            onValueChange = { onAction(PublishProductAction.UpdateOriginalPrice(it)) },
                            label = { Text("আসল দাম (৳)") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    OutlinedTextField(
                        value = uiState.stock,
                        onValueChange = { onAction(PublishProductAction.UpdateStock(it)) },
                        label = { Text("স্টক পরিমাণ *") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.validationErrors.containsKey("stock"),
                        supportingText = {
                            uiState.validationErrors["stock"]?.let { error ->
                                Text(error, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }
            }
        }

        // Seller Information
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "বিক্রেতার তথ্য",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = uiState.sellerName,
                        onValueChange = { onAction(PublishProductAction.UpdateSellerName(it)) },
                        label = { Text("বিক্রেতার নাম *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.validationErrors.containsKey("sellerName"),
                        supportingText = {
                            uiState.validationErrors["sellerName"]?.let { error ->
                                Text(error, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = uiState.sellerContact,
                        onValueChange = { onAction(PublishProductAction.UpdateSellerContact(it)) },
                        label = { Text("যোগাযোগের নম্বর *") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = uiState.validationErrors.containsKey("sellerContact"),
                        supportingText = {
                            uiState.validationErrors["sellerContact"]?.let { error ->
                                Text(error, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = uiState.location,
                        onValueChange = { onAction(PublishProductAction.UpdateLocation(it)) },
                        label = { Text("ঠিকানা *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.validationErrors.containsKey("location"),
                        supportingText = {
                            uiState.validationErrors["location"]?.let { error ->
                                Text(error, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }
            }
        }

        // Publish Button
        item {
            Button(
                onClick = { onAction(PublishProductAction.PublishProduct) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isPublishing
            ) {
                if (uiState.isPublishing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("প্রকাশ হচ্ছে...")
                } else {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("পণ্য প্রকাশ করুন")
                }
            }
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AddImageButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Image",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "ছবি যোগ করুন",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SelectedImageCard(
    imageUri: String,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier.size(100.dp)
    ) {
        // Image placeholder (in real app, use AsyncImage for loading actual images)
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.government_seal_of_bangladesh), // Placeholder
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Remove button
        IconButton(
            onClick = onRemove,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.error,
                    shape = RoundedCornerShape(50)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = MaterialTheme.colorScheme.onError,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
