package com.akash.beautifulbhaluka.presentation.screens.shops.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.Product
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition

class ProductDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    // Mock data - in real app this would come from repository
    private val mockProducts = listOf(
        Product(
            id = "1",
            name = "স্যামসাং গ্যালাক্সি A54",
            description = "একদম নতুন কন্ডিশনে স্যামসাং গ্যালাক্সি A54। সব ফিচার সহ। ৬GB RAM, ১২৮GB স্টোরেজ। সাথে অরিজিনাল চার্জার ও ইয়ারফোন দেওয়া হবে।",
            price = 35000.0,
            originalPrice = 40000.0,
            imageUrl = "https://via.placeholder.com/300",
            category = ProductCategory(
                "5",
                "মোবাইল",
                "Mobile",
                R.drawable.ic_launcher_foreground,
                1
            ),
            stock = 5,
            sellerName = "মোঃ রহিম উদ্দিন",
            sellerContact = "+880 1711 123456",
            location = "ভালুকা, ময়মনসিংহ",
            rating = 4.5f,
            reviewCount = 23,
            isNew = true,
            isFeatured = true,
            condition = ProductCondition.NEW
        ),
        Product(
            id = "2",
            name = "হোন্ডা মোটরসাইকেল",
            description = "২০২২ মডেল হোন্ডা বাইক, খুবই ভালো কন্ডিশন। নিয়মিত সার্ভিসিং করা হয়েছে। কোনো দুর্ঘটনার ইতিহাস নেই।",
            price = 125000.0,
            imageUrl = "https://via.placeholder.com/300",
            category = ProductCategory(
                "6",
                "গাড়ি",
                "Vehicles",
                R.drawable.ic_launcher_foreground,
                1
            ),
            stock = 1,
            sellerName = "আব্দুল করিম",
            sellerContact = "+880 1712 654321",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 8,
            isFeatured = true,
            condition = ProductCondition.USED
        )
    )

    fun loadProductDetails(productId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                // Simulate network delay
                delay(1000)

                val product = mockProducts.find { it.id == productId }

                if (product != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        product = product,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        product = null,
                        error = "পণ্যটি খুঁজে পাওয়া যায়নি"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "পণ্য লোড করতে সমস্যা হয়েছে: ${e.message}"
                )
            }
        }
    }

    fun onAction(action: ProductDetailsAction) {
        when (action) {
            is ProductDetailsAction.LoadProduct -> loadProductDetails(action.productId)
            ProductDetailsAction.ToggleFavorite -> toggleFavorite()
            ProductDetailsAction.ContactSeller -> contactSeller()
            ProductDetailsAction.ShareProduct -> shareProduct()
            ProductDetailsAction.ReportProduct -> reportProduct()
        }
    }

    private fun toggleFavorite() {
        _uiState.value = _uiState.value.copy(
            isFavorite = !_uiState.value.isFavorite
        )
    }

    private fun contactSeller() {
        // In real app, this would open phone dialer or messaging app
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isContactingSeller = true)
            delay(500)
            _uiState.value = _uiState.value.copy(isContactingSeller = false)
        }
    }

    private fun shareProduct() {
        // In real app, this would open share intent
    }

    private fun reportProduct() {
        // In real app, this would open report functionality
    }
}
