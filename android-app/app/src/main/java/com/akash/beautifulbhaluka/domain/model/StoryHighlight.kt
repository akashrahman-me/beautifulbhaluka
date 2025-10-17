package com.akash.beautifulbhaluka.domain.model

data class StoryHighlight(
    val id: String,
    val title: String,
    val coverImage: String,
    val storiesCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

