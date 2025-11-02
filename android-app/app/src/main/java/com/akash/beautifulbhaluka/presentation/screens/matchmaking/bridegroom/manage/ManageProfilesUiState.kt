package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.manage

import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile

data class ManageProfilesUiState(
    val isLoading: Boolean = false,
    val myProfiles: List<MatchmakingProfile> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val selectedProfile: MatchmakingProfile? = null,
    val showDeleteDialog: Boolean = false,
    val profileToDelete: String? = null
)

sealed class ManageProfilesAction {
    object LoadMyProfiles : ManageProfilesAction()
    object Refresh : ManageProfilesAction()
    data class DeleteProfile(val profileId: String) : ManageProfilesAction()
    data class ShowDeleteDialog(val profileId: String) : ManageProfilesAction()
    object HideDeleteDialog : ManageProfilesAction()
    object ConfirmDelete : ManageProfilesAction()
}

