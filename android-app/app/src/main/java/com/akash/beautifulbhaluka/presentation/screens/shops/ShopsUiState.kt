package com.akash.beautifulbhaluka.presentation.screens.shops

data class ShopsUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<ProductCategory> = emptyList(),
    val selectedCategory: ProductCategory? = null,
    val searchQuery: String = "",
    val filteredProducts: List<Product> = emptyList(),
    val error: String? = null
)

data class Product(
    val id: String,
    val name: String,
    val title: String = name, // Add title property that maps to name
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val currency: String = "৳", // Add currency property
    val imageUrl: String,
    val imageUrls: List<String> = emptyList(),
    val category: ProductCategory,
    val condition: ProductCondition = ProductCondition.NEW, // Add condition property
    val stock: Int,
    val isInStock: Boolean = stock > 0,
    val sellerName: String,
    val sellerContact: String,
    val location: String,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val isNew: Boolean = false,
    val isFeatured: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class ProductCategory(
    val id: String,
    val name: String,
    val nameEn: String,
    val icon: Int,
    val productCount: Int = 0 // Add productCount property
)

// Add ProductCondition enum
enum class ProductCondition(val displayName: String) {
    NEW("নতুন"),
    USED("ব্যবহৃত"),
    REFURBISHED("পুনর্নির্মিত")
}

enum class SortOption {
    NEWEST,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    RATING,
    NAME
}

sealed class ShopsAction {
    data class SearchProducts(val query: String) : ShopsAction()
    data class SelectCategory(val category: ProductCategory?) : ShopsAction()
    data class ToggleFavorite(val productId: String) : ShopsAction()
    data class SortProducts(val sortOption: SortOption) : ShopsAction()
    object Refresh : ShopsAction()
    object ClearFilters : ShopsAction()
}
