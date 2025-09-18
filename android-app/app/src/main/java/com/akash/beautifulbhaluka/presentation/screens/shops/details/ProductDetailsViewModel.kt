package com.akash.beautifulbhaluka.presentation.screens.shops.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.Product
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition

class ProductDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    // Mock data - in real app this would come from repository/usecase
    private val mockProducts = listOf(
        Product(
            id = "1",
            name = "স্যামসাং গ্যালাক্সি A54",
            description = "একদম নতুন কন্ডিশনে স্যামসাং গ্যালাক্সি A54। সব ফিচার সহ। ৬GB RAM, ১২৮GB স্টোরেজ। সাথে অরিজিনাল চার্জার ও ইয়ারফোন দেওয়া হবে।",
            price = 35000.0,
            originalPrice = 40000.0,
            imageUrl = "https://via.placeholder.com/300",
            imageUrls = listOf(
                "https://via.placeholder.com/300x300/FF0000",
                "https://via.placeholder.com/300x300/00FF00",
                "https://via.placeholder.com/300x300/0000FF"
            ),
            category = ProductCategory(
                "5",
                "মোবাইল",
                "Mobile",
                R.drawable.government_seal_of_bangladesh,
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
            imageUrls = listOf("https://via.placeholder.com/300"),
            category = ProductCategory(
                "6",
                "যানবাহন",
                "Vehicle",
                R.drawable.government_seal_of_bangladesh,
                1
            ),
            stock = 1,
            sellerName = "আব্দুল করিম",
            sellerContact = "+880 1812 987654",
            location = "ভালুকা, ময়মনসিংহ",
            rating = 4.2f,
            reviewCount = 15,
            condition = ProductCondition.USED
        ),
        Product(
            id = "3",
            name = "ল্যাপটপ Dell Inspiron",
            description = "Dell Inspiron 15 3000 সিরিজ। Intel Core i5 প্রসেসর, 8GB RAM, 1TB HDD। অফিস ও পড়াশোনার জন্য পারফেক্ট।",
            price = 45000.0,
            originalPrice = 55000.0,
            imageUrl = "https://via.placeholder.com/300",
            imageUrls = listOf("https://via.placeholder.com/300"),
            category = ProductCategory(
                "7",
                "কম্পিউটার",
                "Computer",
                R.drawable.government_seal_of_bangladesh,
                1
            ),
            stock = 2,
            sellerName = "নাসির আহমেদ",
            sellerContact = "+880 1916 555777",
            location = "ভালুকা, ময়মনসিংহ",
            rating = 4.7f,
            reviewCount = 31,
            condition = ProductCondition.REFURBISHED
        )
    )

    // Action handler following architecture pattern
    fun onAction(action: ProductDetailsAction) {
        when (action) {
            is ProductDetailsAction.LoadProduct -> {
                loadProduct(action.productId)
            }

            ProductDetailsAction.ShareProduct -> {
                shareProduct()
            }

            ProductDetailsAction.ToggleFavorite -> {
                toggleFavorite()
            }

            ProductDetailsAction.ContactSeller -> {
                contactSeller()
            }

            ProductDetailsAction.DismissSuccessMessage -> {
                dismissSuccessMessage()
            }
        }
    }

    private fun loadProduct(productId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Simulate network delay
                delay(1500)

                val product = mockProducts.find { it.id == productId }
                if (product != null) {
                    _uiState.update {
                        it.copy(
                            product = product,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "পণ্যটি পাওয়া যায়নি"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "পণ্য লোড করতে সমস্যা হয়েছে: ${e.message}"
                    )
                }
            }
        }
    }

    private fun shareProduct() {
        // Implementation for sharing product
        // This would typically involve Android's Intent system
    }

    private fun toggleFavorite() {
        _uiState.update {
            it.copy(isFavorite = !it.isFavorite)
        }
    }

    private fun contactSeller() {
        viewModelScope.launch {
            _uiState.update { it.copy(isContactingSeller = true) }

            // Simulate contacting seller
            delay(2000)

            _uiState.update {
                it.copy(
                    isContactingSeller = false,
                    contactSellerSuccess = true
                )
            }
        }
    }

    private fun dismissSuccessMessage() {
        _uiState.update {
            it.copy(contactSellerSuccess = false)
        }
    }
}
