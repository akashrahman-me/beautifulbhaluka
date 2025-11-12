package com.akash.beautifulbhaluka.domain.model

data class NewsArticle(
    val id: String,
    val url: String,
    val title: String,
    val content: String, // Excerpt/preview
    val thumbnailUrl: String?,
    val sourceUrl: String,
    val addedAt: Long = System.currentTimeMillis(),
    val userId: String,
    val userName: String
)

