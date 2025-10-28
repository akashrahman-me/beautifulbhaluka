package com.akash.beautifulbhaluka.presentation.screens.social.profile.friends

import com.akash.beautifulbhaluka.domain.model.Friend

/**
 * UI State for All Friends Screen
 */
data class AllFriendsUiState(
    val isLoading: Boolean = false,
    val friends: List<Friend> = emptyList(),
    val filteredFriends: List<Friend> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: FriendFilter = FriendFilter.ALL,
    val error: String? = null,
    val isRefreshing: Boolean = false
)

/**
 * User Actions for All Friends Screen
 */
sealed class AllFriendsAction {
    data class LoadFriends(val userId: String) : AllFriendsAction()
    data class SearchFriends(val query: String) : AllFriendsAction()
    data class SelectFilter(val filter: FriendFilter) : AllFriendsAction()
    data class FriendClick(val friend: Friend) : AllFriendsAction()
    data class MessageClick(val friend: Friend) : AllFriendsAction()
    data class RemoveFriend(val friend: Friend) : AllFriendsAction()
    object Refresh : AllFriendsAction()
    object NavigateBack : AllFriendsAction()
}

/**
 * Friend Filter Types
 */
enum class FriendFilter(val displayName: String) {
    ALL("All Friends"),
    RECENT("Recently Added"),
    MUTUAL("Mutual Friends"),
    HOMETOWN("Hometown"),
    WORK("Work")
}

