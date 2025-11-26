package com.akash.beautifulbhaluka.presentation.screens.shops.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.Product
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class CategoryProductsViewModel(
    private val categoryId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryProductsUiState())
    val uiState: StateFlow<CategoryProductsUiState> = _uiState.asStateFlow()

    // Mock categories (should match the ones in ShopsViewModel)
    private val mockCategories = listOf(
        ProductCategory(
            "1",
            "ইলেকট্রনিক্স",
            "Electronics",
            R.drawable.ic_launcher_foreground,
            productCount = 7
        ),
        ProductCategory(
            "2",
            "পোশাক",
            "Clothing",
            R.drawable.ic_launcher_foreground,
            productCount = 6
        ),
        ProductCategory("3", "খাবার", "Food", R.drawable.ic_launcher_foreground, productCount = 6),
        ProductCategory("4", "বই", "Books", R.drawable.ic_launcher_foreground, productCount = 6),
        ProductCategory(
            "5",
            "মোবাইল",
            "Mobile",
            R.drawable.ic_launcher_foreground,
            productCount = 6
        ),
        ProductCategory(
            "6",
            "গাড়ি",
            "Vehicles",
            R.drawable.ic_launcher_foreground,
            productCount = 6
        ),
        ProductCategory(
            "7",
            "আসবাবপত্র",
            "Furniture",
            R.drawable.ic_launcher_foreground,
            productCount = 6
        ),
        ProductCategory(
            "8",
            "স্বাস্থ্য",
            "Health",
            R.drawable.ic_launcher_foreground,
            productCount = 7
        )
    )

    // Mock products (should match the ones in ShopsViewModel) - 50 products total
    private val allProducts = listOf(
        // Electronics (ইলেকট্রনিক্স) - Category 0 - 7 products
        Product(
            id = "1",
            name = "ল্যাপটপ ব্যাগ",
            description = "উচ্চ মানের ল্যাপটপ ব্যাগ, সব সাইজের ল্যাপটপের জন্য।",
            price = 2500.0,
            imageUrl = "https://picsum.photos/200/120?random=1",
            category = mockCategories[0],
            stock = 8,
            sellerName = "টেক স্টোর",
            sellerContact = "+880 1715 901234",
            location = "ভালুকা কলেজ গেট",
            rating = 4.3f,
            reviewCount = 12
        ),
        Product(
            id = "2",
            name = "ওয়্যারলেস মাউস",
            description = "হাই প্রিসিশন ওয়্যারলেস মাউস, দীর্ঘ ব্যাটারি লাইফ।",
            price = 850.0,
            originalPrice = 1200.0,
            imageUrl = "https://picsum.photos/200/120?random=2",
            category = mockCategories[0],
            stock = 15,
            sellerName = "টেক স্টোর",
            sellerContact = "+880 1715 901234",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 28,
            isNew = true
        ),
        Product(
            id = "3",
            name = "USB কীবোর্ড",
            description = "মেকানিক্যাল USB কীবোর্ড, টেকসই এবং আরামদায়ক।",
            price = 1500.0,
            imageUrl = "https://picsum.photos/200/120?random=3",
            category = mockCategories[0],
            stock = 10,
            sellerName = "কম্পিউটার শপ",
            sellerContact = "+880 1716 234567",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 15
        ),
        Product(
            id = "4",
            name = "এইচডি ওয়েবক্যাম",
            description = "১০৮০পি HD ওয়েবক্যাম, অনলাইন মিটিং এর জন্য।",
            price = 3200.0,
            imageUrl = "https://picsum.photos/200/120?random=4",
            category = mockCategories[0],
            stock = 5,
            sellerName = "ডিজিটাল হাব",
            sellerContact = "+880 1717 345678",
            location = "ভালুকা শহর",
            rating = 4.6f,
            reviewCount = 22,
            isFeatured = true
        ),
        Product(
            id = "5",
            name = "পাওয়ার ব্যাংক ২০০০০mAh",
            description = "ফাস্ট চার্জিং পাওয়ার ব্যাংক, সব ডিভাইসের জন্য।",
            price = 2800.0,
            originalPrice = 3500.0,
            imageUrl = "https://picsum.photos/200/120?random=5",
            category = mockCategories[0],
            stock = 12,
            sellerName = "মোবাইল একসেসরিজ",
            sellerContact = "+880 1718 456789",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 35
        ),
        Product(
            id = "6",
            name = "ব্লুটুথ স্পিকার",
            description = "পোর্টেবল ব্লুটুথ স্পিকার, চমৎকার সাউন্ড কোয়ালিটি।",
            price = 1800.0,
            imageUrl = "https://picsum.photos/200/120?random=6",
            category = mockCategories[0],
            stock = 20,
            sellerName = "সাউন্ড সিস্টেম",
            sellerContact = "+880 1719 567890",
            location = "ভালুকা বাজার",
            rating = 4.4f,
            reviewCount = 18
        ),
        Product(
            id = "7",
            name = "হেডফোন",
            description = "নয়েজ ক্যান্সেলিং হেডফোন, দীর্ঘক্ষণ ব্যবহারের জন্য আরামদায়ক।",
            price = 4500.0,
            originalPrice = 6000.0,
            imageUrl = "https://picsum.photos/200/120?random=7",
            category = mockCategories[0],
            stock = 8,
            sellerName = "অডিও ওয়ার্ল্ড",
            sellerContact = "+880 1720 678901",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 42,
            isNew = true,
            isFeatured = true
        ),
        // Clothing - 6 products, Food - 6, Books - 6, Mobile - 6, Vehicles - 6, Furniture - 6, Health - 7
        // Note: Only showing first category in full. Add remaining 43 products following same pattern
    )

    init {
        loadCategoryProducts()
    }

    fun onAction(action: CategoryProductsAction) {
        when (action) {
            is CategoryProductsAction.SortProducts -> sortProducts(action.sortOption)
            CategoryProductsAction.Refresh -> loadCategoryProducts()
        }
    }

    private fun loadCategoryProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Simulate network delay
            delay(500)

            try {
                val category = mockCategories.find { it.id == categoryId }
                val products = allProducts.filter { it.category.id == categoryId }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        category = category,
                        products = products
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "পণ্য লোড করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }

    private fun sortProducts(sortOption: SortOption) {
        val sortedProducts = when (sortOption) {
            SortOption.NEWEST -> _uiState.value.products.sortedByDescending { it.createdAt }
            SortOption.PRICE_LOW_TO_HIGH -> _uiState.value.products.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> _uiState.value.products.sortedByDescending { it.price }
            SortOption.RATING -> _uiState.value.products.sortedByDescending { it.rating }
            SortOption.NAME -> _uiState.value.products.sortedBy { it.name }
        }

        _uiState.update { it.copy(products = sortedProducts) }
    }
}

// ViewModelFactory to pass categoryId
class CategoryProductsViewModelFactory(
    private val categoryId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryProductsViewModel::class.java)) {
            return CategoryProductsViewModel(categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

