package com.akash.beautifulbhaluka.presentation.screens.houserent

import com.akash.beautifulbhaluka.domain.model.HouseRent
import com.akash.beautifulbhaluka.domain.model.PropertyType
import com.akash.beautifulbhaluka.domain.model.RentCategory
import com.akash.beautifulbhaluka.domain.model.RentSortOption
import com.akash.beautifulbhaluka.domain.model.PriceRange

data class HouseRentUiState(
    val isLoading: Boolean = false,
    val properties: List<HouseRent> = emptyList(),
    val categories: List<RentCategory> = emptyList(),
    val selectedCategory: RentCategory? = null,
    val selectedPropertyType: PropertyType? = null,
    val searchQuery: String = "",
    val filteredProperties: List<HouseRent> = emptyList(),
    val priceRange: PriceRange = PriceRange(),
    val selectedBedrooms: Int? = null,
    val showAvailableOnly: Boolean = true,
    val sortOption: RentSortOption = RentSortOption.NEWEST,
    val error: String? = null
)

sealed class HouseRentAction {
    data class SearchProperties(val query: String) : HouseRentAction()
    data class SelectCategory(val category: RentCategory?) : HouseRentAction()
    data class SelectPropertyType(val type: PropertyType?) : HouseRentAction()
    data class FilterByPrice(val range: PriceRange) : HouseRentAction()
    data class FilterByBedrooms(val bedrooms: Int?) : HouseRentAction()
    data class ToggleFavorite(val propertyId: String) : HouseRentAction()
    data class SortProperties(val sortOption: RentSortOption) : HouseRentAction()
    data class ToggleAvailability(val showAvailableOnly: Boolean) : HouseRentAction()
    object Refresh : HouseRentAction()
    object ClearFilters : HouseRentAction()
}

