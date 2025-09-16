package com.akash.beautifulbhaluka.presentation.components.layout

data class TopbarUiState(
    val searchQuery: String = "",
    val searchResults: List<SearchResult> = emptyList(),
    val isSearching: Boolean = false,
    val recentSearches: List<String> = emptyList(),
    val suggestions: List<SearchSuggestion> = emptyList()
)

data class SearchResult(
    val id: String,
    val title: String,
    val description: String,
    val category: SearchCategory,
    val route: String = ""
)

data class SearchSuggestion(
    val id: String,
    val text: String,
    val category: SearchCategory
)

enum class SearchCategory(val displayName: String) {
    SERVICES("সেবা"),
    PLACES("স্থান"),
    PEOPLE("ব্যক্তি"),
    ORGANIZATIONS("প্রতিষ্ঠান"),
    EMERGENCY("জরুরি"),
    HEALTHCARE("স্বাস্থ্য"),
    EDUCATION("শিক্ষা"),
    BUSINESS("ব্যবসা"),
    TOURISM("পর্যটন")
}

sealed class TopbarAction {
    data class SearchQueryChanged(val query: String) : TopbarAction()
    data class SearchExecuted(val query: String) : TopbarAction()
    data class SearchResultClicked(val result: SearchResult) : TopbarAction()
    data class SuggestionClicked(val suggestion: SearchSuggestion) : TopbarAction()
    object ClearSearch : TopbarAction()
    object LoadSuggestions : TopbarAction()
}
