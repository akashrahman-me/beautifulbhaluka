package com.akash.beautifulbhaluka.presentation.screens.social

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.SocialRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SocialFeedViewModel : ViewModel() {

    private val repository = SocialRepositoryImpl()

    private val _uiState = MutableStateFlow(SocialFeedUiState())
    val uiState: StateFlow<SocialFeedUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun onAction(action: SocialFeedAction) {
        when (action) {
            is SocialFeedAction.LoadPosts -> loadPosts()
            is SocialFeedAction.Refresh -> refreshPosts()
            is SocialFeedAction.LikePost -> likePost(action.postId)
            is SocialFeedAction.UnlikePost -> unlikePost(action.postId)
            is SocialFeedAction.SharePost -> sharePost(action.postId)
            is SocialFeedAction.DeletePost -> deletePost(action.postId)
            else -> {}
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getPosts()
                .onSuccess { posts ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        posts = posts,
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }

    private fun refreshPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)

            repository.getPosts()
                .onSuccess { posts ->
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false,
                        posts = posts,
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false,
                        error = error.message
                    )
                }
        }
    }

    private fun likePost(postId: String) {
        viewModelScope.launch {
            repository.likePost(postId)
                .onSuccess {
                    // Update local state optimistically
                    _uiState.value = _uiState.value.copy(
                        posts = _uiState.value.posts.map { post ->
                            if (post.id == postId) {
                                post.copy(likes = post.likes + 1, isLiked = true)
                            } else {
                                post
                            }
                        }
                    )
                }
        }
    }

    private fun unlikePost(postId: String) {
        viewModelScope.launch {
            repository.unlikePost(postId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        posts = _uiState.value.posts.map { post ->
                            if (post.id == postId) {
                                post.copy(likes = maxOf(0, post.likes - 1), isLiked = false)
                            } else {
                                post
                            }
                        }
                    )
                }
        }
    }

    private fun sharePost(postId: String) {
        viewModelScope.launch {
            repository.sharePost(postId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        posts = _uiState.value.posts.map { post ->
                            if (post.id == postId) {
                                post.copy(shares = post.shares + 1)
                            } else {
                                post
                            }
                        }
                    )
                }
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            repository.deletePost(postId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        posts = _uiState.value.posts.filter { it.id != postId }
                    )
                }
        }
    }

    private fun reactToPost(postId: String, reaction: com.akash.beautifulbhaluka.domain.model.Reaction) {
        _uiState.value = _uiState.value.copy(
            posts = _uiState.value.posts.map { post ->
                if (post.id == postId) {
                    post.copy(
                        userReaction = reaction,
                        customReactionEmoji = null,
                        customReactionLabel = null,
                        isLiked = true,
                        likes = if (!post.isLiked && post.userReaction == null && post.customReactionEmoji == null) {
                            post.likes + 1
                        } else {
                            post.likes
                        }
                    )
                } else {
                    post
                }
            }
        )
    }

    private fun customReactToPost(postId: String, emoji: String, label: String) {
        _uiState.value = _uiState.value.copy(
            posts = _uiState.value.posts.map { post ->
                if (post.id == postId) {
                    post.copy(
                        customReactionEmoji = emoji,
                        customReactionLabel = label,
                        userReaction = null,
                        isLiked = true,
                        likes = if (!post.isLiked && post.userReaction == null && post.customReactionEmoji == null) {
                            post.likes + 1
                        } else {
                            post.likes
                        }
                    )
                } else {
                    post
                }
            }
        )
    }
}
