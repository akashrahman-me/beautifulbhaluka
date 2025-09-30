package com.akash.beautifulbhaluka.presentation.screens.cleaner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CleanerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CleanerUiState())
    val uiState: StateFlow<CleanerUiState> = _uiState.asStateFlow()

    init {
        onAction(CleanerAction.LoadData)
    }

    fun onAction(action: CleanerAction) {
        when (action) {
            is CleanerAction.LoadData -> loadData()
            is CleanerAction.CallCleaner -> callCleaner(action.phone)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the provided cleaner services
            val cleaners = listOf(
                Cleaner(
                    name = "মোঃ খলিল",
                    role = "টয়লেট পরিষ্কারক",
                    phone = "01885-418135",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/cartoon-man-with-a-mop-and-towel-free-png.png"
                ),
                Cleaner(
                    name = "সোরহাব",
                    role = "মটার দিয়ে টয়লেট পরিষ্কারক",
                    phone = "01317-732478",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/cartoon-man-with-a-mop-and-towel-free-png.png"
                ),
                Cleaner(
                    name = "সুইপার",
                    role = "টয়লেট পরিষ্কারক",
                    phone = "01941-087984",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/cartoon-man-with-a-mop-and-towel-free-png.png"
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    cleaners = cleaners,
                    error = null
                )
            }
        }
    }

    private fun callCleaner(phone: String) {
        // TODO: Implement phone call functionality
        // For now, this is just a placeholder
    }
}
