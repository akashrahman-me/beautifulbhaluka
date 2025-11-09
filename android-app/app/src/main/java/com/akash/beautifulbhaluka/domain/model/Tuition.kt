package com.akash.beautifulbhaluka.domain.model

data class TuitionPost(
    val id: String,
    val teacherId: String,
    val teacherName: String,
    val teacherPhoto: String,
    val teacherRating: Float,
    val totalReviews: Int,
    val subjects: List<String>,
    val classes: List<String>,
    val experience: String,
    val qualification: String,
    val preferredLocations: List<String>,
    val preferredMedium: List<String>, // Bangla, English
    val fee: String, // Per month or per hour
    val availability: String,
    val contactNumber: String,
    val description: String,
    val verified: Boolean,
    val createdAt: Long,
    val isFavorite: Boolean = false
)

data class TuitionRequest(
    val id: String,
    val parentName: String,
    val parentPhoto: String,
    val studentClass: String,
    val subjects: List<String>,
    val preferredGender: String, // Male, Female, Any
    val numberOfDays: Int,
    val salary: String,
    val location: String,
    val medium: String,
    val requirements: String,
    val contactNumber: String,
    val createdAt: Long,
    val status: String // Active, Closed
)

enum class TuitionCategory {
    FIND_TUTOR,
    FIND_STUDENT,
    ALL
}

enum class TuitionClass(val label: String) {
    CLASS_1_5("Class 1-5"),
    CLASS_6_8("Class 6-8"),
    CLASS_9_10("Class 9-10"),
    CLASS_11_12("Class 11-12"),
    UNIVERSITY("University")
}

enum class TuitionSubject(val label: String) {
    BANGLA("Bangla"),
    ENGLISH("English"),
    MATH("Math"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    ICT("ICT"),
    ACCOUNTING("Accounting"),
    ECONOMICS("Economics"),
    ALL_SUBJECTS("All Subjects")
}

