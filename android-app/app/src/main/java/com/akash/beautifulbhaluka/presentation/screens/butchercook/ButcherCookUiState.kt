package com.akash.beautifulbhaluka.presentation.screens.butchercook

data class ButcherCookUiState(
    val isLoading: Boolean = false,
    val butcherCooks: List<ButcherCook> = emptyList(),
    val filteredButcherCooks: List<ButcherCook> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val selectedType: ButcherCookType = ButcherCookType.ALL,
    val isSearchExpanded: Boolean = false
)

data class ButcherCook(
    val emoji: String,
    val name: String,
    val number: String,
    val address: String,
    val type: String
)

enum class ButcherCookType(val displayName: String, val value: String) {
    ALL("সব", ""),
    BUTCHER("কসাই", "কসাই"),
    COOK("বাবুর্চি", "বাবুর্চি")
}

sealed interface ButcherCookAction {
    object LoadData : ButcherCookAction
    data class SearchQueryChanged(val query: String) : ButcherCookAction
    data class TypeFilterChanged(val type: ButcherCookType) : ButcherCookAction
    data class SearchExpandedChanged(val expanded: Boolean) : ButcherCookAction
    data class CallNumber(val number: String) : ButcherCookAction
    object NavigateToPublish : ButcherCookAction
}
