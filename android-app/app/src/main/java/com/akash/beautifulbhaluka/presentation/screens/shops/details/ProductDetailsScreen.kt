package com.akash.beautifulbhaluka.presentation.screens.shops.details

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.screens.shops.Product
import com.akash.beautifulbhaluka.presentation.components.common.Carousel
import com.akash.beautifulbhaluka.presentation.screens.home.CarouselItem

// Main Screen Composable (Following Architecture Pattern)
@Composable
fun ProductDetailsScreen(
    productId: String,
    viewModel: ProductDetailsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Load product on screen initialization
    LaunchedEffect(productId) {
        viewModel.onAction(ProductDetailsAction.LoadProduct(productId))
    }

    // Stateless Content Composable
    ProductDetailsContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack
    )
}

// Stateless Content Composable (Following Architecture Pattern)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductDetailsContent(
    uiState: ProductDetailsUiState,
    onAction: (ProductDetailsAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar
        TopAppBar(
            title = { Text("পণ্যের বিস্তারিত") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { onAction(ProductDetailsAction.ShareProduct) }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
                IconButton(onClick = { onAction(ProductDetailsAction.ToggleFavorite) }) {
                    Icon(
                        if (uiState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (uiState.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )

        when {
            uiState.isLoading -> {
                LoadingState()
            }

            uiState.error != null -> {
                ErrorState(
                    error = uiState.error,
                    onRetry = { /* Retry action could be added */ }
                )
            }

            uiState.product != null -> {
                ProductDetailsBody(
                    product = uiState.product,
                    isContactingSeller = uiState.isContactingSeller,
                    onContactSeller = { onAction(ProductDetailsAction.ContactSeller) }
                )
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("পণ্যের তথ্য লোড হচ্ছে...")
        }
    }
}

@Composable
private fun ErrorState(
    error: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Error,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("আবার চেষ্টা করুন")
            }
        }
    }
}

@Composable
private fun ProductDetailsBody(
    product: Product,
    isContactingSeller: Boolean,
    onContactSeller: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Product images carousel
        item {
            ProductImageCarousel(product = product)
        }

        // Product basic info
        item {
            ProductBasicInfo(product = product)
        }

        // Product description
        item {
            ProductDescription(description = product.description)
        }

        // Seller information
        item {
            SellerInformation(
                product = product,
                isContactingSeller = isContactingSeller,
                onContactSeller = onContactSeller
            )
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ProductImageCarousel(product: Product) {
    // Convert product images to CarouselItem format
    val carouselItems = if (product.imageUrls.isNotEmpty()) {
        product.imageUrls.mapIndexed { index, imageUrl ->
            CarouselItem(
                id = index.toString(),
                title = product.name,
                imageUrl = imageUrl,
                description = "Product image ${index + 1}"
            )
        }
    } else {
        // Fallback carousel items for products without multiple images
        listOf(
            CarouselItem(
                id = "0",
                title = product.name,
                imageUrl = product.imageUrl,
                description = product.name
            )
        )
    }

    Carousel(
        items = carouselItems,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    )
}

@Composable
private fun ProductBasicInfo(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${product.currency}${product.price.toInt()}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (product.originalPrice != null) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "${product.currency}${product.originalPrice.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "অবস্থা: ${product.condition.displayName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "স্টক: ${product.stock}টি",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (product.isInStock)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun ProductDescription(description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "বিবরণ",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SellerInformation(
    product: Product,
    isContactingSeller: Boolean,
    onContactSeller: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "বিক্রেতার তথ্য",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = product.sellerName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = product.sellerContact,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onContactSeller,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isContactingSeller
            ) {
                if (isContactingSeller) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("যোগাযোগ করা হচ্ছে...")
                } else {
                    Icon(Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("বিক্রেতার সাথে যোগাযোগ করুন")
                }
            }
        }
    }
}
