package com.akash.beautifulbhaluka.presentation.screens.craftsman

data class CraftsmanUiState(
    val isLoading: Boolean = false,
    val craftsmen: List<Craftsman> = emptyList(),
    val filteredCraftsmen: List<Craftsman> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val selectedType: CraftsmanType = CraftsmanType.ALL,
    val isSearchExpanded: Boolean = false
)

data class Craftsman(
    val name: String,
    val number: String,
    val type: String,
    val address: String
)

enum class CraftsmanType(val displayName: String, val value: String) {
    ALL("সব", ""),
    ELECTRICIAN("ইলেকট্রিক মিস্ত্রি", "ইলেকট্রিক মিস্ত্রি"),
    MASON("রাজমিস্ত্রী", "রাজমিস্ত্রী"),
    CARPENTER("কাঠমিস্ত্রী", "কাঠমিস্ত্রী"),
    PAINTER("রং মিস্ত্রি", "রং মিস্ত্রি"),
    PLUMBER("স্যানিটারি মিস্ত্রি", "স্যানিটারি মিস্ত্রি"),
    TILES("টাইলস মিস্ত্রি", "টাইলস মিস্ত্রি"),
    AC_FRIDGE("এসি/ফ্রিজ মিস্ত্রি", "এসি মিস্ত্রি"),
    CCTV_COMPUTER("সিসি ক্যামেরা/কম্পিউটার", "সিসি ক্যামেরা মিস্ত্রি"),
    GLASS("থাই গ্লাস মিস্ত্রি", "থাই গ্লাস মিস্ত্রি"),
    INTERIOR("ইন্টেরিয়র ডিজাইন", "ইন্টেরিয়র ডিজাইন"),
    TV("টিভি মিস্ত্রি", "টিভি মিস্ত্রি"),
    VEHICLE("গাড়ির মিস্ত্রি", "গাড়ির মিস্ত্রি"),
    OTHER("অন্যান্য", "other")
}

sealed interface CraftsmanAction {
    object LoadData : CraftsmanAction
    data class SearchQueryChanged(val query: String) : CraftsmanAction
    data class TypeFilterChanged(val type: CraftsmanType) : CraftsmanAction
    data class SearchExpandedChanged(val expanded: Boolean) : CraftsmanAction
    data class CallNumber(val number: String) : CraftsmanAction
}
