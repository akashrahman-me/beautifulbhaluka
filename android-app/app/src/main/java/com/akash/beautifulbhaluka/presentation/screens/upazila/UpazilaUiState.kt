package com.akash.beautifulbhaluka.presentation.screens.upazila

data class UpazilaUiState(
    val isLoading: Boolean = false,
    val upazilaInfo: com.akash.beautifulbhaluka.domain.model.UpazilaInfo? = null,
    val error: String? = null,
    val expandedSections: Set<Int> = emptySet()
)

sealed class UpazilaAction {
    object LoadData : UpazilaAction()
    data class ToggleSection(val index: Int) : UpazilaAction()
}
