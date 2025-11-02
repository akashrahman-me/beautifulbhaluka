package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageMatchmakerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ManageMatchmakerUiState())
    val uiState: StateFlow<ManageMatchmakerUiState> = _uiState.asStateFlow()

    init {
        loadMyProfiles()
    }

    fun onAction(action: ManageMatchmakerAction) {
        when (action) {
            is ManageMatchmakerAction.LoadMyProfiles -> loadMyProfiles()
            is ManageMatchmakerAction.Refresh -> refresh()
            is ManageMatchmakerAction.ShowDeleteDialog -> showDeleteDialog(action.profile)
            is ManageMatchmakerAction.DismissDeleteDialog -> dismissDeleteDialog()
            is ManageMatchmakerAction.DeleteProfile -> deleteProfile(action.profileId)
            is ManageMatchmakerAction.ToggleAvailability -> toggleAvailability(action.profileId)
        }
    }

    private fun loadMyProfiles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1000) // Simulate network call

            // Mock data - in real app, fetch from repository
            val mockProfiles = generateMockMyProfiles()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    myProfiles = mockProfiles
                )
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            delay(800)
            loadMyProfiles()
        }
    }

    private fun showDeleteDialog(profile: Matchmaker) {
        _uiState.update { it.copy(deleteDialogProfile = profile) }
    }

    private fun dismissDeleteDialog() {
        _uiState.update { it.copy(deleteDialogProfile = null) }
    }

    private fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true) }
            delay(1000) // Simulate API call

            // Remove from list
            val updatedProfiles = _uiState.value.myProfiles.filter { it.id != profileId }
            _uiState.update {
                it.copy(
                    myProfiles = updatedProfiles,
                    isDeleting = false,
                    deleteDialogProfile = null
                )
            }
        }
    }

    private fun toggleAvailability(profileId: String) {
        viewModelScope.launch {
            delay(500) // Simulate API call

            val updatedProfiles = _uiState.value.myProfiles.map { profile ->
                if (profile.id == profileId) {
                    profile.copy(available = !profile.available)
                } else {
                    profile
                }
            }
            _uiState.update { it.copy(myProfiles = updatedProfiles) }
        }
    }

    private fun generateMockMyProfiles(): List<Matchmaker> {
        // These would be the user's own matchmaker profiles
        return listOf(
            Matchmaker(
                id = "my1",
                name = "Your Matchmaker Profile",
                age = 50,
                experience = "15+ years",
                location = "Bhaluka, Mymensingh",
                contactNumber = "01711-123456",
                whatsapp = "01711-123456",
                email = "you@email.com",
                profileImageUrl = "https://picsum.photos/seed/matchmaker-my1/400/400",
                bio = "Professional matchmaker with over 15 years of experience helping families find perfect matches. I specialize in elite families and professionals.",
                specialization = listOf("Elite Families", "Doctors", "Business"),
                successfulMatches = 150,
                rating = 4.7,
                verified = true,
                available = true,
                servicesOffered = listOf(
                    "Profile Creation",
                    "Background Verification",
                    "Meeting Arrangement"
                ),
                languages = listOf("Bengali", "English"),
                workingHours = "10 AM - 8 PM",
                consultationFee = "৳2000",
                testimonials = listOf(
                    Testimonial("Client A", "Excellent service!", 5.0),
                    Testimonial("Client B", "Very professional.", 4.5)
                )
            )
        )
    }
}

