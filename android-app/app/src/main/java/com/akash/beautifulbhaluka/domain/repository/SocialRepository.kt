package com.akash.beautifulbhaluka.domain.repository

import com.akash.beautifulbhaluka.domain.model.Comment
import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.model.PostPrivacy
import com.akash.beautifulbhaluka.domain.model.SocialProfile
import kotlinx.coroutines.flow.Flow

interface SocialRepository {
    // Posts
    suspend fun getPosts(): Result<List<Post>>
    suspend fun getPostById(postId: String): Result<Post>
    suspend fun getUserPosts(userId: String): Result<List<Post>>
    suspend fun createPost(
        content: String,
        images: List<String>,
        videoUrl: String?,
        privacy: PostPrivacy,
        location: String?
    ): Result<Post>
    suspend fun deletePost(postId: String): Result<Unit>
    suspend fun likePost(postId: String): Result<Unit>
    suspend fun unlikePost(postId: String): Result<Unit>
    suspend fun sharePost(postId: String): Result<Unit>

    // Comments
    suspend fun getComments(postId: String): Result<List<Comment>>
    suspend fun addComment(postId: String, content: String): Result<Comment>
    suspend fun deleteComment(commentId: String): Result<Unit>
    suspend fun likeComment(commentId: String): Result<Unit>
    suspend fun unlikeComment(commentId: String): Result<Unit>

    // Profile
    suspend fun getProfile(userId: String): Result<SocialProfile>
    suspend fun updateProfile(
        bio: String,
        location: String,
        website: String
    ): Result<SocialProfile>
    suspend fun updateProfileImage(imageUrl: String): Result<Unit>
    suspend fun updateCoverImage(imageUrl: String): Result<Unit>
    suspend fun followUser(userId: String): Result<Unit>
    suspend fun unfollowUser(userId: String): Result<Unit>

    // Observe changes
    fun observePosts(): Flow<List<Post>>
}

