package com.akash.beautifulbhaluka.presentation.screens.shops.details

// ProductDetailsAction - Following Architecture Pattern
sealed class ProductDetailsAction {
    data class LoadProduct(val productId: String) : ProductDetailsAction()
    object ShareProduct : ProductDetailsAction()
    object ToggleFavorite : ProductDetailsAction()
    object ContactSeller : ProductDetailsAction()
    object DismissSuccessMessage : ProductDetailsAction()
}
