package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.details

import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile

data class MatchmakingDetailsUiState(
    val isLoading: Boolean = true,
    val profile: MatchmakingProfile? = null,
    val showContactInfo: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false,
    val isContactingInProgress: Boolean = false,
    val interestSent: Boolean = false
)

sealed class MatchmakingDetailsAction {
    data class LoadProfile(val profileId: String) : MatchmakingDetailsAction()
    object ToggleContactInfo : MatchmakingDetailsAction()
    object ToggleFavorite : MatchmakingDetailsAction()
    object SendInterest : MatchmakingDetailsAction()
    object CallContact : MatchmakingDetailsAction()
    object SendEmail : MatchmakingDetailsAction()
    object ClearError : MatchmakingDetailsAction()
}

