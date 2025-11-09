package com.akash.beautifulbhaluka.presentation.screens.tuition.details

import com.akash.beautifulbhaluka.domain.model.TuitionPost

data class TuitionDetailsUiState(
    val isLoading: Boolean = false,
    val tutor: TuitionPost? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)

sealed class TuitionDetailsAction {
    data class LoadDetails(val tutorId: String) : TuitionDetailsAction()
    object ToggleFavorite : TuitionDetailsAction()
    object CallTutor : TuitionDetailsAction()
    object MessageTutor : TuitionDetailsAction()
    object ShareTutor : TuitionDetailsAction()
}

