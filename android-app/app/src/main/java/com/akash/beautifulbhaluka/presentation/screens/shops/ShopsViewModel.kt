package com.akash.beautifulbhaluka.presentation.screens.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ShopsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ShopsUiState())
    val uiState: StateFlow<ShopsUiState> = _uiState.asStateFlow()

    private val mockCategories = listOf(
        ProductCategory(
            "1",
            "ইলেকট্রনিক্স",
            "Electronics",
            R.drawable.ic_launcher_foreground,
            productCount = 2
        ),
        ProductCategory(
            "2",
            "পোশাক",
            "Clothing",
            R.drawable.ic_launcher_foreground,
            productCount = 1
        ),
        ProductCategory("3", "খাবার", "Food", R.drawable.ic_launcher_foreground, productCount = 1),
        ProductCategory("4", "বই", "Books", R.drawable.ic_launcher_foreground, productCount = 1),
        ProductCategory(
            "5",
            "মোবাইল",
            "Mobile",
            R.drawable.ic_launcher_foreground,
            productCount = 1
        ),
        ProductCategory(
            "6",
            "গাড়ি",
            "Vehicles",
            R.drawable.ic_launcher_foreground,
            productCount = 1
        ),
        ProductCategory(
            "7",
            "আসবাবপত্র",
            "Furniture",
            R.drawable.ic_launcher_foreground,
            productCount = 1
        ),
        ProductCategory(
            "8",
            "স্বাস্থ্য",
            "Health",
            R.drawable.ic_launcher_foreground,
            productCount = 1
        )
    )

    private val mockProducts = listOf(
        Product(
            id = "1",
            name = "স্যামসাং গ্যালাক্সি A54",
            description = "একদম নতুন কন্ডিশনে স্যামসাং গ্যালাক্সি A54। সব ফিচার সহ।",
            price = 35000.0,
            originalPrice = 40000.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[4],
            stock = 5,
            sellerName = "মোঃ রহিম উদ্দিন",
            sellerContact = "+880 1711 123456",
            location = "ভালুকা, ময়মনসিংহ",
            rating = 4.5f,
            reviewCount = 23,
            isNew = true,
            isFeatured = true
        ),
        Product(
            id = "2",
            name = "হোন্ডা মোটরসাইকেল",
            description = "২০২২ মডেল হোন্ডা বাইক, খুবই ভালো কন্ডিশন।",
            price = 125000.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[5],
            stock = 1,
            sellerName = "আব্দুল করিম",
            sellerContact = "+880 1712 654321",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 8,
            isFeatured = true
        ),
        Product(
            id = "3",
            name = "কটন শার্ট",
            description = "১০০% কটন শার্ট, সব সাইজ পাওয়া যায়।",
            price = 1200.0,
            originalPrice = 1500.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[1],
            stock = 20,
            sellerName = "ফারুক টেইলার্স",
            sellerContact = "+880 1713 789012",
            location = "ভালুকা শহর",
            rating = 4.0f,
            reviewCount = 15
        ),
        Product(
            id = "4",
            name = "দেশি মুরগি",
            description = "সম্পূর্ণ দেশি মুরগি, তাজা এবং স্বাস্থ্যকর।",
            price = 500.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[2],
            stock = 10,
            sellerName = "কৃষক আব্দুল মজিদ",
            sellerContact = "+880 1714 345678",
            location = "গ্রাম: বালিয়া",
            rating = 4.8f,
            reviewCount = 32,
            isNew = true
        ),
        Product(
            id = "5",
            name = "ল্যাপটপ ব্যাগ",
            description = "উচ্চ মানের ল্যাপটপ ব্যাগ, সব সাইজের ল্যাপটপের জন্য।",
            price = 2500.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[0],
            stock = 8,
            sellerName = "টেক স্টোর",
            sellerContact = "+880 1715 901234",
            location = "ভালুকা কলেজ গেট",
            rating = 4.3f,
            reviewCount = 12
        ),
        Product(
            id = "6",
            name = "গল্পের বই সেট",
            description = "জনপ্রিয় লেখকদের গল্পের বইয়ের সেট।",
            price = 800.0,
            originalPrice = 1000.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[3],
            stock = 15,
            sellerName = "বুক কর্নার",
            sellerContact = "+880 1716 567890",
            location = "ভালুকা লাইব্রেরি",
            rating = 4.6f,
            reviewCount = 28
        ),
        Product(
            id = "7",
            name = "কাঠের টেবিল",
            description = "শক্ত কাঠের তৈরি পড়ার টেবিল, দীর্ঘস্থায়ী।",
            price = 8500.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[6],
            stock = 3,
            sellerName = "কারিগর আসবাবপত্র",
            sellerContact = "+880 1717 234567",
            location = "ভালুকা বাস স্ট্যান্ড",
            rating = 4.4f,
            reviewCount = 7
        ),
        Product(
            id = "8",
            name = "ভিটামিন সি ট্যাবলেট",
            description = "প্রাকৃতিক ভিটামিন সি ট্যাবলেট, রোগ প্রতিরোধ ক্ষমতা বৃদ্ধি করে।",
            price = 350.0,
            imageUrl = "https://via.placeholder.com/300",
            category = mockCategories[7],
            stock = 25,
            sellerName = "হেলথ ফার্মেসি",
            sellerContact = "+880 1718 890123",
            location = "ভালুকা হাসপাতাল রোড",
            rating = 4.7f,
            reviewCount = 45,
            isNew = true
        )
    )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Simulate network delay
            delay(1000)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                products = mockProducts,
                categories = mockCategories,
                filteredProducts = mockProducts
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        filterProducts()
    }

    fun onCategorySelected(category: ProductCategory?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        filterProducts()
    }

    private fun filterProducts() {
        val currentState = _uiState.value
        var filtered = currentState.products

        // Filter by category
        currentState.selectedCategory?.let { category ->
            filtered = filtered.filter { it.category.id == category.id }
        }

        // Filter by search query
        if (currentState.searchQuery.isNotBlank()) {
            filtered = filtered.filter { product ->
                product.name.contains(currentState.searchQuery, ignoreCase = true) ||
                        product.description.contains(currentState.searchQuery, ignoreCase = true) ||
                        product.sellerName.contains(currentState.searchQuery, ignoreCase = true)
            }
        }

        _uiState.value = _uiState.value.copy(filteredProducts = filtered)
    }

    fun sortProducts(sortOption: SortOption) {
        val currentProducts = _uiState.value.filteredProducts
        val sortedProducts = when (sortOption) {
            SortOption.NEWEST -> currentProducts.sortedByDescending { it.createdAt }
            SortOption.PRICE_LOW_TO_HIGH -> currentProducts.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> currentProducts.sortedByDescending { it.price }
            SortOption.RATING -> currentProducts.sortedByDescending { it.rating }
            SortOption.NAME -> currentProducts.sortedBy { it.name }
        }

        _uiState.value = _uiState.value.copy(filteredProducts = sortedProducts)
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(
            selectedCategory = null,
            searchQuery = "",
            filteredProducts = _uiState.value.products
        )
    }

    fun refreshProducts() {
        loadInitialData()
    }

    fun onAction(action: ShopsAction) {
        when (action) {
            is ShopsAction.SearchProducts -> onSearchQueryChanged(action.query)
            is ShopsAction.SelectCategory -> onCategorySelected(action.category)
            is ShopsAction.ToggleFavorite -> toggleFavorite(action.productId)
            is ShopsAction.SortProducts -> sortProducts(action.sortOption)
            ShopsAction.Refresh -> refreshProducts()
            ShopsAction.ClearFilters -> clearFilters()
        }
    }

    private fun toggleFavorite(productId: String) {
        // Implementation for toggling favorite
        // For now, just a placeholder as we don't have favorite functionality yet
    }
}
