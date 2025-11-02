package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ManageProfilesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ManageProfilesUiState())
    val uiState: StateFlow<ManageProfilesUiState> = _uiState.asStateFlow()

    init {
        loadMyProfiles()
    }

    fun onAction(action: ManageProfilesAction) {
        when (action) {
            is ManageProfilesAction.LoadMyProfiles -> loadMyProfiles()
            is ManageProfilesAction.Refresh -> refresh()
            is ManageProfilesAction.DeleteProfile -> deleteProfile(action.profileId)
            is ManageProfilesAction.ShowDeleteDialog -> showDeleteDialog(action.profileId)
            is ManageProfilesAction.HideDeleteDialog -> hideDeleteDialog()
            is ManageProfilesAction.ConfirmDelete -> confirmDelete()
        }
    }

    private fun loadMyProfiles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // TODO: Replace with actual repository call
                // val profiles = repository.getMyProfiles()

                // Mock data for now - in production, fetch from repository
                val mockProfiles = getMockUserProfiles()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        myProfiles = mockProfiles,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load profiles"
                    )
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            try {
                // TODO: Replace with actual repository call
                val mockProfiles = getMockUserProfiles()

                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        myProfiles = mockProfiles,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        error = e.message ?: "Failed to refresh"
                    )
                }
            }
        }
    }

    private fun showDeleteDialog(profileId: String) {
        _uiState.update {
            it.copy(
                showDeleteDialog = true,
                profileToDelete = profileId
            )
        }
    }

    private fun hideDeleteDialog() {
        _uiState.update {
            it.copy(
                showDeleteDialog = false,
                profileToDelete = null
            )
        }
    }

    private fun confirmDelete() {
        val profileId = _uiState.value.profileToDelete
        if (profileId != null) {
            deleteProfile(profileId)
        }
        hideDeleteDialog()
    }

    private fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            try {
                // TODO: Replace with actual repository call
                // repository.deleteProfile(profileId)

                // For now, just remove from list
                val updatedProfiles = _uiState.value.myProfiles.filter { it.id != profileId }

                _uiState.update {
                    it.copy(
                        myProfiles = updatedProfiles,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message ?: "Failed to delete profile")
                }
            }
        }
    }

    // Mock data - replace with actual repository in production
    private fun getMockUserProfiles() = listOf(
        MatchmakingProfile(
            id = "user_1",
            name = "Anika Rahman",
            age = 26,
            gender = "Female",
            occupation = "Software Engineer",
            education = "Bachelor's in Computer Science",
            location = "Dhaka, Bangladesh",
            height = "5'4\"",
            religion = "Islam",
            maritalStatus = "Never Married",
            bio = "A passionate software developer who loves technology and innovation. Looking for someone who shares similar values and interests in building a beautiful life together.",
            interests = listOf("Coding", "Reading", "Travel", "Photography"),
            verified = true,
            createdAt = System.currentTimeMillis() - 86400000 * 5 // 5 days ago
        ),
        MatchmakingProfile(
            id = "user_2",
            name = "Md. Fahim Hasan",
            age = 29,
            gender = "Male",
            occupation = "Doctor (MBBS)",
            education = "Bachelor of Medicine, Bachelor of Surgery",
            location = "Mymensingh, Bangladesh",
            height = "5'10\"",
            religion = "Islam",
            maritalStatus = "Never Married",
            bio = "Dedicated medical professional serving at a government hospital. Family-oriented and seeking a caring life partner who values tradition and modern thinking.",
            interests = listOf("Medicine", "Cricket", "Music", "Cooking"),
            verified = true,
            createdAt = System.currentTimeMillis() - 86400000 * 15 // 15 days ago
        ),
        MatchmakingProfile(
            id = "user_3",
            name = "Sabrina Akter",
            age = 24,
            gender = "Female",
            occupation = "Teacher",
            education = "Master's in English Literature",
            location = "Bhaluka, Mymensingh",
            height = "5'3\"",
            religion = "Islam",
            maritalStatus = "Never Married",
            bio = "An enthusiastic educator passionate about shaping young minds. Love literature, nature walks, and meaningful conversations. Looking for a respectful and understanding partner.",
            interests = listOf("Teaching", "Literature", "Gardening", "Art"),
            verified = false,
            createdAt = System.currentTimeMillis() - 86400000 * 30 // 30 days ago
        )
    )
}

