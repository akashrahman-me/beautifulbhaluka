package com.akash.beautifulbhaluka.presentation.screens.social.profile.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.Friend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for All Friends Screen
 * Handles business logic and state management
 */
class AllFriendsViewModel : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(AllFriendsUiState())
    val uiState: StateFlow<AllFriendsUiState> = _uiState.asStateFlow()

    fun onAction(action: AllFriendsAction) {
        when (action) {
            is AllFriendsAction.LoadFriends -> loadFriends(action.userId)
            is AllFriendsAction.SearchFriends -> searchFriends(action.query)
            is AllFriendsAction.SelectFilter -> selectFilter(action.filter)
            is AllFriendsAction.FriendClick -> handleFriendClick(action.friend)
            is AllFriendsAction.MessageClick -> handleMessageClick(action.friend)
            is AllFriendsAction.RemoveFriend -> handleRemoveFriend(action.friend)
            is AllFriendsAction.Refresh -> refreshFriends()
            is AllFriendsAction.NavigateBack -> {} // Handled by navigation
        }
    }

    private fun loadFriends(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getFriends(userId)
                .onSuccess { friends ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            friends = friends,
                            filteredFriends = applyFilters(friends, it.searchQuery, it.selectedFilter),
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load friends"
                        )
                    }
                }
        }
    }

    private fun searchFriends(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                filteredFriends = applyFilters(it.friends, query, it.selectedFilter)
            )
        }
    }

    private fun selectFilter(filter: FriendFilter) {
        _uiState.update {
            it.copy(
                selectedFilter = filter,
                filteredFriends = applyFilters(it.friends, it.searchQuery, filter)
            )
        }
    }

    private fun applyFilters(
        friends: List<Friend>,
        searchQuery: String,
        filter: FriendFilter
    ): List<Friend> {
        var result = friends

        // Apply search filter
        if (searchQuery.isNotBlank()) {
            result = result.filter { friend ->
                friend.userName.contains(searchQuery, ignoreCase = true)
            }
        }

        // Apply category filter
        result = when (filter) {
            FriendFilter.ALL -> result
            FriendFilter.RECENT -> result.sortedByDescending { it.userId } // TODO: Add timestamp field
            FriendFilter.MUTUAL -> result.filter { it.mutualFriends > 0 }
            FriendFilter.HOMETOWN -> result // TODO: Add hometown filter
            FriendFilter.WORK -> result // TODO: Add work filter
        }

        return result
    }

    private fun refreshFriends() {
        val currentState = _uiState.value
        if (!currentState.isRefreshing && currentState.friends.isNotEmpty()) {
            _uiState.update { it.copy(isRefreshing = true) }
            viewModelScope.launch {
                // Simulate refresh
                kotlinx.coroutines.delay(1000)
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    private fun handleFriendClick(friend: Friend) {
        // TODO: Navigate to friend's profile
    }

    private fun handleMessageClick(friend: Friend) {
        // TODO: Navigate to chat with friend
    }

    private fun handleRemoveFriend(friend: Friend) {
        viewModelScope.launch {
            // TODO: Implement remove friend functionality
            _uiState.update { state ->
                state.copy(
                    friends = state.friends.filter { it.userId != friend.userId },
                    filteredFriends = state.filteredFriends.filter { it.userId != friend.userId }
                )
            }
        }
    }
}

