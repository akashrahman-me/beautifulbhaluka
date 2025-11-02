package com.akash.beautifulbhaluka.domain.model

data class Matchmaker(
    val id: String,
    val name: String,
    val age: Int,
    val experience: String, // e.g., "10+ years"
    val location: String,
    val contactNumber: String,
    val whatsapp: String? = null,
    val email: String? = null,
    val profileImageUrl: String? = null,
    val bio: String,
    val specialization: List<String>, // e.g., ["Elite Families", "Doctors", "Engineers"]
    val successfulMatches: Int = 0,
    val rating: Double = 0.0,
    val verified: Boolean = false,
    val available: Boolean = true,
    val servicesOffered: List<String>, // e.g., ["Profile Creation", "Background Verification", "Meeting Arrangement"]
    val languages: List<String>, // e.g., ["Bengali", "English", "Hindi"]
    val workingHours: String? = null, // e.g., "9 AM - 6 PM"
    val consultationFee: String? = null, // e.g., "Free Consultation" or "৳5000"
    val socialMedia: MatchmakerSocialMedia? = null,
    val testimonials: List<Testimonial> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastActive: Long = System.currentTimeMillis()
)

data class MatchmakerSocialMedia(
    val facebook: String? = null,
    val instagram: String? = null,
    val linkedin: String? = null,
    val website: String? = null
)

data class Testimonial(
    val clientName: String,
    val message: String,
    val rating: Double,
    val date: Long = System.currentTimeMillis()
)

enum class MatchmakerSpecialization(val label: String) {
    ELITE_FAMILIES("Elite Families"),
    DOCTORS("Doctors"),
    ENGINEERS("Engineers"),
    BUSINESS("Business Families"),
    GOVERNMENT_SERVICE("Government Service"),
    OVERSEAS("Overseas Matches"),
    DIVORCED_WIDOWED("Divorced/Widowed"),
    GENERAL("General")
}

enum class MatchmakerServiceType(val label: String) {
    PROFILE_CREATION("Profile Creation"),
    BACKGROUND_VERIFICATION("Background Verification"),
    MEETING_ARRANGEMENT("Meeting Arrangement"),
    FAMILY_COUNSELING("Family Counseling"),
    POST_MARRIAGE_SUPPORT("Post-Marriage Support"),
    BIODATA_WRITING("Biodata Writing"),
    PHOTOGRAPHY("Photography Services"),
    NEGOTIATION("Marriage Negotiation")
}

