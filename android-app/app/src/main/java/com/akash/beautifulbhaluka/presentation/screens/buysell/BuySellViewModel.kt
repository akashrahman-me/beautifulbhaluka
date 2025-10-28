package com.akash.beautifulbhaluka.presentation.screens.buysell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class BuySellViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BuySellUiState())
    val uiState: StateFlow<BuySellUiState> = _uiState.asStateFlow()

    private val mockCategories = listOf(
        BuySellCategory("1", "ইলেকট্রনিক্স", "Electronics", "Devices", 45, 0xFF2196F3),
        BuySellCategory("2", "মোবাইল ফোন", "Mobile Phones", "Smartphone", 89, 0xFF4CAF50),
        BuySellCategory("3", "গাড়ি", "Vehicles", "DirectionsCar", 23, 0xFFFF5722),
        BuySellCategory("4", "বাইক", "Motorbikes", "TwoWheeler", 67, 0xFF9C27B0),
        BuySellCategory("5", "আসবাবপত্র", "Furniture", "Chair", 34, 0xFFFF9800),
        BuySellCategory("6", "ফ্যাশন", "Fashion", "Checkroom", 78, 0xFFE91E63),
        BuySellCategory("7", "বাড়ি", "Property", "Home", 12, 0xFF00BCD4),
        BuySellCategory("8", "চাকরি", "Jobs", "Work", 56, 0xFF795548),
        BuySellCategory("9", "সেবা", "Services", "HomeRepairService", 43, 0xFF607D8B),
        BuySellCategory("10", "পোষা প্রাণী", "Pets", "Pets", 19, 0xFF8BC34A),
        BuySellCategory("11", "বই", "Books", "Book", 28, 0xFF3F51B5),
        BuySellCategory("12", "খেলাধুলা", "Sports", "SportsBasketball", 15, 0xFFFFC107)
    )

    private val mockItems = listOf(
        BuySellItem(
            id = "1",
            title = "Samsung Galaxy S23 Ultra (512GB)",
            description = "একদম নতুন কন্ডিশনে Samsung Galaxy S23 Ultra। সব এক্সেসরিজ সহ। বক্স, চার্জার, ইয়ারফোন সব আছে। মাত্র ৩ মাস ব্যবহার করা হয়েছে। কোন স্ক্র্যাচ নেই।",
            price = 95000.0,
            originalPrice = 125000.0,
            imageUrl = "https://picsum.photos/400/300?random=1",
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=1",
                "https://picsum.photos/400/300?random=11",
                "https://picsum.photos/400/300?random=12"
            ),
            category = mockCategories[1],
            condition = ItemCondition.LIKE_NEW,
            type = ItemType.SELL,
            sellerName = "মোহাম্মদ আলী",
            sellerContact = "+880 1711-123456",
            location = "ভালুকা বাজার, ময়মনসিংহ",
            rating = 4.8f,
            viewCount = 234,
            isFeatured = true,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 86400000L
        ),
        BuySellItem(
            id = "2",
            title = "Honda CB 150R (2022 Model)",
            description = "২০২২ মডেল Honda CB 150R। সম্পূর্ণ পেপার সহ। রেগুলার সার্ভিসিং করা। খুবই ভালো কন্ডিশন। ১৫,০০০ কিমি চলেছে।",
            price = 185000.0,
            imageUrl = "https://picsum.photos/400/300?random=2",
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=2",
                "https://picsum.photos/400/300?random=21"
            ),
            category = mockCategories[3],
            condition = ItemCondition.GOOD,
            type = ItemType.SELL,
            sellerName = "রহিম মিয়া",
            sellerContact = "+880 1812-345678",
            location = "ভালুকা উপজেলা",
            rating = 4.5f,
            viewCount = 567,
            isFeatured = true,
            isUrgent = true,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 172800000L
        ),
        BuySellItem(
            id = "3",
            title = "Sony 55\" 4K Smart TV",
            description = "Sony 55 inch 4K Smart Android TV। ২ বছর ব্যবহৃত। পারফেক্ট কন্ডিশন। কোন সমস্যা নেই।",
            price = 65000.0,
            originalPrice = 95000.0,
            imageUrl = "https://picsum.photos/400/300?random=3",
            category = mockCategories[0],
            condition = ItemCondition.GOOD,
            type = ItemType.SELL,
            sellerName = "করিম সাহেব",
            sellerContact = "+880 1913-456789",
            location = "কদমতলী, ভালুকা",
            rating = 4.2f,
            viewCount = 189,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 259200000L
        ),
        BuySellItem(
            id = "4",
            title = "Gaming Laptop - RTX 3060 ⚡",
            description = "ASUS ROG Strix Gaming Laptop। RTX 3060, i7 11th Gen, 16GB RAM, 512GB SSD। গেমিং এবং ভারী কাজের জন্য পারফেক্ট।",
            price = 125000.0,
            imageUrl = "https://picsum.photos/400/300?random=4",
            category = mockCategories[0],
            condition = ItemCondition.LIKE_NEW,
            type = ItemType.SELL,
            sellerName = "তানভীর আহমেদ",
            sellerContact = "+880 1714-567890",
            location = "ভালুকা সদর",
            rating = 4.9f,
            viewCount = 423,
            isFeatured = true,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 345600000L
        ),
        BuySellItem(
            id = "5",
            title = "আধুনিক সোফা সেট (৫ সিটার)",
            description = "নতুন কন্ডিশনে ৫ সিটার সোফা সেট। খুবই কম্ফোর্টেবল। হোম ডেলিভারি সুবিধা আছে।",
            price = 45000.0,
            imageUrl = "https://picsum.photos/400/300?random=5",
            category = mockCategories[4],
            condition = ItemCondition.NEW,
            type = ItemType.SELL,
            sellerName = "ফারহানা বেগম",
            sellerContact = "+880 1815-678901",
            location = "ভালুকা পৌরসভা",
            viewCount = 156,
            createdAt = System.currentTimeMillis() - 432000000L
        ),
        BuySellItem(
            id = "6",
            title = "iPhone 13 Pro কিনতে চাই",
            description = "ভালো কন্ডিশনে iPhone 13 Pro খুঁজছি। ৬৪GB বা ১২৮GB। সরাসরি ডিল করতে চাই।",
            price = 75000.0,
            imageUrl = "https://picsum.photos/400/300?random=6",
            category = mockCategories[1],
            condition = ItemCondition.GOOD,
            type = ItemType.BUY,
            sellerName = "নাহিদ হাসান",
            sellerContact = "+880 1916-789012",
            location = "ভালুকা",
            viewCount = 89,
            isUrgent = true,
            createdAt = System.currentTimeMillis() - 518400000L
        ),
        BuySellItem(
            id = "7",
            title = "Royal Enfield Classic 350",
            description = "২০২১ মডেল Royal Enfield। নতুন টায়ার। ফুল পেপার। খুবই ভালো কন্ডিশন।",
            price = 225000.0,
            imageUrl = "https://picsum.photos/400/300?random=7",
            category = mockCategories[3],
            condition = ItemCondition.GOOD,
            type = ItemType.SELL,
            sellerName = "সাইফুল ইসলাম",
            sellerContact = "+880 1717-890123",
            location = "ভালুকা বাজার",
            rating = 4.6f,
            viewCount = 345,
            isFeatured = true,
            isVerified = true,
            createdAt = System.currentTimeMillis() - 604800000L
        ),
        BuySellItem(
            id = "8",
            title = "ব্র্যান্ডেড জুতা (পুরুষ)",
            description = "Nike Air Max। সাইজ ৪২। নতুন। অরিজিনাল প্রোডাক্ট।",
            price = 3500.0,
            originalPrice = 5500.0,
            imageUrl = "https://picsum.photos/400/300?random=8",
            category = mockCategories[5],
            condition = ItemCondition.NEW,
            type = ItemType.SELL,
            sellerName = "রাজিব",
            sellerContact = "+880 1818-901234",
            location = "ভালুকা",
            viewCount = 67,
            createdAt = System.currentTimeMillis() - 691200000L
        )
    )

    init {
        loadItems()
    }

    fun onAction(action: BuySellAction) {
        when (action) {
            is BuySellAction.LoadItems -> loadItems()
            is BuySellAction.SelectCategory -> selectCategory(action.category)
            is BuySellAction.SelectFilter -> selectFilter(action.filter)
            is BuySellAction.UpdateSearchQuery -> updateSearchQuery(action.query)
            is BuySellAction.SelectSortOption -> selectSortOption(action.option)
            is BuySellAction.Refresh -> refresh()
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(500) // Simulate network delay
            _uiState.update {
                it.copy(
                    isLoading = false,
                    items = mockItems,
                    categories = mockCategories,
                    filteredItems = mockItems
                )
            }
            applyFilters()
        }
    }

    private fun selectCategory(category: BuySellCategory?) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFilters()
    }

    private fun selectFilter(filter: BuySellFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        applyFilters()
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    private fun selectSortOption(option: SortOption) {
        _uiState.update { it.copy(sortOption = option) }
        applyFilters()
    }

    private fun refresh() {
        loadItems()
    }

    private fun applyFilters() {
        val currentState = _uiState.value
        var filtered = currentState.items

        // Filter by category
        currentState.selectedCategory?.let { category ->
            filtered = filtered.filter { it.category.id == category.id }
        }

        // Filter by type/filter
        filtered = when (currentState.selectedFilter) {
            BuySellFilter.ALL -> filtered
            BuySellFilter.SELL -> filtered.filter { it.type == ItemType.SELL }
            BuySellFilter.BUY -> filtered.filter { it.type == ItemType.BUY }
            BuySellFilter.EXCHANGE -> filtered.filter { it.type == ItemType.EXCHANGE }
            BuySellFilter.FEATURED -> filtered.filter { it.isFeatured }
            BuySellFilter.URGENT -> filtered.filter { it.isUrgent }
        }

        // Filter by search query
        if (currentState.searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.title.contains(currentState.searchQuery, ignoreCase = true) ||
                        it.description.contains(currentState.searchQuery, ignoreCase = true) ||
                        it.location.contains(currentState.searchQuery, ignoreCase = true)
            }
        }

        // Sort
        filtered = when (currentState.sortOption) {
            SortOption.NEWEST -> filtered.sortedByDescending { it.createdAt }
            SortOption.PRICE_LOW -> filtered.sortedBy { it.price }
            SortOption.PRICE_HIGH -> filtered.sortedByDescending { it.price }
            SortOption.MOST_VIEWED -> filtered.sortedByDescending { it.viewCount }
        }

        _uiState.update { it.copy(filteredItems = filtered) }
    }
}

