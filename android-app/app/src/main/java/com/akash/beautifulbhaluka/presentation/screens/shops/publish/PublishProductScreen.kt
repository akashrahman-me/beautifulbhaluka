package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                        items(ProductCondition.values()) { condition ->
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

        // Error Message
        if (uiState.error != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }

        // Publish Button
        item {
            Button(
                onClick = { onAction(PublishProductAction.PublishProduct) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isPublishing
            ) {
                if (uiState.isPublishing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("প্রকাশ করা হচ্ছে...")
                } else {
                    Icon(Icons.Default.Publish, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("পণ্য প্রকাশ করুন")
                }
            }
        }

        // Add spacing at bottom
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
