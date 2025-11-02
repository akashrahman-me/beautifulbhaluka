package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchmakerDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MatchmakerDetailsUiState())
    val uiState: StateFlow<MatchmakerDetailsUiState> = _uiState.asStateFlow()

    fun loadMatchmaker(matchmakerId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(800)

            // Mock data - replace with actual repository call
            val matchmaker = generateMockMatchmaker(matchmakerId)

            if (matchmaker != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        matchmaker = matchmaker
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Matchmaker not found"
                    )
                }
            }
        }
    }

    private fun generateMockMatchmaker(id: String): Matchmaker? {
        val mockMatchmakers = listOf(
            Matchmaker(
                id = "m1",
                name = "Abdul Karim",
                age = 55,
                experience = "20+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01711-123456",
                whatsapp = "01711-123456",
                profileImageUrl = "https://picsum.photos/seed/matchmaker1/400/400",
                bio = "Experienced matchmaker specializing in elite families. Successfully arranged 300+ marriages with high satisfaction rate. Dedicated to finding perfect matches based on compatibility, family values, and individual preferences.",
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
                        "Excellent service! Found perfect match for my daughter. Very professional and caring approach.",
                        5.0
                    ),
                    Testimonial(
                        "Fatima Begum",
                        "Very professional and respectful. Helped us find a wonderful match for our son.",
                        4.5
                    ),
                    Testimonial(
                        "Dr. Kamal Hassan",
                        "Outstanding matchmaker! His experience and network are invaluable.",
                        5.0
                    )
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
                profileImageUrl = "https://picsum.photos/seed/matchmaker2/400/400",
                bio = "Female matchmaker with expertise in professional matches. Understanding and compassionate approach. I believe in building long-term relationships with families and ensuring compatibility.",
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
                consultationFee = "৳2000",
                testimonials = listOf(
                    Testimonial(
                        "Sharif Ahmed",
                        "Very helpful and understanding. Highly recommend!",
                        4.5
                    ),
                    Testimonial("Nasrin Akter", "Professional service with personal touch.", 5.0)
                )
            ),
            Matchmaker(
                id = "m3",
                name = "Maulana Sayed Ali",
                age = 62,
                experience = "25+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01913-345678",
                profileImageUrl = "https://picsum.photos/seed/matchmaker3/400/400",
                bio = "Traditional matchmaker with deep community roots. Specializes in religious families and overseas matches. With 25 years of experience, I have built a strong network across Bangladesh and abroad.",
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
                consultationFee = "Free Consultation",
                testimonials = listOf(
                    Testimonial(
                        "Abdullah Rahman",
                        "Most trusted matchmaker in the area. Helped many families.",
                        5.0
                    ),
                    Testimonial("Zainab Khatun", "Excellent guidance throughout the process.", 4.8)
                )
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
                profileImageUrl = "https://picsum.photos/seed/matchmaker4/400/400",
                bio = "Modern approach to traditional matchmaking. Specializes in educated professionals and second marriages. I understand the unique needs of modern families while respecting traditional values.",
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
                ),
                testimonials = listOf(
                    Testimonial(
                        "Imran Khan",
                        "Very professional and modern approach. Highly satisfied!",
                        4.7
                    )
                )
            ),
            Matchmaker(
                id = "m5",
                name = "Hafez Abdur Rahman",
                age = 58,
                experience = "18+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01816-567890",
                profileImageUrl = "https://picsum.photos/seed/matchmaker5/400/400",
                bio = "Islamic scholar and matchmaker. Focus on compatibility and religious values. My goal is to help families build strong, faith-based marriages that last a lifetime.",
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
                consultationFee = "Free Consultation",
                testimonials = listOf(
                    Testimonial(
                        "Mahmud Hasan",
                        "Excellent Islamic guidance. Very trustworthy.",
                        5.0
                    )
                )
            ),
            Matchmaker(
                id = "m6",
                name = "Shahida Begum",
                age = 50,
                experience = "16+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01917-678901",
                whatsapp = "01917-678901",
                profileImageUrl = "https://picsum.photos/seed/matchmaker6/400/400",
                bio = "Dedicated to helping families find suitable matches. Patient and understanding approach. I take time to understand each family's unique needs and preferences.",
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
                consultationFee = "৳1500",
                testimonials = listOf(
                    Testimonial(
                        "Rafiq Uddin",
                        "Very patient and helpful throughout the process.",
                        4.5
                    )
                )
            )
        )

        return mockMatchmakers.find { it.id == id }
    }
}

