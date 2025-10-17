package com.akash.beautifulbhaluka.domain.model

data class Photo(
    val id: String,
    val imageUrl: String,
    val caption: String = "",
    val likes: Int = 0,
    val comments: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

