package com.akash.beautifulbhaluka.presentation.screens.voterlist

data class VoterListUiState(
    val isLoading: Boolean = false,
    val voterListItems: List<VoterListItem> = emptyList(),
    val error: String? = null
)

data class VoterListItem(
    val unionName: String,
    val downloadUrl: String
)

sealed interface VoterListAction {
    object LoadData : VoterListAction
    data class DownloadFile(val item: VoterListItem) : VoterListAction
}
