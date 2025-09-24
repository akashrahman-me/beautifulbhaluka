package com.akash.beautifulbhaluka.presentation.screens.pourashava

data class PourashavaUiState(
    val isLoading: Boolean = false,
    val pourashavaInfo: com.akash.beautifulbhaluka.domain.model.PourashavaInfo? = null,
    val error: String? = null,
    val expandedSections: Set<Int> = emptySet()
)

sealed class PourashavaAction {
    object LoadData : PourashavaAction()
    data class ToggleSection(val index: Int) : PourashavaAction()
}
