package com.akash.beautifulbhaluka.presentation.screens.shops.category

import com.akash.beautifulbhaluka.presentation.screens.shops.Product
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.SortOption

data class CategoryProductsUiState(
    val isLoading: Boolean = false,
    val category: ProductCategory? = null,
    val products: List<Product> = emptyList(),
    val error: String? = null
)

sealed class CategoryProductsAction {
    data class SortProducts(val sortOption: SortOption) : CategoryProductsAction()
    object Refresh : CategoryProductsAction()
}

