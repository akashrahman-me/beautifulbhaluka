package com.akash.beautifulbhaluka.presentation.screens.buysell

data class BuySellUiState(
    val isLoading: Boolean = false,
    val items: List<BuySellItem> = emptyList(),
    val categories: List<BuySellCategory> = emptyList(),
    val selectedCategory: BuySellCategory? = null,
    val selectedFilter: BuySellFilter = BuySellFilter.ALL,
    val searchQuery: String = "",
    val filteredItems: List<BuySellItem> = emptyList(),
    val sortOption: SortOption = SortOption.NEWEST,
    val error: String? = null
)

data class BuySellItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val currency: String = "৳",
    val imageUrl: String,
    val imageUrls: List<String> = listOf(),
    val category: BuySellCategory,
    val condition: ItemCondition,
    val type: ItemType,
    val sellerName: String,
    val sellerAvatar: String? = null,
    val sellerContact: String,
    val location: String,
    val rating: Float = 0f,
    val viewCount: Int = 0,
    val isFeatured: Boolean = false,
    val isUrgent: Boolean = false,
    val isVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long? = null
)

data class BuySellCategory(
    val id: String,
    val name: String,
    val nameEn: String,
    val icon: String, // Material icon name
    val itemCount: Int = 0,
    val color: Long = 0xFF6200EE
)

enum class ItemCondition(val displayName: String, val displayNameEn: String) {
    NEW("নতুন", "New"),
    LIKE_NEW("প্রায় নতুন", "Like New"),
    GOOD("ভালো", "Good"),
    FAIR("মোটামুটি", "Fair"),
    USED("ব্যবহৃত", "Used")
}

enum class ItemType(val displayName: String, val displayNameEn: String) {
    SELL("বিক্রয়", "For Sale"),
    BUY("ক্রয়", "Wanted"),
    EXCHANGE("বিনিময়", "Exchange")
}

enum class BuySellFilter(val displayName: String) {
    ALL("সব"),
    SELL("বিক্রয়"),
    BUY("ক্রয়"),
    EXCHANGE("বিনিময়"),
    FEATURED("ফিচারড"),
    URGENT("জরুরী")
}

enum class SortOption(val displayName: String) {
    NEWEST("নতুন আগে"),
    PRICE_LOW("দাম কম থেকে বেশি"),
    PRICE_HIGH("দাম বেশি থেকে কম"),
    MOST_VIEWED("জনপ্রিয়")
}

sealed class BuySellAction {
    object LoadItems : BuySellAction()
    data class SelectCategory(val category: BuySellCategory?) : BuySellAction()
    data class SelectFilter(val filter: BuySellFilter) : BuySellAction()
    data class UpdateSearchQuery(val query: String) : BuySellAction()
    data class SelectSortOption(val option: SortOption) : BuySellAction()
    object Refresh : BuySellAction()
}

