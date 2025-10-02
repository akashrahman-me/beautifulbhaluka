package com.akash.beautifulbhaluka.presentation.screens.craftsman

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

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

enum class CraftsmanType(
    val displayName: String,
    val value: String,
    val icon: ImageVector
) {
    ALL("সব", "", Icons.Default.Build),
    ELECTRICIAN("ইলেকট্রিক মিস্ত্রি", "ইলেকট্রিক মিস্ত্রি", Icons.Default.ElectricBolt),
    MASON("রাজমিস্ত্রী", "রাজমিস্ত্রী", Icons.Default.Construction),
    CARPENTER("কাঠমিস্ত্রী", "কাঠমিস্ত্রী", Icons.Default.Carpenter),
    PAINTER("রং মিস্ত্রি", "রং মিস্ত্রি", Icons.Default.Palette),
    PLUMBER("স্যানিটারি মিস্ত্রি", "স্যানিটারি মিস্ত্রি", Icons.Default.Plumbing),
    TILES("টাইলস মিস্ত্রি", "টাইলস মিস্ত্রি", Icons.Default.GridView),
    AC_FRIDGE("এসি/ফ্রিজ মিস্ত্রি", "এসি মিস্ত্রি", Icons.Default.AcUnit),
    CCTV_COMPUTER("সিসি ক্যামেরা/কম্পিউটার", "সিসি ক্যামেরা মিস্ত্রি", Icons.Default.Videocam),
    GLASS("থাই গ্লাস মিস্ত্রি", "থাই গ্লাস মিস্ত্রি", Icons.Default.Window),
    INTERIOR("ইন্টেরিয়র ডিজাইন", "ইন্টেরিয়র ডিজাইন", Icons.Default.DesignServices),
    TV("টিভি মিস্ত্রি", "টিভি মিস্ত্রি", Icons.Default.Tv),
    VEHICLE("গাড়ির মিস্ত্রি", "গাড়ির মিস্ত্রি", Icons.Default.CarRepair),
    OTHER("অন্যান্য", "other", Icons.Default.Handyman)
}

sealed interface CraftsmanAction {
    object LoadData : CraftsmanAction
    data class SearchQueryChanged(val query: String) : CraftsmanAction
    data class TypeFilterChanged(val type: CraftsmanType) : CraftsmanAction
    data class SearchExpandedChanged(val expanded: Boolean) : CraftsmanAction
    data class CallNumber(val number: String) : CraftsmanAction
}
