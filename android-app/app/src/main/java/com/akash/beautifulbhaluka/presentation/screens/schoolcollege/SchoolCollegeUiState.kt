package com.akash.beautifulbhaluka.presentation.screens.schoolcollege

enum class InstitutionCategory(val label: String) {
    ALL("সব"),
    SCHOOL("স্কুল"),
    COLLEGE("কলেজ"),
    MADRASA("মাদ্রসা"),
    COACHING_CENTER("কোচিং সেন্টার"),
    KINDERGARTEN("কিন্ডারগার্টেন"),
    PRIMARY_SCHOOL("প্রাইমারি স্কুল")
}

data class SchoolCollegeUiState(
    val isLoading: Boolean = false,
    val selectedCategory: InstitutionCategory = InstitutionCategory.ALL,
    val allInstitutions: List<Institution> = emptyList(),
    val filteredInstitutions: List<Institution> = emptyList(),
    val featuredInstitutions: List<FeaturedInstitution> = emptyList(),
    val colleges: List<CollegeInstitution> = emptyList(),
    val highSchools: List<HighSchoolInstitution> = emptyList(),
    val error: String? = null
)

data class Institution(
    val id: String,
    val name: String,
    val category: InstitutionCategory,
    val thumbnail: String? = null,
    val established: String,
    val eiin: String,
    val location: String? = null,
    val mobile: String? = null,
    val description: String? = null
)

data class FeaturedInstitution(
    val title: String,
    val thumbnail: String,
    val founder: String? = null,
    val established: String,
    val eiin: String,
    val collegeCode: String? = null,
    val website: String? = null,
    val details: String? = null
)

data class CollegeInstitution(
    val serialNumber: String,
    val name: String,
    val eiin: String
)

data class HighSchoolInstitution(
    val serialNumber: String,
    val name: String,
    val eiin: String
)

sealed interface SchoolCollegeAction {
    object LoadData : SchoolCollegeAction
    data class OnInstitutionClick(val institution: FeaturedInstitution) : SchoolCollegeAction
    data class OnCategorySelected(val category: InstitutionCategory) : SchoolCollegeAction
    object OnPublishClick : SchoolCollegeAction
}
