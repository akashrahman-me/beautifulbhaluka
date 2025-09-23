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

    fun onAction(action: ShopsAction) {
        when (action) {
            is ShopsAction.SearchProducts -> {
                _uiState.value = _uiState.value.copy(
                    searchQuery = action.query,
                    filteredProducts = filterProducts(action.query, _uiState.value.selectedCategory)
                )
            }

            is ShopsAction.SelectCategory -> {
                _uiState.value = _uiState.value.copy(
                    selectedCategory = action.category,
                    filteredProducts = filterProducts(_uiState.value.searchQuery, action.category)
                )
            }

            is ShopsAction.ToggleFavorite -> {
                // Handle favorite toggle - could update local storage or backend
                // For now, just a placeholder
            }

            is ShopsAction.SortProducts -> {
                // Handle product sorting
                val sortedProducts = when (action.sortOption) {
                    SortOption.PRICE_LOW_TO_HIGH -> _uiState.value.filteredProducts.sortedBy { it.price }
                    SortOption.PRICE_HIGH_TO_LOW -> _uiState.value.filteredProducts.sortedByDescending { it.price }
                    SortOption.NAME -> _uiState.value.filteredProducts.sortedBy { it.name }
                    SortOption.NEWEST -> _uiState.value.filteredProducts.sortedByDescending { it.createdAt }
                    SortOption.RATING -> _uiState.value.filteredProducts.sortedByDescending { it.rating }
                }
                _uiState.value = _uiState.value.copy(filteredProducts = sortedProducts)
            }

            ShopsAction.Refresh -> {
                loadInitialData()
            }

            ShopsAction.ClearFilters -> {
                _uiState.value = _uiState.value.copy(
                    selectedCategory = null,
                    searchQuery = "",
                    filteredProducts = _uiState.value.products
                )
            }
        }
    }

    private fun filterProducts(
        searchQuery: String,
        selectedCategory: ProductCategory?
    ): List<Product> {
        var filtered = _uiState.value.products

        // Filter by category
        if (selectedCategory != null) {
            filtered = filtered.filter { it.category.id == selectedCategory.id }
        }

        // Filter by search query
        if (searchQuery.isNotEmpty()) {
            filtered = filtered.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true) ||
                        it.location.contains(searchQuery, ignoreCase = true)
            }
        }

        return filtered
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
}
