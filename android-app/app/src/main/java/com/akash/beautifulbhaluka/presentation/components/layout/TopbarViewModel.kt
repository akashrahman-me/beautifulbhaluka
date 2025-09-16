package com.akash.beautifulbhaluka.presentation.components.layout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class TopbarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TopbarUiState())
    val uiState: StateFlow<TopbarUiState> = _uiState.asStateFlow()

    init {
        loadSuggestions()
    }

    fun onAction(action: TopbarAction) {
        when (action) {
            is TopbarAction.SearchQueryChanged -> handleSearchQueryChanged(action.query)
            is TopbarAction.SearchExecuted -> handleSearchExecuted(action.query)
            is TopbarAction.SearchResultClicked -> handleSearchResultClicked(action.result)
            is TopbarAction.SuggestionClicked -> handleSuggestionClicked(action.suggestion)
            is TopbarAction.ClearSearch -> clearSearch()
            is TopbarAction.LoadSuggestions -> loadSuggestions()
        }
    }

    private fun handleSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        if (query.isNotBlank()) {
            performSearch(query)
        } else {
            _uiState.update { it.copy(searchResults = emptyList(), isSearching = false) }
        }
    }

    private fun handleSearchExecuted(query: String) {
        if (query.isNotBlank()) {
            addToRecentSearches(query)
            performSearch(query)
        }
    }

    private fun handleSearchResultClicked(result: SearchResult) {
        addToRecentSearches(result.title)
        // Navigate to result - this would be handled by parent screen
    }

    private fun handleSuggestionClicked(suggestion: SearchSuggestion) {
        _uiState.update { it.copy(searchQuery = suggestion.text) }
        performSearch(suggestion.text)
    }

    private fun clearSearch() {
        _uiState.update {
            it.copy(
                searchQuery = "",
                searchResults = emptyList(),
                isSearching = false
            )
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }

            // Simulate network delay
            delay(300)

            val results = searchMockData(query)

            _uiState.update {
                it.copy(
                    searchResults = results,
                    isSearching = false
                )
            }
        }
    }

    private fun addToRecentSearches(query: String) {
        _uiState.update { state ->
            val updatedRecent = (listOf(query) + state.recentSearches)
                .distinct()
                .take(10) // Keep only last 10 searches
            state.copy(recentSearches = updatedRecent)
        }
    }

    private fun loadSuggestions() {
        _uiState.update {
            it.copy(suggestions = getMockSuggestions())
        }
    }

    // Mock search implementation with realistic Beautiful Bhaluka data
    private fun searchMockData(query: String): List<SearchResult> {
        val allData = getMockSearchData()

        return allData.filter { item ->
            item.title.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true) ||
                    item.category.displayName.contains(query, ignoreCase = true)
        }.take(20) // Limit results for performance
    }

    // Production-ready mock data for Beautiful Bhaluka
    private fun getMockSearchData(): List<SearchResult> {
        return listOf(
            // Emergency Services
            SearchResult(
                "1",
                "ফায়ার সার্ভিস",
                "আগুন নিভানোর জরুরি সেবা",
                SearchCategory.EMERGENCY,
                "emergency/fire"
            ),
            SearchResult(
                "2",
                "থানা পুলিশ",
                "ভালুকা থানা পুলিশ স্টেশন",
                SearchCategory.EMERGENCY,
                "emergency/police"
            ),
            SearchResult(
                "3",
                "উপজেলা প্রশাসন",
                "ভালুকা উপজেলা প্রশাসনিক অফিস",
                SearchCategory.EMERGENCY,
                "admin/upazilla"
            ),

            // Healthcare
            SearchResult(
                "4",
                "ডাক্তার",
                "স্থানীয় চিকিৎসক তালিকা",
                SearchCategory.HEALTHCARE,
                "healthcare/doctors"
            ),
            SearchResult(
                "5",
                "এ্যাম্বুলেন্স",
                "জরুরি অ্যাম্বুলেন্স সেবা",
                SearchCategory.HEALTHCARE,
                "healthcare/ambulance"
            ),
            SearchResult(
                "6",
                "হাসপাতাল",
                "ভালুকা উপজেলা স্বাস্থ্য কমপ্লেক্স",
                SearchCategory.HEALTHCARE,
                "healthcare/hospital"
            ),
            SearchResult(
                "7",
                "ফার্মেসি",
                "ঔষধের দোকান",
                SearchCategory.HEALTHCARE,
                "healthcare/pharmacy"
            ),

            // Education
            SearchResult(
                "8",
                "স্কুল কলেজ",
                "শিক্ষা প্রতিষ্ঠানের তালিকা",
                SearchCategory.EDUCATION,
                "education/schools"
            ),
            SearchResult(
                "9",
                "টিউশন",
                "গৃহশিক্ষক খোঁজা",
                SearchCategory.EDUCATION,
                "education/tuition"
            ),
            SearchResult(
                "10",
                "মাদ্রাসা",
                "ধর্মীয় শিক্ষা প্রতিষ্ঠান",
                SearchCategory.EDUCATION,
                "education/madrasah"
            ),

            // Business & Services
            SearchResult(
                "11",
                "ব্যাংক",
                "স্থানীয় ব্যাংক শাখা",
                SearchCategory.BUSINESS,
                "business/banks"
            ),
            SearchResult(
                "12",
                "কুরিয়ার সার্ভিস",
                "পার্সেল ও কুরিয়ার",
                SearchCategory.BUSINESS,
                "business/courier"
            ),
            SearchResult(
                "13",
                "গাড়ি ভাড়া",
                "যানবাহন ভাড়া সেবা",
                SearchCategory.BUSINESS,
                "business/transport"
            ),
            SearchResult(
                "14",
                "রেস্টুরেন্ট",
                "খাবারের দোকান",
                SearchCategory.BUSINESS,
                "business/restaurants"
            ),

            // Tourism & Places
            SearchResult(
                "15",
                "দর্শনীয় স্থান",
                "পর্যটন এলাকা",
                SearchCategory.TOURISM,
                "tourism/attractions"
            ),
            SearchResult(
                "16",
                "আবাসিক হোটেল",
                "থাকার জায়গা",
                SearchCategory.TOURISM,
                "tourism/hotels"
            ),
            SearchResult(
                "17",
                "হাট বাজার",
                "স্থানীয় বাজার",
                SearchCategory.PLACES,
                "places/markets"
            ),

            // People & Organizations
            SearchResult(
                "18",
                "সাংবাদিক",
                "স্থানীয় সংবাদকর্মী",
                SearchCategory.PEOPLE,
                "people/journalists"
            ),
            SearchResult(
                "19",
                "আইনজীবী",
                "আইনি পরামর্শদাতা",
                SearchCategory.PEOPLE,
                "people/lawyers"
            ),
            SearchResult(
                "20",
                "প্রসিদ্ধ ব্যক্তি",
                "এলাকার বিশিষ্ট ব্যক্তিত্ব",
                SearchCategory.PEOPLE,
                "people/notable"
            ),
            SearchResult(
                "21",
                "কৃতি সন্তান",
                "ভালুকার গর্বিত সন্তান",
                SearchCategory.PEOPLE,
                "people/achievers"
            ),

            // Services
            SearchResult(
                "22",
                "ব্রডব্যান্ড সার্ভিস",
                "ইন্টারনেট সেবা",
                SearchCategory.SERVICES,
                "services/internet"
            ),
            SearchResult(
                "23",
                "বিদ্যুৎ",
                "বিদ্যুৎ বিভাগ",
                SearchCategory.SERVICES,
                "services/electricity"
            ),
            SearchResult(
                "24",
                "পরিচ্ছন্নতা কর্মী",
                "পরিষ্কার-পরিচ্ছন্নতা",
                SearchCategory.SERVICES,
                "services/cleaning"
            ),
            SearchResult("25", "জিম", "ফিটনেস সেন্টার", SearchCategory.SERVICES, "services/gym"),
            SearchResult(
                "26",
                "লেডিস পার্লার",
                "নারীদের সৌন্দর্য কেন্দ্র",
                SearchCategory.SERVICES,
                "services/beauty"
            ),
            SearchResult(
                "27",
                "জেন্টস পার্লার",
                "পুরুষদের সৌন্দর্য কেন্দ্র",
                SearchCategory.SERVICES,
                "services/barber"
            ),

            // Government & Official
            SearchResult(
                "28",
                "উপজেলা",
                "উপজেলা প্রশাসন",
                SearchCategory.ORGANIZATIONS,
                "gov/upazilla"
            ),
            SearchResult(
                "29",
                "পৌরসভা",
                "পৌর কর্পোরেশন",
                SearchCategory.ORGANIZATIONS,
                "gov/municipality"
            ),
            SearchResult(
                "30",
                "ইউনিয়ন",
                "ইউনিয়ন পরিষদ",
                SearchCategory.ORGANIZATIONS,
                "gov/union"
            ),
            SearchResult(
                "31",
                "কাজী অফিস",
                "বিবাহ নিবন্ধন",
                SearchCategory.ORGANIZATIONS,
                "gov/kazi"
            ),
            SearchResult("32", "অফিস", "সরকারি অফিস", SearchCategory.ORGANIZATIONS, "gov/offices"),

            // Special Services
            SearchResult(
                "33",
                "ব্লাড ব্যাংক",
                "রক্তদান কেন্দ্র",
                SearchCategory.HEALTHCARE,
                "healthcare/blood"
            ),
            SearchResult(
                "34",
                "ক্রয়বিক্রয়",
                "কেনাবেচা",
                SearchCategory.BUSINESS,
                "business/trading"
            ),
            SearchResult("35", "চাকরি", "কর্মসংস্থান", SearchCategory.BUSINESS, "jobs/listings"),
            SearchResult(
                "36",
                "মুক্তিযোদ্ধার তালিকা",
                "বীর মুক্তিযোদ্ধা",
                SearchCategory.PEOPLE,
                "people/freedom-fighters"
            ),
            SearchResult(
                "37",
                "ভোটার তালিকা",
                "নির্বাচনী তালিকা",
                SearchCategory.ORGANIZATIONS,
                "gov/voters"
            ),
            SearchResult(
                "38",
                "প্রেস ও গ্রাফিক্স",
                "ডিজাইন ও প্রিন্টিং",
                SearchCategory.BUSINESS,
                "business/graphics"
            ),
            SearchResult(
                "39",
                "সকল মিস্ত্রি",
                "কারিগরি সেবা",
                SearchCategory.SERVICES,
                "services/craftsmen"
            ),
            SearchResult(
                "40",
                "এয়ার ট্রাভেল",
                "বিমান টিকিট",
                SearchCategory.SERVICES,
                "services/travel"
            )
        )
    }

    private fun getMockSuggestions(): List<SearchSuggestion> {
        return listOf(
            SearchSuggestion("s1", "জরুরি সেবা", SearchCategory.EMERGENCY),
            SearchSuggestion("s2", "ডাক্তার", SearchCategory.HEALTHCARE),
            SearchSuggestion("s3", "স্কুল", SearchCategory.EDUCATION),
            SearchSuggestion("s4", "ব্যাংক", SearchCategory.BUSINESS),
            SearchSuggestion("s5", "হোটেল", SearchCategory.TOURISM),
            SearchSuggestion("s6", "রেস্টুরেন্ট", SearchCategory.BUSINESS),
            SearchSuggestion("s7", "পুলিশ", SearchCategory.EMERGENCY),
            SearchSuggestion("s8", "হাসপাতাল", SearchCategory.HEALTHCARE),
            SearchSuggestion("s9", "দর্শনীয় স্থান", SearchCategory.TOURISM),
            SearchSuggestion("s10", "চাকরি", SearchCategory.BUSINESS)
        )
    }
}
