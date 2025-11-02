package com.akash.beautifulbhaluka.presentation.screens.matchmaking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.*
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
        loadMatchmakers()
    }

    fun onAction(action: MatchmakingAction) {
        when (action) {
            is MatchmakingAction.LoadProfiles -> loadProfiles()
            is MatchmakingAction.LoadMatchmakers -> loadMatchmakers()
            is MatchmakingAction.Refresh -> refresh()
            is MatchmakingAction.SelectTab -> selectTab(action.tab)
            is MatchmakingAction.SelectCategory -> selectCategory(action.category)
            is MatchmakingAction.Search -> search(action.query)
            is MatchmakingAction.FilterByGender -> filterByGender(action.gender)
            is MatchmakingAction.FilterByAgeRange -> filterByAgeRange(action.range)
            is MatchmakingAction.FilterBySpecialization -> filterBySpecialization(action.specialization)
            is MatchmakingAction.ToggleFilters -> toggleFilters()
            is MatchmakingAction.ClearFilters -> clearFilters()
        }
    }

    private fun selectTab(tab: MatchmakingTab) {
        _uiState.update { it.copy(selectedTab = tab, searchQuery = "", showFilters = false) }
        if (tab == MatchmakingTab.MATCHMAKERS) {
            applyMatchmakerFilters()
        } else {
            applyFilters()
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

    private fun loadMatchmakers() {
        viewModelScope.launch {
            delay(800) // Simulate network delay

            val mockMatchmakers = generateMockMatchmakers()
            _uiState.update {
                it.copy(
                    matchmakers = mockMatchmakers,
                    filteredMatchmakers = mockMatchmakers
                )
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            delay(1000)
            if (_uiState.value.selectedTab == MatchmakingTab.PROFILES) {
                loadProfiles()
            } else {
                loadMatchmakers()
            }
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    private fun selectCategory(category: ProfileCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFilters()
    }

    private fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (_uiState.value.selectedTab == MatchmakingTab.PROFILES) {
            applyFilters()
        } else {
            applyMatchmakerFilters()
        }
    }

    private fun filterByGender(gender: String) {
        _uiState.update { it.copy(selectedGenderFilter = gender) }
        applyFilters()
    }

    private fun filterByAgeRange(range: IntRange) {
        _uiState.update { it.copy(selectedAgeRange = range) }
        applyFilters()
    }

    private fun filterBySpecialization(specialization: String) {
        _uiState.update { it.copy(selectedSpecialization = specialization) }
        applyMatchmakerFilters()
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
                selectedCategory = ProfileCategory.ALL,
                selectedSpecialization = "All"
            )
        }
        if (_uiState.value.selectedTab == MatchmakingTab.PROFILES) {
            applyFilters()
        } else {
            applyMatchmakerFilters()
        }
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

    private fun applyMatchmakerFilters() {
        val state = _uiState.value
        var filtered = state.matchmakers

        // Specialization filter
        if (state.selectedSpecialization != "All") {
            filtered = filtered.filter { matchmaker ->
                matchmaker.specialization.contains(state.selectedSpecialization)
            }
        }

        // Search filter
        if (state.searchQuery.isNotBlank()) {
            filtered = filtered.filter { matchmaker ->
                matchmaker.name.contains(state.searchQuery, ignoreCase = true) ||
                        matchmaker.location.contains(state.searchQuery, ignoreCase = true) ||
                        matchmaker.specialization.any {
                            it.contains(
                                state.searchQuery,
                                ignoreCase = true
                            )
                        }
            }
        }

        _uiState.update { it.copy(filteredMatchmakers = filtered) }
    }

    private fun generateMockMatchmakers(): List<Matchmaker> {
        return listOf(
            Matchmaker(
                id = "m1",
                name = "Abdul Karim",
                age = 55,
                experience = "20+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01711-123456",
                whatsapp = "01711-123456",
                bio = "Experienced matchmaker specializing in elite families. Successfully arranged 300+ marriages with high satisfaction rate.",
                specialization = listOf("Elite Families", "Doctors", "Engineers"),
                successfulMatches = 320,
                rating = 4.8,
                verified = true,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Background Verification",
                    "Meeting Arrangement",
                    "Family Counseling"
                ),
                languages = listOf("Bengali", "English", "Urdu"),
                workingHours = "9 AM - 8 PM",
                consultationFee = "Free Consultation",
                testimonials = listOf(
                    Testimonial(
                        "Rashid Ahmed",
                        "Excellent service! Found perfect match for my daughter.",
                        5.0
                    ),
                    Testimonial("Fatima Begum", "Very professional and respectful.", 4.5)
                )
            ),
            Matchmaker(
                id = "m2",
                name = "Rahima Khatun",
                age = 48,
                experience = "15+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01812-234567",
                whatsapp = "01812-234567",
                email = "rahima.matchmaker@email.com",
                bio = "Female matchmaker with expertise in professional matches. Understanding and compassionate approach.",
                specialization = listOf("Business", "Government Service", "Doctors"),
                successfulMatches = 180,
                rating = 4.6,
                verified = true,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Background Verification",
                    "Meeting Arrangement",
                    "Biodata Writing"
                ),
                languages = listOf("Bengali", "English"),
                workingHours = "10 AM - 6 PM",
                consultationFee = "৳2000"
            ),
            Matchmaker(
                id = "m3",
                name = "Maulana Sayed Ali",
                age = 62,
                experience = "25+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01913-345678",
                bio = "Traditional matchmaker with deep community roots. Specializes in religious families and overseas matches.",
                specialization = listOf("General", "Overseas", "Religious Families"),
                successfulMatches = 450,
                rating = 4.9,
                verified = true,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Meeting Arrangement",
                    "Marriage Negotiation",
                    "Post-Marriage Support"
                ),
                languages = listOf("Bengali", "English", "Arabic"),
                workingHours = "After Asr - 9 PM",
                consultationFee = "Free Consultation"
            ),
            Matchmaker(
                id = "m4",
                name = "Nasrin Akter",
                age = 42,
                experience = "12+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01715-456789",
                whatsapp = "01715-456789",
                email = "nasrin.ghotak@email.com",
                bio = "Modern approach to traditional matchmaking. Specializes in educated professionals and second marriages.",
                specialization = listOf("Engineers", "Divorced/Widowed", "Overseas"),
                successfulMatches = 150,
                rating = 4.7,
                verified = true,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Background Verification",
                    "Photography",
                    "Biodata Writing"
                ),
                languages = listOf("Bengali", "English"),
                workingHours = "2 PM - 8 PM",
                consultationFee = "৳3000",
                socialMedia = MatchmakerSocialMedia(
                    facebook = "fb.com/nasrin.matchmaker",
                    instagram = "@nasrin_matchmaker"
                )
            ),
            Matchmaker(
                id = "m5",
                name = "Hafez Abdur Rahman",
                age = 58,
                experience = "18+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01816-567890",
                bio = "Islamic scholar and matchmaker. Focus on compatibility and religious values.",
                specialization = listOf("General", "Religious Families", "Government Service"),
                successfulMatches = 280,
                rating = 4.8,
                verified = true,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Meeting Arrangement",
                    "Family Counseling",
                    "Marriage Negotiation"
                ),
                languages = listOf("Bengali", "Arabic"),
                workingHours = "4 PM - 9 PM",
                consultationFee = "Free Consultation"
            ),
            Matchmaker(
                id = "m6",
                name = "Shahida Begum",
                age = 50,
                experience = "16+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01917-678901",
                whatsapp = "01917-678901",
                bio = "Dedicated to helping families find suitable matches. Patient and understanding approach.",
                specialization = listOf("Business", "Doctors", "General"),
                successfulMatches = 200,
                rating = 4.5,
                verified = false,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Meeting Arrangement",
                    "Biodata Writing"
                ),
                languages = listOf("Bengali"),
                workingHours = "9 AM - 5 PM",
                consultationFee = "৳1500"
            )
        )
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
                preferences = MatchPreferences(
                    25..32,
                    "Graduate",
                    listOf("Mymensingh", "Dhaka"),
                    "5'6\" - 6'0\""
                )
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
                preferences = MatchPreferences(
                    23..28,
                    "Graduate",
                    listOf("Mymensingh"),
                    "5'2\" - 5'6\""
                )
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

