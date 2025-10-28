package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.FamilyDetails
import com.akash.beautifulbhaluka.domain.model.MatchPreferences
import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile
import com.akash.beautifulbhaluka.domain.model.ProfileCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchmakingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MatchmakingUiState())
    val uiState: StateFlow<MatchmakingUiState> = _uiState.asStateFlow()

    init {
        loadProfiles()
    }

    fun onAction(action: MatchmakingAction) {
        when (action) {
            is MatchmakingAction.LoadProfiles -> loadProfiles()
            is MatchmakingAction.Refresh -> refresh()
            is MatchmakingAction.SelectCategory -> selectCategory(action.category)
            is MatchmakingAction.Search -> search(action.query)
            is MatchmakingAction.FilterByGender -> filterByGender(action.gender)
            is MatchmakingAction.FilterByAgeRange -> filterByAgeRange(action.range)
            is MatchmakingAction.ToggleFilters -> toggleFilters()
            is MatchmakingAction.ClearFilters -> clearFilters()
        }
    }

    private fun loadProfiles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1000) // Simulate network delay

            val mockProfiles = generateMockProfiles()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    profiles = mockProfiles,
                    filteredProfiles = mockProfiles
                )
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            delay(1000)
            loadProfiles()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    private fun selectCategory(category: ProfileCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFilters()
    }

    private fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    private fun filterByGender(gender: String) {
        _uiState.update { it.copy(selectedGenderFilter = gender) }
        applyFilters()
    }

    private fun filterByAgeRange(range: IntRange) {
        _uiState.update { it.copy(selectedAgeRange = range) }
        applyFilters()
    }

    private fun toggleFilters() {
        _uiState.update { it.copy(showFilters = !it.showFilters) }
    }

    private fun clearFilters() {
        _uiState.update {
            it.copy(
                selectedGenderFilter = "All",
                selectedAgeRange = 18..50,
                searchQuery = "",
                selectedCategory = ProfileCategory.ALL
            )
        }
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        var filtered = state.profiles

        // Category filter
        filtered = when (state.selectedCategory) {
            ProfileCategory.ALL -> filtered
            ProfileCategory.VERIFIED -> filtered.filter { it.verified }
            ProfileCategory.RECENT -> filtered.sortedByDescending { it.createdAt }.take(10)
            ProfileCategory.PREMIUM -> filtered.filter { it.verified }
        }

        // Gender filter
        if (state.selectedGenderFilter != "All") {
            filtered = filtered.filter { it.gender == state.selectedGenderFilter }
        }

        // Age filter
        filtered = filtered.filter { it.age in state.selectedAgeRange }

        // Search filter
        if (state.searchQuery.isNotBlank()) {
            filtered = filtered.filter { profile ->
                profile.name.contains(state.searchQuery, ignoreCase = true) ||
                profile.occupation.contains(state.searchQuery, ignoreCase = true) ||
                profile.location.contains(state.searchQuery, ignoreCase = true)
            }
        }

        _uiState.update { it.copy(filteredProfiles = filtered) }
    }

    private fun generateMockProfiles(): List<MatchmakingProfile> {
        return listOf(
            MatchmakingProfile(
                id = "1",
                name = "Fatima Rahman",
                age = 26,
                gender = "Female",
                occupation = "Software Engineer",
                education = "B.Sc in CSE, BUET",
                location = "Bhaluka, Mymensingh",
                height = "5'4\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Looking for a life partner who values education and family.",
                interests = listOf("Reading", "Traveling", "Cooking"),
                verified = true,
                familyDetails = FamilyDetails("Teacher", "Homemaker", 2, "Nuclear"),
                preferences = MatchPreferences(25..32, "Graduate", listOf("Mymensingh", "Dhaka"), "5'6\" - 6'0\"")
            ),
            MatchmakingProfile(
                id = "2",
                name = "Ahmed Hassan",
                age = 29,
                gender = "Male",
                occupation = "Doctor (MBBS)",
                education = "MBBS, DMC",
                location = "Bhaluka, Mymensingh",
                height = "5'10\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Practicing doctor seeking educated and family-oriented partner.",
                interests = listOf("Sports", "Music", "Social Work"),
                verified = true,
                familyDetails = FamilyDetails("Business", "Teacher", 1, "Joint"),
                preferences = MatchPreferences(23..28, "Graduate", listOf("Mymensingh"), "5'2\" - 5'6\"")
            ),
            MatchmakingProfile(
                id = "3",
                name = "Nusrat Jahan",
                age = 24,
                gender = "Female",
                occupation = "Teacher",
                education = "Masters in English, DU",
                location = "Bhaluka, Mymensingh",
                height = "5'3\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Simple person with strong family values.",
                interests = listOf("Teaching", "Poetry", "Gardening"),
                verified = false,
                familyDetails = FamilyDetails("Farmer", "Homemaker", 3, "Joint")
            ),
            MatchmakingProfile(
                id = "4",
                name = "Kamal Uddin",
                age = 31,
                gender = "Male",
                occupation = "Banker",
                education = "MBA, NSU",
                location = "Bhaluka, Mymensingh",
                height = "5'8\"",
                religion = "Islam",
                maritalStatus = "Divorced",
                bio = "Looking for a second chance at happiness.",
                interests = listOf("Finance", "Cricket", "Photography"),
                verified = true
            ),
            MatchmakingProfile(
                id = "5",
                name = "Sadia Akter",
                age = 27,
                gender = "Female",
                occupation = "Architect",
                education = "B.Arch, CUET",
                location = "Bhaluka, Mymensingh",
                height = "5'5\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Creative mind seeking understanding partner.",
                interests = listOf("Design", "Art", "Travel"),
                verified = true
            ),
            MatchmakingProfile(
                id = "6",
                name = "Rafiq Ahmed",
                age = 33,
                gender = "Male",
                occupation = "Business Owner",
                education = "BBA, IBA",
                location = "Bhaluka, Mymensingh",
                height = "5'11\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Successful entrepreneur looking for life partner.",
                interests = listOf("Business", "Cars", "Technology"),
                verified = true
            ),
            MatchmakingProfile(
                id = "7",
                name = "Ayesha Siddique",
                age = 25,
                gender = "Female",
                occupation = "Pharmacist",
                education = "B.Pharm",
                location = "Bhaluka, Mymensingh",
                height = "5'2\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Healthcare professional with caring nature.",
                interests = listOf("Healthcare", "Yoga", "Volunteering"),
                verified = false
            ),
            MatchmakingProfile(
                id = "8",
                name = "Imran Khan",
                age = 28,
                gender = "Male",
                occupation = "Civil Engineer",
                education = "B.Sc in Civil, RUET",
                location = "Bhaluka, Mymensingh",
                height = "5'9\"",
                religion = "Islam",
                maritalStatus = "Never Married",
                bio = "Engineer with traditional values.",
                interests = listOf("Construction", "Swimming", "Movies"),
                verified = true
            )
        )
    }
}

