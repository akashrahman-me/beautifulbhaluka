package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import android.net.Uri
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition

data class PublishProductUiState(
    val isLoading: Boolean = false,
    val isPublishing: Boolean = false,
    val isPublishSuccessful: Boolean = false,

    // Product Information
    val productName: String = "",
    val productDescription: String = "",
    val selectedCategory: ProductCategory? = null,
    val condition: ProductCondition = ProductCondition.NEW,
    val price: String = "",
    val originalPrice: String = "",
    val stock: String = "",

    // Images
    val selectedImages: Set<Uri> = emptySet(),

    // Seller Information
    val sellerName: String = "",
    val sellerContact: String = "",
    val location: String = "",

    // Available Data
    val categories: List<ProductCategory> = emptyList(),

    // Validation
    val validationErrors: Map<String, String> = emptyMap(),

    // UI States
    val showSuccessDialog: Boolean = false,
    val errorMessage: String? = null
)

// Actions following architecture pattern
sealed interface PublishProductAction {
    data class UpdateProductName(val name: String) : PublishProductAction
    data class UpdateProductDescription(val description: String) : PublishProductAction
    data class UpdatePrice(val price: String) : PublishProductAction
    data class UpdateOriginalPrice(val originalPrice: String) : PublishProductAction
    data class UpdateCondition(val condition: ProductCondition) : PublishProductAction
    data class UpdateStock(val stock: String) : PublishProductAction
    data class UpdateSellerName(val name: String) : PublishProductAction
    data class UpdateSellerContact(val contact: String) : PublishProductAction
    data class UpdateLocation(val location: String) : PublishProductAction
    data class SelectCategory(val category: ProductCategory) : PublishProductAction
    data class AddImages(val uris: List<Uri>) : PublishProductAction
    data class RemoveImage(val uri: Uri) : PublishProductAction
    data object PublishProduct : PublishProductAction
    data object ClearForm : PublishProductAction
    data object DismissSuccessMessage : PublishProductAction
}
