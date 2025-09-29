package com.akash.beautifulbhaluka.presentation.screens.voterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.VoterListRepositoryImpl
import com.akash.beautifulbhaluka.domain.usecase.GetVoterListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for voter list screen following MVVM pattern
 */
class VoterListViewModel : ViewModel() {

    private val repository = VoterListRepositoryImpl.getInstance()
    private val getVoterListUseCase = GetVoterListUseCase(repository)

    private val _uiState = MutableStateFlow(VoterListUiState())
    val uiState: StateFlow<VoterListUiState> = _uiState.asStateFlow()

    init {
        onAction(VoterListAction.LoadData)
    }

    fun onAction(action: VoterListAction) {
        when (action) {
            is VoterListAction.LoadData -> loadData()
            is VoterListAction.Refresh -> refresh()
            is VoterListAction.DownloadFile -> {
                // Download action will be handled in the UI layer
                // as it requires platform-specific functionality
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getVoterListUseCase()
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            voterListItems = data,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error occurred"
                        )
                    }
                }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, error = null) }

            getVoterListUseCase()
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            voterListItems = data,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            error = error.message ?: "Unknown error occurred"
                        )
                    }
                }
        }
    }
}
