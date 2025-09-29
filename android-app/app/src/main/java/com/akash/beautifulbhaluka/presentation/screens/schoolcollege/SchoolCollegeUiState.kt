package com.akash.beautifulbhaluka.presentation.screens.schoolcollege

data class SchoolCollegeUiState(
    val isLoading: Boolean = false,
    val featuredInstitutions: List<FeaturedInstitution> = emptyList(),
    val colleges: List<CollegeInstitution> = emptyList(),
    val highSchools: List<HighSchoolInstitution> = emptyList(),
    val error: String? = null
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
}
