package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.manage

import com.akash.beautifulbhaluka.domain.model.Matchmaker

data class ManageMatchmakerUiState(
    val isLoading: Boolean = false,
    val myProfiles: List<Matchmaker> = emptyList(),
    val error: String? = null,
    val deleteDialogProfile: Matchmaker? = null,
    val isDeleting: Boolean = false
)

sealed class ManageMatchmakerAction {
    object LoadMyProfiles : ManageMatchmakerAction()
    object Refresh : ManageMatchmakerAction()
    data class ShowDeleteDialog(val profile: Matchmaker) : ManageMatchmakerAction()
    object DismissDeleteDialog : ManageMatchmakerAction()
    data class DeleteProfile(val profileId: String) : ManageMatchmakerAction()
    data class ToggleAvailability(val profileId: String) : ManageMatchmakerAction()
}

