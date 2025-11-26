package com.akash.beautifulbhaluka.domain.model

data class Writing(
    val id: String,
    val title: String,
    val excerpt: String,
    val content: String,
    val coverImageUrl: String?,
    val category: WritingCategory,
    val authorId: String,
    val authorName: String,
    val authorProfileImage: String?,
    val likes: Int = 0,
    val comments: Int = 0,
    val isLiked: Boolean = false,
    val userReaction: Reaction? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

