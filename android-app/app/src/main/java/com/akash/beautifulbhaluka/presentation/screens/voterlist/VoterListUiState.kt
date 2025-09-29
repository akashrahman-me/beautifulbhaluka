package com.akash.beautifulbhaluka.presentation.screens.voterlist

import com.akash.beautifulbhaluka.domain.model.VoterListItem

/**
 * UI State for voter list screen
 */
data class VoterListUiState(
    val isLoading: Boolean = false,
    val voterListItems: List<VoterListItem> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)

/**
 * Actions that can be performed on voter list screen
 */
sealed class VoterListAction {
    object LoadData : VoterListAction()
    object Refresh : VoterListAction()
    data class DownloadFile(val item: VoterListItem) : VoterListAction()
}
