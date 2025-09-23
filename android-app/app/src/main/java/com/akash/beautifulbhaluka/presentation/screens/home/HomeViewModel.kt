package com.akash.beautifulbhaluka.presentation.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var onNavigateToScreen: ((String) -> Unit)? = null

    fun setNavigationCallback(callback: (String) -> Unit) {
        onNavigateToScreen = callback
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.LoadData -> loadData()
            is HomeAction.NavigateToLink -> navigateToLink(action.link)
        }
    }

    private fun loadData() {
        // For now, we're using default data from HomeUiState
        // In the future, this could load data from a repository
        _uiState.value = HomeUiState()
    }

    private fun navigateToLink(link: LinkItem) {
        onNavigateToScreen?.invoke(link.route)
    }
}
