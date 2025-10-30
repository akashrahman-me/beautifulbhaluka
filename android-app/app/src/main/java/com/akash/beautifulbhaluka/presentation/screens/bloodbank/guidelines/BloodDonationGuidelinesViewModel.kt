package com.akash.beautifulbhaluka.presentation.screens.bloodbank.guidelines

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BloodDonationGuidelinesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BloodDonationGuidelinesUiState())
    val uiState: StateFlow<BloodDonationGuidelinesUiState> = _uiState.asStateFlow()

    val guidelineData = GuidelineData()

    fun onAction(action: GuidelinesAction) {
        when (action) {
            is GuidelinesAction.ToggleSection -> toggleSection(action.section)
        }
    }

    private fun toggleSection(section: GuidelineSection) {
        _uiState.update { currentState ->
            val expandedSections = currentState.expandedSections.toMutableSet()
            if (expandedSections.contains(section)) {
                expandedSections.remove(section)
            } else {
                expandedSections.add(section)
            }
            currentState.copy(expandedSections = expandedSections)
        }
    }
}

