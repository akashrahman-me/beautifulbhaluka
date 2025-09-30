package com.akash.beautifulbhaluka.presentation.screens.directory

data class DirectoryUiState(
    val isLoading: Boolean = false,
    val offices: List<Office> = emptyList(),
    val subDistrictOffices: List<String> = emptyList(),
    val error: String? = null
)

data class Office(
    val title: String,
    val image: String,
    val address: List<String>
)

sealed interface DirectoryAction {
    object LoadData : DirectoryAction
}
