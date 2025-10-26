package com.akash.beautifulbhaluka.presentation.screens.social.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SocialProfileViewModel : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(SocialProfileUiState())
    val uiState: StateFlow<SocialProfileUiState> = _uiState.asStateFlow()

    fun onAction(action: SocialProfileAction) {
        when (action) {
            is SocialProfileAction.LoadProfile -> loadProfile(action.userId)
            is SocialProfileAction.SelectTab -> selectTab(action.tab)
            is SocialProfileAction.ToggleEditMode -> toggleEditMode()
            is SocialProfileAction.UpdateBio -> updateBio(action.bio)
            is SocialProfileAction.UpdateLocation -> updateLocation(action.location)
            is SocialProfileAction.UpdateWebsite -> updateWebsite(action.website)
            is SocialProfileAction.SaveProfile -> saveProfile()
            is SocialProfileAction.FollowUser -> followUser()
            is SocialProfileAction.UnfollowUser -> unfollowUser()
            is SocialProfileAction.ReactToPost -> reactToPost(action.postId, action.reaction)
            is SocialProfileAction.ReactToPostWithCustomEmoji -> reactToPostWithCustomEmoji(action.postId, action.emoji, action.label)
            else -> {}
        }
    }

    private fun loadProfile(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val profileResult = repository.getProfile(userId)
            val postsResult = repository.getUserPosts(userId)
            val highlightsResult = repository.getStoryHighlights(userId)
            val friendsResult = repository.getFriends(userId)
            val photosResult = repository.getPhotos(userId)

            profileResult.onSuccess { profile ->
                postsResult.onSuccess { posts ->
                    highlightsResult.onSuccess { highlights ->
                        friendsResult.onSuccess { friends ->
                            photosResult.onSuccess { photos ->
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    profile = profile,
                                    posts = posts,
                                    storyHighlights = highlights,
                                    friends = friends,
                                    photos = photos,
                                    editBio = profile.bio,
                                    editLocation = profile.location,
                                    editWebsite = profile.website
                                )
                            }
                        }
                    }
                }
            }

            profileResult.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        }
    }

    private fun selectTab(tab: ProfileTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    private fun toggleEditMode() {
        val currentEditMode = _uiState.value.isEditMode
        _uiState.value = _uiState.value.copy(isEditMode = !currentEditMode)
    }

    private fun updateBio(bio: String) {
        _uiState.value = _uiState.value.copy(editBio = bio)
    }

    private fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(editLocation = location)
    }

    private fun updateWebsite(website: String) {
        _uiState.value = _uiState.value.copy(editWebsite = website)
    }

    private fun saveProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, error = null)

            repository.updateProfile(
                bio = _uiState.value.editBio,
                location = _uiState.value.editLocation,
                website = _uiState.value.editWebsite
            )
                .onSuccess { profile ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        isEditMode = false,
                        profile = profile
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        error = error.message
                    )
                }
        }
    }

    private fun followUser() {
        val userId = _uiState.value.profile?.userId ?: return
        viewModelScope.launch {
            repository.followUser(userId)
                .onSuccess {
                    _uiState.value.profile?.let { profile ->
                        _uiState.value = _uiState.value.copy(
                            profile = profile.copy(
                                isFollowing = true,
                                followersCount = profile.followersCount + 1
                            )
                        )
                    }
                }
        }
    }

    private fun unfollowUser() {
        val userId = _uiState.value.profile?.userId ?: return
        viewModelScope.launch {
            repository.unfollowUser(userId)
                .onSuccess {
                    _uiState.value.profile?.let { profile ->
                        _uiState.value = _uiState.value.copy(
                            profile = profile.copy(
                                isFollowing = false,
                                followersCount = maxOf(0, profile.followersCount - 1)
                            )
                        )
                    }
                }
        }
    }

    private fun reactToPost(postId: String, reaction: com.akash.beautifulbhaluka.domain.model.Reaction) {
        // Update UI state immediately for responsive feedback
        val updatedPosts = _uiState.value.posts.map { post ->
            if (post.id == postId) {
                // Check if clicking the same reaction (to unlike)
                val isSameReaction = post.userReaction == reaction

                if (isSameReaction && post.isLiked) {
                    // Unlike the post
                    post.copy(
                        isLiked = false,
                        likes = maxOf(0, post.likes - 1),
                        userReaction = null
                    )
                } else {
                    // Like with new reaction or change reaction
                    val likesChange = if (post.isLiked) 0 else 1
                    post.copy(
                        isLiked = true,
                        likes = post.likes + likesChange,
                        userReaction = reaction
                    )
                }
            } else {
                post
            }
        }

        _uiState.value = _uiState.value.copy(posts = updatedPosts)

        // TODO: Call repository to persist the reaction
        // viewModelScope.launch {
        //     repository.reactToPost(postId, reaction.name)
        // }
    }

    private fun reactToPostWithCustomEmoji(postId: String, emoji: String, label: String) {
        // Update UI state immediately for responsive feedback
        val updatedPosts = _uiState.value.posts.map { post ->
            if (post.id == postId) {
                post.copy(
                    isLiked = true,
                    likes = if (post.isLiked) post.likes else post.likes + 1,
                    userReaction = com.akash.beautifulbhaluka.domain.model.Reaction.CUSTOM,
                    customReactionEmoji = emoji,
                    customReactionLabel = label
                )
            } else {
                post
            }
        }

        _uiState.value = _uiState.value.copy(posts = updatedPosts)

        // TODO: Call repository to persist the custom reaction
        // viewModelScope.launch {
        //     repository.reactToPostWithCustomEmoji(postId, emoji, label)
        // }
    }
}
