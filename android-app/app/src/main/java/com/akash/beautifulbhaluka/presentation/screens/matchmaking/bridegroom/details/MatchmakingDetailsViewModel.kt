package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchmakingDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MatchmakingDetailsUiState())
    val uiState: StateFlow<MatchmakingDetailsUiState> = _uiState.asStateFlow()

    fun onAction(action: MatchmakingDetailsAction) {
        when (action) {
            is MatchmakingDetailsAction.LoadProfile -> loadProfile(action.profileId)
            is MatchmakingDetailsAction.ToggleContactInfo -> toggleContactInfo()
            is MatchmakingDetailsAction.ToggleFavorite -> toggleFavorite()
            is MatchmakingDetailsAction.SendInterest -> sendInterest()
            is MatchmakingDetailsAction.CallContact -> callContact()
            is MatchmakingDetailsAction.SendEmail -> sendEmail()
            is MatchmakingDetailsAction.ClearError -> clearError()
        }
    }

    private fun loadProfile(profileId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // TODO: Implement actual API call
                delay(1000) // Simulate network call

                // Mock data for demonstration
                val mockProfile = MatchmakingProfile(
                    id = profileId,
                    name = "Fatima Rahman",
                    age = 26,
                    gender = "Female",
                    occupation = "Software Engineer",
                    education = "B.Sc in CSE, BUET",
                    location = "Bhaluka, Mymensingh",
                    height = "5'4\"",
                    religion = "Islam",
                    maritalStatus = "Never Married",
                    bio = "I'm a passionate software engineer who loves creating innovative solutions. Looking for a life partner who values education, family, and personal growth. I enjoy reading, traveling, and exploring new cuisines.",
                    interests = listOf(
                        "Reading",
                        "Traveling",
                        "Cooking",
                        "Technology",
                        "Photography",
                        "Music"
                    ),
                    verified = true,
                    contactNumber = "+880 1XXX-XXXXXX",
                    email = "contact@example.com"
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        profile = mockProfile
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load profile"
                    )
                }
            }
        }
    }

    private fun toggleContactInfo() {
        _uiState.update { it.copy(showContactInfo = !it.showContactInfo) }
    }

    private fun toggleFavorite() {
        _uiState.update { it.copy(isFavorite = !it.isFavorite) }
    }

    private fun sendInterest() {
        viewModelScope.launch {
            _uiState.update { it.copy(isContactingInProgress = true) }
            try {
                // TODO: Implement actual API call
                delay(1000)
                _uiState.update {
                    it.copy(
                        isContactingInProgress = false,
                        interestSent = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isContactingInProgress = false,
                        error = "Failed to send interest"
                    )
                }
            }
        }
    }

    private fun callContact() {
        // TODO: Implement call functionality
    }

    private fun sendEmail() {
        // TODO: Implement email functionality
    }


    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

