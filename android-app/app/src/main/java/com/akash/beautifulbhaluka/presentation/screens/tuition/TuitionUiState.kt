package com.akash.beautifulbhaluka.presentation.screens.tuition

import com.akash.beautifulbhaluka.domain.model.TuitionCategory
import com.akash.beautifulbhaluka.domain.model.TuitionPost
import com.akash.beautifulbhaluka.domain.model.TuitionRequest

data class TuitionUiState(
    val isLoading: Boolean = false,
    val tutors: List<TuitionPost> = emptyList(),
    val requests: List<TuitionRequest> = emptyList(),
    val selectedCategory: TuitionCategory = TuitionCategory.ALL,
    val searchQuery: String = "",
    val error: String? = null,
    val isRefreshing: Boolean = false
)

sealed class TuitionAction {
    object LoadData : TuitionAction()
    object Refresh : TuitionAction()
    data class SelectCategory(val category: TuitionCategory) : TuitionAction()
    data class UpdateSearch(val query: String) : TuitionAction()
    data class ToggleFavorite(val tutorId: String) : TuitionAction()
    data class NavigateToTutorDetails(val tutorId: String) : TuitionAction()
    object NavigateToPublishTutor : TuitionAction()
    object NavigateToPublishRequest : TuitionAction()
}

