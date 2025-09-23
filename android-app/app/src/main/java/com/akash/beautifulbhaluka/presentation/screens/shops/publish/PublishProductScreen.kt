package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(modifier = Modifier.fillMaxSize()) {
        // Simple Top Bar
        TopAppBar(
            title = { Text("পণ্য প্রকাশ করুন") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        when {
            uiState.isLoading -> {
                LoadingState()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    // Product Information
                    item {
                        ProductInfoSection(
                            uiState = uiState,
                            onAction = viewModel::onAction
                        )
                    }

                    // Category Selection
                    item {
                        CategorySection(
                            uiState = uiState,
                            onAction = viewModel::onAction
                        )
                    }

                    // Pricing
                    item {
                        PricingSection(
                            uiState = uiState,
                            onAction = viewModel::onAction
                        )
                    }

                    // Seller Information
                    item {
                        SellerInfoSection(
                            uiState = uiState,
                            onAction = viewModel::onAction
                        )
                    }

                    // Publish Button
                    item {
                        PublishButton(
                            isPublishing = uiState.isPublishing,
                            hasValidationErrors = uiState.validationErrors.isNotEmpty(),
                            onPublish = { viewModel.onAction(PublishProductAction.PublishProduct) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ProductInfoSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "পণ্যের তথ্য",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = uiState.productName,
                onValueChange = { onAction(PublishProductAction.UpdateProductName(it)) },
                label = { Text("পণ্যের নাম *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.validationErrors.containsKey("productName")
            )

            OutlinedTextField(
                value = uiState.productDescription,
                onValueChange = { onAction(PublishProductAction.UpdateProductDescription(it)) },
                label = { Text("বিবরণ") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                isError = uiState.validationErrors.containsKey("productDescription")
            )
        }
    }
}

@Composable
private fun CategorySection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "ক্যাটেগরি",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Simple category dropdown or chips would go here
            Text(
                text = "ক্যাটেগরি নির্বাচন করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PricingSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "মূল্য নির্ধারণ",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.price,
                    onValueChange = { onAction(PublishProductAction.UpdatePrice(it)) },
                    label = { Text("দাম (৳) *") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    prefix = { Text("৳") },
                    isError = uiState.validationErrors.containsKey("price")
                )

                OutlinedTextField(
                    value = uiState.stock,
                    onValueChange = { onAction(PublishProductAction.UpdateStock(it)) },
                    label = { Text("স্টক *") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = uiState.validationErrors.containsKey("stock")
                )
            }
        }
    }
}

@Composable
private fun SellerInfoSection(
    uiState: PublishProductUiState,
    onAction: (PublishProductAction) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "বিক্রেতার তথ্য",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = uiState.sellerName,
                onValueChange = { onAction(PublishProductAction.UpdateSellerName(it)) },
                label = { Text("নাম *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = uiState.validationErrors.containsKey("sellerName")
            )

            OutlinedTextField(
                value = uiState.sellerContact,
                onValueChange = { onAction(PublishProductAction.UpdateSellerContact(it)) },
                label = { Text("মোবাইল নম্বর *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                placeholder = { Text("+880 1XXX XXXXXX") },
                isError = uiState.validationErrors.containsKey("sellerContact")
            )

            OutlinedTextField(
                value = uiState.location,
                onValueChange = { onAction(PublishProductAction.UpdateLocation(it)) },
                label = { Text("ঠিকানা *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                isError = uiState.validationErrors.containsKey("location")
            )
        }
    }
}

@Composable
private fun PublishButton(
    isPublishing: Boolean,
    hasValidationErrors: Boolean,
    onPublish: () -> Unit
) {
    Button(
        onClick = onPublish,
        enabled = !isPublishing && !hasValidationErrors,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (isPublishing) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("প্রকাশ হচ্ছে...")
        } else {
            Icon(Icons.Default.Publish, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("পণ্য প্রকাশ করুন")
        }
    }
}
