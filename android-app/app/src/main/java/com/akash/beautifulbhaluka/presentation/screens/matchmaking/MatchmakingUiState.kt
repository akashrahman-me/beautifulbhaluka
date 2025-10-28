package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile
import com.akash.beautifulbhaluka.domain.model.ProfileCategory

data class MatchmakingUiState(
    val isLoading: Boolean = false,
    val profiles: List<MatchmakingProfile> = emptyList(),
    val filteredProfiles: List<MatchmakingProfile> = emptyList(),
    val selectedCategory: ProfileCategory = ProfileCategory.ALL,
    val searchQuery: String = "",
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val selectedGenderFilter: String = "All",
    val selectedAgeRange: IntRange = 18..50,
    val showFilters: Boolean = false
)

sealed class MatchmakingAction {
    object LoadProfiles : MatchmakingAction()
    object Refresh : MatchmakingAction()
    data class SelectCategory(val category: ProfileCategory) : MatchmakingAction()
    data class Search(val query: String) : MatchmakingAction()
    data class FilterByGender(val gender: String) : MatchmakingAction()
    data class FilterByAgeRange(val range: IntRange) : MatchmakingAction()
    object ToggleFilters : MatchmakingAction()
    object ClearFilters : MatchmakingAction()
}

