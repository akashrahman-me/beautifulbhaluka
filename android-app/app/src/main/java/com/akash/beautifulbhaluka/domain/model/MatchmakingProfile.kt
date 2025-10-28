package com.akash.beautifulbhaluka.domain.model

data class MatchmakingProfile(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val occupation: String,
    val education: String,
    val location: String,
    val height: String,
    val religion: String,
    val maritalStatus: String,
    val bio: String,
    val interests: List<String>,
    val profileImageUrl: String? = null,
    val verified: Boolean = false,
    val contactNumber: String? = null,
    val email: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val familyDetails: FamilyDetails? = null,
    val preferences: MatchPreferences? = null
)

data class FamilyDetails(
    val fatherOccupation: String,
    val motherOccupation: String,
    val siblings: Int,
    val familyType: String // Nuclear, Joint
)

data class MatchPreferences(
    val ageRange: IntRange,
    val educationLevel: String,
    val preferredLocations: List<String>,
    val heightRange: String
)

enum class ProfileCategory {
    ALL,
    RECENT,
    VERIFIED,
    PREMIUM
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER
}

enum class MaritalStatus {
    NEVER_MARRIED,
    DIVORCED,
    WIDOWED
}

