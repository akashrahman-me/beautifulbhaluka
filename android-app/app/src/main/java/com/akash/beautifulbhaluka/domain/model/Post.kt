package com.akash.beautifulbhaluka.domain.model

data class Post(
    val id: String,
    val userId: String,
    val userName: String,
    val userProfileImage: String,
    val content: String,
    val images: List<String> = emptyList(),
    val videoUrl: String? = null,
    val likes: Int = 0,
    val comments: Int = 0,
    val shares: Int = 0,
    val isLiked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val location: String? = null,
    val privacy: PostPrivacy = PostPrivacy.PUBLIC
)

enum class PostPrivacy(val displayName: String) {
    PUBLIC("সবাই"),
    FRIENDS("বন্ধুরা"),
    ONLY_ME("শুধুমাত্র আমি")
}

data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val userName: String,
    val userProfileImage: String,
    val content: String,
    val likes: Int = 0,
    val isLiked: Boolean = false,
    val replies: List<Comment> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)
