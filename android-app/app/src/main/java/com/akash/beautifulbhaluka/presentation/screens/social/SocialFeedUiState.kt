package com.akash.beautifulbhaluka.presentation.screens.social

import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.model.PostPrivacy

data class SocialFeedUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)

sealed class SocialFeedAction {
    object LoadPosts : SocialFeedAction()
    object Refresh : SocialFeedAction()
    data class LikePost(val postId: String) : SocialFeedAction()
    data class UnlikePost(val postId: String) : SocialFeedAction()
    data class SharePost(val postId: String) : SocialFeedAction()
    data class DeletePost(val postId: String) : SocialFeedAction()
    data class NavigateToComments(val postId: String) : SocialFeedAction()
    data class NavigateToProfile(val userId: String) : SocialFeedAction()
    object NavigateToCreatePost : SocialFeedAction()
}

