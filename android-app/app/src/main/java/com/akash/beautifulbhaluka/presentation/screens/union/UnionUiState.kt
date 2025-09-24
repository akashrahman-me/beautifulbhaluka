package com.akash.beautifulbhaluka.presentation.screens.union

data class UnionUiState(
    val isLoading: Boolean = false,
    val unionInfo: com.akash.beautifulbhaluka.domain.model.UnionInfo? = null,
    val error: String? = null,
    val expandedSections: Set<Int> = emptySet()
)

sealed class UnionAction {
    object LoadData : UnionAction()
    data class ToggleSection(val index: Int) : UnionAction()
}
