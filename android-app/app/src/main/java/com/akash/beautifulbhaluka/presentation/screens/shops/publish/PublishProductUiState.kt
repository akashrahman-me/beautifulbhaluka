package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition

data class PublishProductUiState(
    val isLoading: Boolean = false,
    val isPublishing: Boolean = false,
    val categories: List<ProductCategory> = emptyList(),
    val selectedCategory: ProductCategory? = null,
    val productName: String = "",
    val productDescription: String = "",
    val price: String = "",
    val originalPrice: String = "",
    val condition: ProductCondition = ProductCondition.NEW,
    val stock: String = "",
    val sellerName: String = "",
    val sellerContact: String = "",
    val location: String = "",
    val selectedImages: List<String> = emptyList(),
    val error: String? = null,
    val validationErrors: Map<String, String> = emptyMap(),
    val isPublishSuccessful: Boolean = false
)

sealed class PublishProductAction {
    data class UpdateProductName(val name: String) : PublishProductAction()
    data class UpdateProductDescription(val description: String) : PublishProductAction()
    data class UpdatePrice(val price: String) : PublishProductAction()
    data class UpdateOriginalPrice(val originalPrice: String) : PublishProductAction()
    data class UpdateCondition(val condition: ProductCondition) : PublishProductAction()
    data class UpdateStock(val stock: String) : PublishProductAction()
    data class UpdateSellerName(val name: String) : PublishProductAction()
    data class UpdateSellerContact(val contact: String) : PublishProductAction()
    data class UpdateLocation(val location: String) : PublishProductAction()
    data class SelectCategory(val category: ProductCategory) : PublishProductAction()
    data class AddImage(val imageUri: String) : PublishProductAction()
    data class RemoveImage(val imageUri: String) : PublishProductAction()
    object PublishProduct : PublishProductAction()
    object ClearForm : PublishProductAction()
    object DismissSuccessMessage : PublishProductAction()
}
