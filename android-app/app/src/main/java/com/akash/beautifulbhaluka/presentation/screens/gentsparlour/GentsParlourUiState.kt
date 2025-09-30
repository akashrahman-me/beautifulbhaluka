package com.akash.beautifulbhaluka.presentation.screens.gentsparlour

data class GentsParlourUiState(
    val isLoading: Boolean = false,
    val parlours: List<GentsParlour> = emptyList(),
    val error: String? = null
)

data class GentsParlour(
    val title: String,
    val image: String,
    val description: String? = null,
    val address: String? = null,
    val phones: List<String> = emptyList()
)

sealed interface GentsParlourAction {
    object LoadData : GentsParlourAction
    data class CallPhone(val phoneNumber: String) : GentsParlourAction
}
