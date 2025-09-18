package com.akash.beautifulbhaluka.presentation.screens.shops.details

import com.akash.beautifulbhaluka.presentation.screens.shops.Product

data class ProductDetailsUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null,
    val isContactingSeller: Boolean = false,
    val isFavorite: Boolean = false,
    val contactSellerSuccess: Boolean = false
)
